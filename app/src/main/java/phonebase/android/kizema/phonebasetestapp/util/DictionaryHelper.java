package phonebase.android.kizema.phonebasetestapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import phonebase.android.kizema.phonebasetestapp.App;

public class DictionaryHelper {

    public static DictionaryHelper instance;

    public List<String> dictionary;

    public static DictionaryHelper getInstance(){
        if (instance == null){
            instance = new DictionaryHelper();
        }

        return instance;
    }

    private DictionaryHelper(){
        init();
    }

    private void init(){
        dictionary = new ArrayList<>(4000);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(App.getContext().getAssets().open("dictionary.txt")));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                dictionary.add(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

}
