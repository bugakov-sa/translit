package translit.impl;

import translit.Translit;
import translit.TranslitFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ReverseFromFileTranslitFactory implements TranslitFactory {

    private final Path path;

    public ReverseFromFileTranslitFactory(Path path) {
        this.path = path;
    }

    public Translit create() throws Exception {
        final Map<String, List<String>> map = new HashMap<>();
        Files.readAllLines(path).stream().forEach(line -> {
            String key = line.split("=")[0];
            List<String> value = new ArrayList<>();
            for(String s : line.split("=")[1].split(",")) value.add(s);
            map.put(key, value);
        });
        final Map<String, List<String>> reverseMap = new HashMap<>();
        for(String key : map.keySet()) {
            for(String value : map.get(key)) {
                if(!reverseMap.containsKey(value)) {
                    reverseMap.put(value, new ArrayList<>());
                }
                if(!reverseMap.get(value).contains(key)) {
                    reverseMap.get(value).add(key);
                }
            }
        }
        return s -> reverseMap.getOrDefault(s, Collections.EMPTY_LIST);
    }
}