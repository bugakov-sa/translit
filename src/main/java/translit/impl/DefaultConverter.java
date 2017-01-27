package translit.impl;

import translit.Converter;
import translit.Stat;
import translit.Translit;

import java.util.*;

public class DefaultConverter implements Converter {

    private final Stat stat;
    private final Translit translit;

    public DefaultConverter(Stat stat, Translit translit) {
        this.stat = stat;
        this.translit = translit;
    }

    public List<String> convert(String s) {
        List<String> translations = Util.translations(s, translit);
        List<Double> weights = new ArrayList<>();
        for(String translation : translations) {
            weights.add(weight(translation));
        }
        Double maxWeight = Collections.max(weights);
        List<String> res = new ArrayList<>();
        for(int i = 0; i < weights.size(); i++) {
            if(weights.get(i).equals(maxWeight)) {
                res.add(translations.get(i));
            }
        }
        return res;
    }

    private Double weight(String tl) {
        return Util.fragmentations(tl).stream().map(this::weight2).max(Double::compare).get();
    }

    private Double weight2(List<String> tl) {
        double acc = 0;
        for(String s : tl) {
            acc += stat.freq(s) * s.length() * s.length();
        }
        return acc / (tl.size() * tl.size());
    }
}
