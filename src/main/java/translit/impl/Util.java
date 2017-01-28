package translit.impl;

import translit.Translit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Util {

    public static final String END_CHAR = "#";

    public static List<String> translations(String word, Translit translit) {
        return translations("", word, translit);
    }

    private static List<String> translations(String translation, String tail, Translit translit) {
        if(tail.isEmpty()) {
            return Collections.singletonList(translation);
        }
        List<String> res = new ArrayList<>();
        for(int i = 1; i <= tail.length(); i++) {
            for(String s : translit.translit(tail.substring(0, i))) {
                res.addAll(translations(translation + s, tail.substring(i), translit));
            }
        }
        return new ArrayList<>(new HashSet<>(res));
    }
}
