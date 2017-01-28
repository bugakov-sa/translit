package translit.impl;

import translit.Converter;
import translit.ConverterFactory;
import translit.StatFactory;
import translit.TranslitFactory;

public class DefaultConverterFactory implements ConverterFactory {

    private final StatFactory seqStatFactory;
    private final StatFactory wordStatFactory;
    private final TranslitFactory translitFactory;

    public DefaultConverterFactory(
            StatFactory seqStatFactory,
            StatFactory wordStatFactory,
            TranslitFactory translitFactory
    ) {
        this.seqStatFactory = seqStatFactory;
        this.wordStatFactory = wordStatFactory;
        this.translitFactory = translitFactory;
    }

    public Converter create() throws Exception {
        return new DefaultConverter(seqStatFactory.create(), wordStatFactory.create(), translitFactory.create());
    }
}
