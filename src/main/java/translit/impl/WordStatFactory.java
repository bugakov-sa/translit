package translit.impl;

import translit.Stat;
import translit.StatFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class WordStatFactory implements StatFactory {

    private final Path path;

    public WordStatFactory(Path path) {
        this.path = path;
    }

    @Override
    public Stat create() throws Exception {
        final Map<String, Long> map = new HashMap<>();
        Files.readAllLines(path).stream().forEach(line -> {
            String word = line.split("\t")[0];
            long freq = Long.valueOf(line.split("\t")[1]);
            map.put(word, freq);
        });
        return s -> map.getOrDefault(s, 0L);
    }
}
