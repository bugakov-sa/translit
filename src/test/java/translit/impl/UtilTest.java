package translit.impl;

import org.junit.Assert;
import org.junit.Test;
import translit.Translit;

import java.util.*;

public class UtilTest {

    private final Translit translit = new Translit() {
        Map<String, List<String>> map = new HashMap<>();
        {
            map.put("ya", Arrays.asList("я", "йа"));
            map.put("n", Arrays.asList("н"));
            map.put("d", Arrays.asList("д"));
            map.put("e", Arrays.asList("е", "э"));
            map.put("x", Arrays.asList("кс"));
        }
        @Override
        public List<String> translit(String s) {
            return map.getOrDefault(s, Collections.EMPTY_LIST);
        }
    };

    private void testTranslations(String expected, String word) {
        Assert.assertEquals(expected, Util.translations(word, translit).toString());
    }

    @Test
    public void testTranslations1(){
        testTranslations("[яндекс, яндэкс, йандекс, йандэкс]", "yandex");
    }
}
