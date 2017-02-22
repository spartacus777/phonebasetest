package phonebase.android.kizema.phonebasetestapp.util;

public class ValuableContactHelper {

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
