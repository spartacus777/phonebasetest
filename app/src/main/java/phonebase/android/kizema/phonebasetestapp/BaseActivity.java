package phonebase.android.kizema.phonebasetestapp;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

public class BaseActivity extends Activity {

    public void hideKeyboard(IBinder binder) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (binder != null && inputManager != null) {
            inputManager.hideSoftInputFromWindow(binder, 0);//HIDE_NOT_ALWAYS
            inputManager.showSoftInputFromInputMethod(binder, 0);
        }
    }
}
