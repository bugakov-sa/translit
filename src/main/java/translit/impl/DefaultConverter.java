package translit.impl;

import translit.Converter;
import translit.Stat;
import translit.Translit;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.max;
import static java.util.Collections.singletonList;
import static translit.impl.Util.END_CHAR;

public class DefaultConverter implements Converter {

    private final Stat seqStat;
    private final Stat wordStat;
    private final Translit translit;

    public DefaultConverter(Stat seqStat, Stat wordStat, Translit translit) {
        this.seqStat = seqStat;
        this.wordStat = wordStat;
        this.translit = translit;
    }

    public List<String> convert(String s) {
        final List<String> translations = Util.translations(s, translit);
        final List<String> existTranslations = translations
                .stream()
                .filter(tl -> wordStat.freq(tl) > 0)
                .collect(Collectors.toList());
        if(existTranslations.isEmpty()) {
            return singletonList(max(translations,
                    (o1, o2) -> weight(o1).compareTo(weight(o2))));
        }
        else {
            return singletonList(max(existTranslations,
                    (o1, o2) -> wordStat.freq(o1).compareTo(wordStat.freq(o2))));
        }
    }

    private Double weight(String tl) {
        final String tl2 = tl + END_CHAR;
        final int c = 10;
        return c * c * c * avgFreq(tl2, 5) + c * c * avgFreq(tl2, 4) + c * avgFreq(tl2, 3) + avgFreq(tl2, 2);
    }

    /**
     * Вычисляет среднюю частоту встречаемости подстрок строки tl длиной seqLength
     */
    private Double avgFreq(String tl, int seqLength) {
        if (tl.length() >= seqLength) {
            int n = tl.length() - seqLength + 1;
            double sum = 0d;
            for (int i = 0; i < n; i++) {
                sum += seqStat.freq(tl.substring(i, i + seqLength));
            }
            return sum / n;
        }
        return 0d;
    }
}
