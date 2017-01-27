package translit.impl;

import translit.Translit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Util {

    public static List<List<String>> fragmentations(String word) {
        return fragmentations(new ArrayList<>(), word);
    }

    private static List<List<String>> fragmentations(List<String> fragmentation, String tail) {
        if(tail.isEmpty()) {
            return Collections.singletonList(fragmentation);
        }
        List<List<String>> res = new ArrayList<>();
        for(int i = 1; i <= tail.length(); i++) {
            List<String> newFragmentation = new ArrayList<>(fragmentation);
            newFragmentation.add(tail.substring(0, i));
            res.addAll(fragmentations(newFragmentation, tail.substring(i)));
        }
        return new ArrayList<>(new HashSet<>(res));
    }

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
