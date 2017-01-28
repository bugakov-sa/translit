package translit.impl;

import translit.Converter;
import translit.Stat;
import translit.Translit;

import java.util.*;

import static java.util.Collections.max;
import static java.util.Collections.singletonList;
import static translit.impl.Util.END_CHAR;

public class DefaultConverter implements Converter {

    private final Stat stat;
    private final Translit translit;

    public DefaultConverter(Stat stat, Translit translit) {
        this.stat = stat;
        this.translit = translit;
    }

    public List<String> convert(String s) {
        return singletonList(max(Util.translations(s, translit),
                (o1, o2) -> weight(o1 + END_CHAR).compareTo(weight(o2 + END_CHAR))));
    }

    private Double weight(String tl) {
        final int c = 10;
        return c * c * c * avgFreq(tl, 5) + c * c * avgFreq(tl, 4) + c * avgFreq(tl, 3) + avgFreq(tl, 2);
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
