import translit.Converter;
import translit.ConverterFactory;
import translit.StatFactory;
import translit.TranslitFactory;
import translit.impl.DefaultConverterFactory;
import translit.impl.FromFileStatFactory;
import translit.impl.FromFileTranslitFactory;
import translit.impl.ReverseFromFileTranslitFactory;

import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        final long startMillis = System.currentTimeMillis();
        StatFactory statFactory = new FromFileStatFactory(Paths.get(
                System.getProperty("user.dir"),
                "data",
                "names.stats"
        ));
        TranslitFactory translitFactory = new FromFileTranslitFactory(Paths.get(
                System.getProperty("user.dir"),
                "data",
                "translit.txt"
        ));
        TranslitFactory reverseTranslitFactory = new ReverseFromFileTranslitFactory(Paths.get(
                System.getProperty("user.dir"),
                "data",
                "translit.txt"
        ));
        ConverterFactory converterFactory = new DefaultConverterFactory(
                statFactory,
                translitFactory
        );
        Converter converter = converterFactory.create();
        ConverterFactory reverseConverterFactory = new DefaultConverterFactory(
                statFactory,
                reverseTranslitFactory
        );
        Converter reverseConverter = reverseConverterFactory.create();

        String[] words = {
            "владимир",
                "алексей",
                "борис",
                "олег",
                "александр",
                "сергей",
                "ольга",
                "ирина",
                "мария",
                "екатерина",
                "наташа",
                "энтони",
                "джульетта",
                "нассим",
                "алекс",
                "билл",
                "йозеф",
                "юрий",
                "инесса",
                "адамар",
                "мандельброт",
                "коши",
                "холмс",
                "ватсон",
                "али",
                "иванов",
                "петров",
                "достоевский",
                "пушкин",
                "рюрикович"
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
        System.out.println("Total millis: " + (System.currentTimeMillis() - startMillis));
    }
}
