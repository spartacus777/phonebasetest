package phonebase.android.kizema.phonebasetestapp.util;

import java.util.HashMap;
import java.util.Map;

public class WordToDigitHelper {

    private static final Map<Character, Integer> map = new HashMap<>(26);

    static {
        map.put('a', 2);
        map.put('b', 2);
        map.put('c', 2);

        map.put('d', 3);
        map.put('e', 3);
        map.put('f', 3);

        map.put('g', 4);
        map.put('h', 4);
        map.put('i', 4);

        map.put('j', 5);
        map.put('k', 5);
        map.put('l', 5);

        map.put('m', 6);
        map.put('n', 6);
        map.put('o', 6);

        map.put('p', 7);
        map.put('q', 7);
        map.put('r', 7);
        map.put('s', 7);

        map.put('t', 8);
        map.put('u', 8);
        map.put('v', 8);

        map.put('w', 9);
        map.put('x', 9);
        map.put('y', 9);
        map.put('z', 9);
    }

    public static String getNumber(String word){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i< word.length(); ++i){
            Integer val = map.get( word.charAt(i));
            if (val != null){
                s.append(val);
            }
        }

        return s.toString();
    }
}
