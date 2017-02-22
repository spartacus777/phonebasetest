package phonebase.android.kizema.phonebasetestapp.util;

import android.util.Log;

import java.util.List;

import phonebase.android.kizema.phonebasetestapp.App;
import phonebase.android.kizema.phonebasetestapp.model.Contact;

public class ValuableContactHelper {

    public interface OnCompletionListener{
        void onComplete();
    }

    public static void process(final List<Contact> conferences, final OnCompletionListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("rr", "Algo starts");
                for (Contact c : conferences){
                    if (!c.isProccessed){
                        c.isProccessed = true;
                        String dicWord = isValuablePhone(c.getPhoneNumber());
                        c.dictionaryWord = dicWord;
                    }
                }

                Log.d("rr", "Algo end. Updating");
                App.getDaoSession().getContactDao().updateInTx(conferences);
                Log.d("rr", "updated");

                App.getUIHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onComplete();
                    }
                });
            }
        }).start();
    }

    public static String isValuablePhone(String phone){
       return isValuable(phone, "");
    }

    private static String isValuable(String phone, String dictionaryWords){
        int counter = 0;

        for (String s : DictionaryHelper.getInstance().dictionary){
            if (phone.startsWith(s)){
                if (s.length() == phone.length()){
                    return dictionaryWords + DictionaryHelper.getInstance().dictionaryWords.get(counter);
                } else {
                    String sub = phone.substring(s.length());
                    String w = isValuable(sub, dictionaryWords + DictionaryHelper.getInstance().dictionaryWords.get(counter));
                    if (w.length() > 0){
                        return w;
                    }
                }
            }

            ++counter;
        }

        return "";
    }
}
