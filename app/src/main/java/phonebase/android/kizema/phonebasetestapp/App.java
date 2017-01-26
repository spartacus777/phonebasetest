package phonebase.android.kizema.phonebasetestapp;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import org.greenrobot.greendao.database.Database;

import phonebase.android.kizema.phonebasetestapp.model.DaoMaster;
import phonebase.android.kizema.phonebasetestapp.model.DaoSession;

public class App extends Application {

    private static Context context;
    private static Handler handler;

    private static DaoSession daoSession;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();

        initGreenDao();
    }

    public static Context getContext(){
        return context;
    }

    public static Handler getUIHandler(){
        return handler;
    }

    private static void initGreenDao(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "contacts-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }
}
