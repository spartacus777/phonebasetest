package phonebase.android.kizema.phonebasetestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import phonebase.android.kizema.phonebasetestapp.control.Controller;
import phonebase.android.kizema.phonebasetestapp.model.Contact;
import phonebase.android.kizema.phonebasetestapp.model.ContactHelper;
import phonebase.android.kizema.phonebasetestapp.uicontrol.SearchButtonControl;
import phonebase.android.kizema.phonebasetestapp.uicontrol.SortController;
import phonebase.android.kizema.phonebasetestapp.util.AppRecyclerView;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_ADD = 32;

    private BroadcastReceiver dataFetchedReceiver;

    @BindView(R.id.activity_main)
    public View parent;

    @BindView(R.id.btnSearch)
    public ImageButton btnSearch;

    @BindView(R.id.tvEmpty)
    public ProgressBar tvEmpty;

    @BindView(R.id.btnAdd)
    public ImageButton btnAdd;

    @BindView(R.id.etSearch)
    public EditText etSearch;

    @BindView(R.id.rvNames)
    public AppRecyclerView rvNames;

    private ContactAdapter contactAdapter;

    private SortController sortController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init(savedInstanceState);
        registerReceiver();
    }

    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        sortController.handleOnSaveState(icicle);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(dataFetchedReceiver);
        super.onDestroy();
    }

    private void init(Bundle savedInstanceState) {
        sortController = new SortController(parent, new SortController.OnSortListener() {
            @Override
            public void onSortChange(SortController.Status status) {
                update();
            }
        });
        sortController.handleOnRestoreState(savedInstanceState);

        List<Contact> contactList = ContactHelper.getAll(sortController.getStatus());

        if (savedInstanceState == null) {
            //update from server only if first time open, do not update while rotation
            Controller.getInstance().fetchContacts();
        }

        contactAdapter = new ContactAdapter(contactList);
        rvNames.setAdapter(contactAdapter);
        LinearLayoutManager mChatLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNames.setLayoutManager(mChatLayoutManager);
        rvNames.setHasFixedSize(true);

        rvNames.setEmptyView(tvEmpty);

        contactAdapter.setOnAdapterClickListener(new ContactAdapter.OnAdapterClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                sendEmail(contact);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewEntryActivity.class);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                update();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        new SearchButtonControl(this, btnSearch, etSearch);
    }

    private void registerReceiver() {
        dataFetchedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                update();
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Controller.FETCH_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(dataFetchedReceiver, intentFilter);
    }

    private void update() {
        String searchText = etSearch.getText().toString();

        if (searchText.equalsIgnoreCase("")) {
            List<Contact> contactList = ContactHelper.getAll(sortController.getStatus());
            contactAdapter.update(contactList);
        } else {
            List<Contact> contacts = ContactHelper.getAllSearch(sortController.getStatus(), searchText);
            contactAdapter.update(contacts);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ADD:
                if (resultCode == RESULT_OK) {
                    update();
                }
                break;
        }
    }

    private void sendEmail(Contact contact) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + contact.phoneNumberOwner));

        String title = "Hi mr. " + contact.phoneNumberOwner;
        String descr = new StringBuilder("Can I buy your phone num ")
                .append(contact.phoneNumber)
                .append(", I bet you price of ")
                .append(contact.phoneNumberPrice)
                .append("$")
                .append("\n Regards, Anton Kizema").toString();

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{contact.phoneNumberOwner});
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, descr);

        startActivity(Intent.createChooser(intent, "Send Email via"));
    }
}
