package translit.impl;

import translit.Translit;
import translit.TranslitFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DefaultTranslitFactory implements TranslitFactory {

    private final Path path;

    public DefaultTranslitFactory(Path path) {
        this.path = path;
    }

    public Translit create() throws Exception {
        final Map<String, List<String>> map = new HashMap<>();
        Files.readAllLines(path).stream().forEach(line -> {
            String key = line.split("=")[0];
            List<String> value = new ArrayList<String>();
            for (String s : line.split("=")[1].split(",")) value.add(s);
            map.put(key, value);
        });
        return s -> map.getOrDefault(s, Collections.EMPTY_LIST);
    }
}
