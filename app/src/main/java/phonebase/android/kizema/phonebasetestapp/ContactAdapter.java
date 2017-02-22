package phonebase.android.kizema.phonebasetestapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import phonebase.android.kizema.phonebasetestapp.model.Contact;
import phonebase.android.kizema.phonebasetestapp.util.ValuableContactHelper;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> topics;

    private OnAdapterClickListener listener;

    public interface OnAdapterClickListener{
        void onItemClick(Contact contact);
    }

    public ContactAdapter(final List<Contact> conferences) {

        ValuableContactHelper.process(conferences, new ValuableContactHelper.OnCompletionListener() {
            @Override
            public void onComplete() {
                ContactAdapter.this.topics = conferences;
                notifyDataSetChanged();
            }
        });
    }

    public void update(final List<Contact> conferences){

        ValuableContactHelper.process(conferences, new ValuableContactHelper.OnCompletionListener() {
            @Override
            public void onComplete() {
                ContactAdapter.this.topics = conferences;
                notifyDataSetChanged();
            }
        });
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
            holder.tvDictionary.setText("-");
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

//        if (holder.thread != null){
//            holder.thread.cancelThread();
//            holder.thread = null;
//        }
//
//        if (phoneWord.length() == 0) {
//            holder.thread = new StoppableThread(model, holder);
//            holder.thread.start();
//        }
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

}


