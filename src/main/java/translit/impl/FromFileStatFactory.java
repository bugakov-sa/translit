package translit.impl;

import translit.Stat;
import translit.StatFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FromFileStatFactory implements StatFactory {

    private final Path path;

    public FromFileStatFactory(Path path) {
        this.path = path;
    }

    public Stat create() throws Exception {
        final Map<String, Long> map = new HashMap<>();
        Files.readAllLines(path).stream().forEach(line -> {
            map.put(line.split("\t")[0], Long.valueOf(line.split("\t")[1]));
        });
        return s -> map.getOrDefault(s, 0L);
    }
}
