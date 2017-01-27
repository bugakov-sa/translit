package translit.impl;

import translit.Converter;
import translit.ConverterFactory;
import translit.StatFactory;
import translit.TranslitFactory;

public class DefaultConverterFactory implements ConverterFactory {

    private final StatFactory statFactory;
    private final TranslitFactory translitFactory;

    public DefaultConverterFactory(StatFactory statFactory, TranslitFactory translitFactory) {
        this.statFactory = statFactory;
        this.translitFactory = translitFactory;
    }

    public Converter create() throws Exception {
        return new DefaultConverter(statFactory.create(), translitFactory.create());
    }
}
