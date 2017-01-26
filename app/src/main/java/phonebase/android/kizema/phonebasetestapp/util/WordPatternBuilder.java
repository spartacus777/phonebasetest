package phonebase.android.kizema.phonebasetestapp.util;

import java.util.HashMap;
import java.util.Map;

public class WordPatternBuilder {

    private static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(2, "[abc]{1}");
        map.put(3, "[def]{1}");
        map.put(4, "[ghi]{1}");
        map.put(5, "[jkl]{1}");
        map.put(6, "[mno]{1}");
        map.put(7, "[pqrs]{1}");
        map.put(8, "[tuv]{1}");
        map.put(9, "[wxyz]{1}");
    }

    public static String getPattern(long number){
        String pattern = "";
        String str = "" + number;

        final int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            int integer = Character.getNumericValue(c);
            pattern += map.get(integer);
        }

        return pattern;
    }

}
