package translit.impl;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

public class StatAccumulatorTest {

    private void test(int seqLength, String[] words, int[] freqs, String expectedStat) {
        StatAccumulator statAccumulator = new StatAccumulator(seqLength);
        for (int i = 0; i < words.length; i++) {
            statAccumulator.add(words[i], freqs[i]);
        }
        final Map<String, Long> sortedStat = new TreeMap<>(statAccumulator.getStat());
        System.out.println(sortedStat);
        Assert.assertEquals(expectedStat, sortedStat.toString());
    }

    @Test
    public void test1() {
        test(1,
                new String[]{"yandex", "mail", "google"},
                new int[]{10, 1, 100},
                "{a=11, d=10, e=110, g=200, i=1, l=101, m=1, n=10, o=200, x=10, y=10}");
    }

    @Test
    public void test2() {
        test(2,
                new String[]{"sergey", "alexey"},
                new int[]{10, 12},
                "{al=12, er=10, ex=12, ey=22, ge=10, le=12, rg=10, se=10, xe=12}");
    }

    @Test
    public void test3() {
        test(3,
                new String[]{"sergey", "serg"},
                new int[]{10, 12},
                "{erg=22, gey=10, rge=10, ser=22}");
    }
}
