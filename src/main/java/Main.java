import translit.Converter;
import translit.ConverterFactory;
import translit.StatFactory;
import translit.TranslitFactory;
import translit.impl.*;

import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        final long startMillis = System.currentTimeMillis();
        System.out.println("Collecting stat...");
        StatFactory seqStatFactory = new SeqStatFactory(Paths.get(
                System.getProperty("user.dir"),
                "data",
                "names.stats"
        ), 5);
        StatFactory wordStatFactory = new WordStatFactory(Paths.get(
                System.getProperty("user.dir"),
                "data",
                "names.stats"
        ));
        TranslitFactory translitFactory = new DefaultTranslitFactory(Paths.get(
                System.getProperty("user.dir"),
                "data",
                "translit.txt"
        ));
        TranslitFactory reverseTranslitFactory = new ReverseTranslitFactory(Paths.get(
                System.getProperty("user.dir"),
                "data",
                "translit.txt"
        ));
        ConverterFactory converterFactory = new DefaultConverterFactory(
                seqStatFactory,
                wordStatFactory, translitFactory
        );
        Converter converter = converterFactory.create();
        ConverterFactory reverseConverterFactory = new DefaultConverterFactory(
                seqStatFactory,
                wordStatFactory, reverseTranslitFactory
        );
        Converter reverseConverter = reverseConverterFactory.create();

        System.out.println("Stat collected during " + (System.currentTimeMillis() - startMillis) + " millis");
        System.out.println("Translating words...");
        System.out.println();
        final long startMillis2 = System.currentTimeMillis();
        String[] words = {
            "владимир", "алексей", "борис", "олег", "александр", "сергей", "ольга", "ирина", "мария", "екатерина",
                "наташа", "энтони", "джульетта", "нассим", "алекс", "билл", "йозеф", "юрий", "инесса", "адамар",
                "мандельброт", "коши", "холмс", "ватсон", "али", "иванов", "петров", "достоевский", "пушкин",
                "рюрикович", "смит", "святослав", "сара", "лариса", "иннокентий", "фридрих", "хайзенберг", "сэм", "курт",
                "робин", "жанна", "бернанке", "толстой", "тухачевский", "гудериан", "берия", "джонсон", "ли", "флин",
                "густаво", "гринспен", "рафаэль", "отто", "кольт", "саймонс", "роберт", "ниро", "ксения", "ким",
                "фробениус", "евклид", "пифагор", "моцарт"
        };
        for(String word : words) {
            List<String> list = converter.convert(word);
            System.out.println(word);
            System.out.println(list);
            for(String s : list) {
                System.out.println(reverseConverter.convert(s));
            }
            System.out.println();
        }
        long convertionMillis = (System.currentTimeMillis() - startMillis2);
        System.out.println(words.length + " words translated during " + convertionMillis + " millis");
    }
}
