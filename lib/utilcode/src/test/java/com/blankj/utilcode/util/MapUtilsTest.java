package com.blankj.utilcode.util;

import android.util.Pair;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.Map;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/14
 *     desc  : test MapUtils
 * </pre>
 */
public class MapUtilsTest extends BaseTest {

    @Test
    public void newUnmodifiableMap() {
        System.out.println(MapUtils.newUnmodifiableMap(
                Pair.create(0, "0"),
                Pair.create(1, "1"),
                Pair.create(2, "2"),
                Pair.create(3, "3")
        ));
    }

    @Test
    public void newHashMap() {
        System.out.println(MapUtils.newHashMap(
                Pair.create(0, "0"),
                Pair.create(1, "1"),
                Pair.create(2, "2"),
                Pair.create(3, "3")
        ));
    }

    @Test
    public void newLinkedHashMap() {
        System.out.println(MapUtils.newLinkedHashMap(
                Pair.create(0, "0"),
                Pair.create(1, "1"),
                Pair.create(2, "2"),
                Pair.create(3, "3")
        ));
    }

    @Test
    public void newTreeMap() {
        System.out.println(MapUtils.newTreeMap(
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                },
                Pair.create(0, "0"),
                Pair.create(1, "1"),
                Pair.create(2, "2"),
                Pair.create(3, "3")
        ));
    }

    @Test
    public void newHashTable() {
        System.out.println(MapUtils.newHashTable(
                Pair.create(0, "0"),
                Pair.create(1, "1"),
                Pair.create(2, "2"),
                Pair.create(3, "3")
        ));
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue(MapUtils.isEmpty(null));
        Assert.assertTrue(MapUtils.isEmpty(MapUtils.newHashMap()));
        Assert.assertFalse(MapUtils.isEmpty(MapUtils.newHashMap(Pair.create(0, 0))));
    }

    @Test
    public void isNotEmpty() {
        Assert.assertFalse(MapUtils.isNotEmpty(null));
        Assert.assertFalse(MapUtils.isNotEmpty(MapUtils.newHashMap()));
        Assert.assertTrue(MapUtils.isNotEmpty(MapUtils.newHashMap(Pair.create(0, 0))));
    }

    @Test
    public void size() {
        Assert.assertEquals(0, MapUtils.size(null));
        Assert.assertEquals(0, MapUtils.size(MapUtils.newHashMap()));
        Assert.assertEquals(1, MapUtils.size(MapUtils.newHashMap(Pair.create(0, 0))));
    }

    @Test
    public void forAllDo() {
        MapUtils.forAllDo(
                MapUtils.newHashMap(
                        Pair.create(0, "0"),
                        Pair.create(1, "1"),
                        Pair.create(2, "2"),
                        Pair.create(3, "3")
                ), new MapUtils.Closure<Integer, String>() {
                    @Override
                    public void execute(Integer key, String value) {
                        System.out.println(key + "=" + value);
                    }
                });
    }

    @Test
    public void transform() {
        Map<String, String> transform = MapUtils.transform(
                MapUtils.newHashMap(
                        Pair.create(0, "0"),
                        Pair.create(1, "1"),
                        Pair.create(2, "2"),
                        Pair.create(3, "3")
                ),
                new MapUtils.Transformer<Integer, String, String, String>() {
                    @Override
                    public Pair<String, String> transform(Integer integer, String s) {
                        return Pair.create("StringKey" + integer, s);
                    }
                }
        );
        System.out.println(transform);
    }
}