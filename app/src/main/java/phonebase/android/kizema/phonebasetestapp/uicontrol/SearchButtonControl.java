package phonebase.android.kizema.phonebasetestapp.uicontrol;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import phonebase.android.kizema.phonebasetestapp.BaseActivity;
import phonebase.android.kizema.phonebasetestapp.R;

public class SearchButtonControl {

    private ImageButton btnSearch;
    private BaseActivity activity;
    private EditText etSearch;

    private boolean isShown = true;

    public SearchButtonControl(BaseActivity a, final ImageButton btn, EditText editText){
        this.btnSearch = btn;
        this.activity = a;
        this.etSearch = editText;

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShown) {
                    activity.hideKeyboard(etSearch.getWindowToken());
                    btnSearch.setImageResource(R.drawable.ic_close);
                } else {
                    etSearch.setText("");
                    btnSearch.setImageResource(R.drawable.ic_search);
                }

                isShown = !isShown;
            }
        });
    }
}
