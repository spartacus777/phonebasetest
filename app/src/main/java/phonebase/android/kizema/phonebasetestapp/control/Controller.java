package phonebase.android.kizema.phonebasetestapp.control;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import phonebase.android.kizema.phonebasetestapp.App;

public class Controller {

    public static final String FETCH_ACTION = "FETCH_ACTION";

    private static Controller instance;

    public static synchronized Controller getInstance(){
        if (instance == null){
            instance = new Controller();
        }

        return instance;
    }

    private Controller(){}

    public void fetchContacts(){
        Log.v("rr", "getContacts() request");

        HttpHelper.getInstance().getAsync(ApiHelper.URL,
                new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.v("rr", "getContacts() exception " + e);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        final String body = response.body().string();
                        Log.v("rr", "getContacts() onResponse ");

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                JsonHelper.getInstance().parse(body);

                                Log.v("rr", "parse ready");

                                Intent intent = new Intent();
                                intent.setAction(FETCH_ACTION);
                                LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
                            }
                        }).start();
                    }
                });
    }

}
