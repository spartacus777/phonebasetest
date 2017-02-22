package phonebase.android.kizema.phonebasetestapp.control;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import phonebase.android.kizema.phonebasetestapp.App;
import phonebase.android.kizema.phonebasetestapp.util.EncriptAlgo;

public class Controller {

    public static final String FETCH_ACTION = "FETCH_ACTION";
    public static final String TAG = "TAG";

    private static Controller instance;

    private volatile int counter = 0;

    public static synchronized Controller getInstance(){
        if (instance == null){
            instance = new Controller();
        }

        return instance;
    }

    private Controller(){}

    public void fetchContacts(){
        fetchOurContacts();
        fetchStolenContacts();
    }

    private synchronized void handleFetchFinished(){
        ++counter;

        if (counter == 2){
            Intent intent = new Intent();
            intent.setAction(FETCH_ACTION);
            LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
        }
    }

    private void fetchOurContacts(){
        Log.v(TAG, "getContacts() request");

        HttpHelper.getInstance().getAsync(ApiHelper.URL,
                new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.v(TAG, "getContacts() exception " + e);
                        handleFetchFinished();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        final String body = response.body().string();
                        Log.v(TAG, "getContacts() onResponse ");

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                JsonHelper.getInstance().parse(body);
                                handleFetchFinished();
                            }
                        }).start();
                    }
                });
    }

    private void fetchStolenContacts(){
        Log.v(TAG, "getContacts() request");

        HttpHelper.getInstance().getAsync(ApiHelper.STOLEN_URL,
                new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.v(TAG, "getContacts() exception " + e);
                        handleFetchFinished();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        final String body = response.body().string();
                        Log.v(TAG, "fetchStolenContacts() onResponse ");

                        String decryptedJson = EncriptAlgo.decode(body);
                        Log.v(TAG, "decryptedJson : " + decryptedJson);

                        JsonHelper.getInstance().parse(decryptedJson);

                        handleFetchFinished();
                    }
                });
    }

}
