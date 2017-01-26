package phonebase.android.kizema.phonebasetestapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import phonebase.android.kizema.phonebasetestapp.model.Contact;
import phonebase.android.kizema.phonebasetestapp.util.DictionaryHelper;
import phonebase.android.kizema.phonebasetestapp.util.WordPatternBuilder;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> topics;

    private OnAdapterClickListener listener;

    public interface OnAdapterClickListener{
        void onItemClick(Contact contact);
    }

    public ContactAdapter(List<Contact> conferences) {
        this.topics = conferences;
    }

    public void update(List<Contact> conferences){
        this.topics = conferences;
        notifyDataSetChanged();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvEmail)
        public TextView tvEmail;

        @BindView(R.id.tvPhone)
        public TextView tvPhone;

        @BindView(R.id.tvPrice)
        public TextView tvPrice;

        @BindView(R.id.tvDictionary)
        public TextView tvDictionary;

        private StoppableThread thread;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);

        return new ContactViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {
        final Contact model = topics.get(position);

        String phoneWord = "";
        if (model.dictionaryWord != null &&
                model.dictionaryWord.length() > 0){
            phoneWord = model.dictionaryWord;
        }

        holder.tvEmail.setText(model.getPhoneNumberOwner());
        holder.tvPhone.setText(model.getPhoneNumber());
        if (phoneWord.length() > 0) {
            holder.tvDictionary.setText(model.getDictionaryWord());
        } else {
            holder.tvDictionary.setText("calculating..");
        }
        holder.tvPrice.setText(model.getPhoneNumberPrice() + "$");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onItemClick(model);
                }
            }
        });

        if (holder.thread != null){
            holder.thread.cancelThread();
            holder.thread = null;
        }

        if (phoneWord.length() == 0) {
            holder.thread = new StoppableThread(model, holder);
            holder.thread.start();
        }
    }

    @Override
    public int getItemCount() {
        if (topics == null){
            return 0;
        }

        return topics.size();
    }

    public void clear(){
        topics.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty(){
        if (topics == null || topics.size() == 0) {
            return true;
        }

        return false;
    }

    public void setOnAdapterClickListener(OnAdapterClickListener listener){
        this.listener = listener;
    }

    private static class StoppableThread extends Thread{

        private Contact model;
        private boolean isCanceled = false;
        private ContactViewHolder holder;

        public StoppableThread(Contact contact, ContactViewHolder holder){
            super();
            this.model = contact;
            this.holder = holder;
        }

        public void cancelThread(){
            isCanceled = true;
        }

        @Override
        public void run() {
            if (isCanceled){
                return;
            }

            List<String> dictionary = DictionaryHelper.getInstance().dictionary;
            String patternString = WordPatternBuilder.getPattern(Long.parseLong(model.getPhoneNumber()));
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher;

            for (String s : dictionary){
                if (isCanceled){
                    return;
                }

                matcher = pattern.matcher(s);
                if (matcher.matches()){
                    model.dictionaryWord = s;
                    App.getDaoSession().getContactDao().update(model);
                    App.getUIHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            holder.tvDictionary.setText(model.dictionaryWord);
                        }
                    });
                    return;
                }
            }

            model.dictionaryWord = "-";
            App.getDaoSession().getContactDao().update(model);
            App.getUIHandler().post(new Runnable() {
                @Override
                public void run() {
                    holder.tvDictionary.setText(model.dictionaryWord);
                }
            });
        }
    }
}


