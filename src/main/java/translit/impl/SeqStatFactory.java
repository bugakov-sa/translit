package translit.impl;

import translit.Stat;
import translit.StatFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SeqStatFactory implements StatFactory {

    private final Path path;
    private final int maxSeqLength;

    public SeqStatFactory(Path path, int maxSeqLength) {
        this.path = path;
        this.maxSeqLength = maxSeqLength;
    }

    @Override
    public Stat create() throws Exception {
        final List<StatAccumulator> accumulators = new ArrayList<>();
        for (int seqLength = 1; seqLength <= maxSeqLength; seqLength++) {
            accumulators.add(new StatAccumulator(seqLength));
        }
        Files.readAllLines(path).stream().forEach(line -> {
            String word = line.split("\t")[0];
            long freq = Long.valueOf(line.split("\t")[1]);
            for (StatAccumulator statAccumulator : accumulators) {
                statAccumulator.add(word + Util.END_CHAR, freq);
            }
        });
        return s -> {
            if (accumulators.size() < s.length()) {
                return -1;
            }
            return accumulators.get(s.length() - 1).getStat().getOrDefault(s, 0L);
        };
    }
}
