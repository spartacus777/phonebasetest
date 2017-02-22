package phonebase.android.kizema.phonebasetestapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import phonebase.android.kizema.phonebasetestapp.model.Contact;
import phonebase.android.kizema.phonebasetestapp.model.ContactHelper;
import phonebase.android.kizema.phonebasetestapp.util.ValuableContactHelper;
import phonebase.android.kizema.phonebasetestapp.validator.EmailValidator;
import phonebase.android.kizema.phonebasetestapp.validator.PhoneValidator;

public class NewEntryActivity extends Activity {

    @BindView(R.id.etPrice)
    public EditText etPrice;

    @BindView(R.id.etPhone)
    public EditText etPhone;

    @BindView(R.id.etEmail)
    public EditText etEmail;

    @BindView(R.id.btnAdd)
    public Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = etPhone.getText().toString();
                String price = etPrice.getText().toString();
                String email = etEmail.getText().toString();

                if (!EmailValidator.getInstance().validate(email)){
                    Toast.makeText(NewEntryActivity.this, "Not valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!PhoneValidator.getInstance().validate(phone)){
                    Toast.makeText(NewEntryActivity.this, "Not valid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!PhoneValidator.getInstance().validate(price)){
                    Toast.makeText(NewEntryActivity.this, "Not valid Price", Toast.LENGTH_SHORT).show();
                    return;
                }

                Contact contact = ContactHelper.create(phone, Integer.parseInt(price), email, ValuableContactHelper.isValuablePhone(phone));
                App.getDaoSession().getContactDao().insertOrReplace(contact);

                setResult(RESULT_OK);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
