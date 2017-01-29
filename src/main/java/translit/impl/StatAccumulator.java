package translit.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * Считает частоты употребления последовательностей символов длиной seqLength
 */
public class StatAccumulator {

    private final Map<String, Long> stat = new HashMap<>();
    private final int seqLength;

    public StatAccumulator(int seqLength) {
        this.seqLength = seqLength;
    }

    public void add(String word, long freq) {
        if(word.length() >= seqLength) {
            for(int i = 0; i < word.length() - seqLength + 1; i++) {
                String seq = word.substring(i, i + seqLength);
                if(!stat.containsKey(seq)) {
                    stat.put(seq, 0L);
                }
                stat.put(seq, stat.get(seq) + freq);
            }
        }
    }

    public Map<String, Long> getStat() {
        return stat;
    }
}
