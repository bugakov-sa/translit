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
        for (String word : translations) {
            weights.add(weight(word + Util.END_CHAR));
        }
        Double maxWeight = Collections.max(weights);
        List<String> res = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            if (weights.get(i).equals(maxWeight)) {
                res.add(translations.get(i));
            }
        }
        return res;
    }

    private Double weight(String tl) {
        final int c = 10;
        switch (tl.length()) {
            case 1:
                return 0d;
            case 2:
                return 0d;
            case 3:
                return c * avgFreq(tl, 3) + avgFreq(tl, 2);
            case 4:
                return c * c * avgFreq(tl, 4) + c * avgFreq(tl, 3);
            default:
                return c * c * c * avgFreq(tl, 5) + c * c * avgFreq(tl, 4) + c * avgFreq(tl, 3);
        }
    }

    private Double avgFreq(String tl, int seqLength) {
        if (tl.length() >= seqLength) {
            int n = tl.length() - seqLength + 1;
            double sum = 0d;
            for (int i = 0; i < n; i++) {
                sum += stat.freq(tl.substring(i, i + seqLength));
            }
            return sum / n;
        }
        return 0d;
    }
}
