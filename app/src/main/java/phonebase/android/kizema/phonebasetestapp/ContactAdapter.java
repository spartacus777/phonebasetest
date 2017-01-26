package phonebase.android.kizema.phonebasetestapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import phonebase.android.kizema.phonebasetestapp.model.Contact;

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

        public TextView tvEmail, tvPhone, tvPrice;

        public ContactViewHolder(View itemView) {
            super(itemView);

            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
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

        holder.tvEmail.setText(model.getPhoneNumberOwner());
        holder.tvPhone.setText(model.getPhoneNumber());
        holder.tvPrice.setText(model.getPhoneNumberPrice() + "$");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onItemClick(model);
                }
            }
        });
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


