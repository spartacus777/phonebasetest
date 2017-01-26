package phonebase.android.kizema.phonebasetestapp.uicontrol;

import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import phonebase.android.kizema.phonebasetestapp.R;

public class SortController {

    @BindView(R.id.btnSort)
    public ImageView btnSort;

    @BindView(R.id.llSort)
    public View llSort;

    public interface OnSortListener{
        void onSortChange(Status status);
    }

    private Status status = Status.UNSORTED;
    private OnSortListener listener;


    public enum Status{
        UNSORTED,
        SORT_UP,
        SORT_DOWN;

        public Status next(){
            switch (this){
                case UNSORTED:
                    return SORT_UP;
                case SORT_UP:
                    return SORT_DOWN;
                case SORT_DOWN:
                    return UNSORTED;
            }

            return null;
        }
    }

    public SortController(View parent, final OnSortListener listener){
        ButterKnife.bind(this, parent);

        this.listener = listener;

        llSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = status.next();
                setProperImage();

                listener.onSortChange(status);
            }
        });
    }

    public Status getStatus(){
        return status;
    }

    private void setProperImage(){
        switch (status){
            case UNSORTED:
                btnSort.setImageResource(R.drawable.ic_unsorted);
                break;
            case SORT_UP:
                btnSort.setImageResource(R.drawable.ic_arrow_top);
                break;
            case SORT_DOWN:
                btnSort.setImageResource(R.drawable.ic_arrow_bottom);
                break;
        }
    }

}
