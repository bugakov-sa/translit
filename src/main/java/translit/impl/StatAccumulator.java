package translit.impl;

import java.util.HashMap;
import java.util.Map;

public class StatAccumulator {

    private final Map<String, Long> stat = new HashMap<>();
    private final int seqLength;

    public StatAccumulator(int seqLength) {
        this.seqLength = seqLength;
    }

    public void add(String word, long freq) {
        if(word.length() >= seqLength) {
            for(int i = 0; i < word.length() - seqLength + 1; i++) {
                String word3 = word.substring(i, i + seqLength);
                if(!stat.containsKey(word3)) {
                    stat.put(word3, 0L);
                }
                stat.put(word3, stat.get(word3) + freq);
            }
        }
    }

    public Map<String, Long> getStat() {
        return stat;
    }
}
