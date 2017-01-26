package phonebase.android.kizema.phonebasetestapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import phonebase.android.kizema.phonebasetestapp.control.Controller;
import phonebase.android.kizema.phonebasetestapp.model.Contact;
import phonebase.android.kizema.phonebasetestapp.model.ContactHelper;

public class MainActivity extends Activity {

    private BroadcastReceiver dataFetchedReceiver;

    @BindView(R.id.btnSearch)
    public ImageButton btnSearch;

    @BindView(R.id.btnAdd)
    public ImageButton btnAdd;

    @BindView(R.id.etSearch)
    public EditText etSearch;

    @BindView(R.id.rvNames)
    public RecyclerView rvNames;

    @BindView(R.id.btnSort)
    public ImageButton btnSort;

    private ContactAdapter topicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init(savedInstanceState);
        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(dataFetchedReceiver);
        super.onDestroy();
    }

    private void init(Bundle savedInstanceState){
        List<Contact> contactList = ContactHelper.getAll();

        if (savedInstanceState == null) {
            //update from server only if first time open, do not update while rotation
            Controller.getInstance().fetchContacts();
        }

        topicAdapter = new ContactAdapter(contactList);
        rvNames.setAdapter(topicAdapter);
        LinearLayoutManager mChatLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNames.setLayoutManager(mChatLayoutManager);
        rvNames.setHasFixedSize(true);
    }

    private void registerReceiver(){
        dataFetchedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                List<Contact> contactList = ContactHelper.getAll();
                topicAdapter.update(contactList);
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Controller.FETCH_ACTION);
        registerReceiver(dataFetchedReceiver, intentFilter, null, null);
    }
}
