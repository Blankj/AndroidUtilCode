package com.blankj.utilcode.util;

import android.util.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/12
 *     desc  : utils about map
 * </pre>
 */
public class MapUtils {

    private MapUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Returns a new read-only map with the specified contents, given as a list of pairs
     * where the first value is the key and the second is the value.
     *
     * @param pairs a list of pairs
     * @return a new read-only map with the specified contents
     */
    @SafeVarargs
    public static <K, V> Map<K, V> newUnmodifiableMap(final Pair<K, V>... pairs) {
        return Collections.unmodifiableMap(newHashMap(pairs));
    }

    @SafeVarargs
    public static <K, V> HashMap<K, V> newHashMap(final Pair<K, V>... pairs) {
        HashMap<K, V> map = new HashMap<>();
        if (pairs == null || pairs.length == 0) {
            return map;
        }
        for (Pair<K, V> pair : pairs) {
            if (pair == null) continue;
            map.put(pair.first, pair.second);
        }
        return map;
    }

    @SafeVarargs
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(final Pair<K, V>... pairs) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>();
        if (pairs == null || pairs.length == 0) {
            return map;
        }
        for (Pair<K, V> pair : pairs) {
            if (pair == null) continue;
            map.put(pair.first, pair.second);
        }
        return map;
    }

    @SafeVarargs
    public static <K, V> TreeMap<K, V> newTreeMap(final Comparator<K> comparator,
                                                  final Pair<K, V>... pairs) {
        if (comparator == null) {
            throw new IllegalArgumentException("comparator must not be null");
        }
        TreeMap<K, V> map = new TreeMap<>(comparator);
        if (pairs == null || pairs.length == 0) {
            return map;
        }
        for (Pair<K, V> pair : pairs) {
            if (pair == null) continue;
            map.put(pair.first, pair.second);
        }
        return map;
    }

    @SafeVarargs
    public static <K, V> Hashtable<K, V> newHashTable(final Pair<K, V>... pairs) {
        Hashtable<K, V> map = new Hashtable<>();
        if (pairs == null || pairs.length == 0) {
            return map;
        }
        for (Pair<K, V> pair : pairs) {
            if (pair == null) continue;
            map.put(pair.first, pair.second);
        }
        return map;
    }

    /**
     * Null-safe check if the specified map is empty.
     * <p>
     * Null returns true.
     *
     * @param map the map to check, may be null
     * @return true if empty or null
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    /**
     * Null-safe check if the specified map is not empty.
     * <p>
     * Null returns false.
     *
     * @param map the map to check, may be null
     * @return true if non-null and non-empty
     */
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    /**
     * Gets the size of the map specified.
     *
     * @param map The map.
     * @return the size of the map specified
     */
    public static int size(Map map) {
        if (map == null) return 0;
        return map.size();
    }

    /**
     * Executes the given closure on each element in the collection.
     * <p>
     * If the input collection or closure is null, there is no change made.
     *
     * @param map     the map to get the input from, may be null
     * @param closure the closure to perform, may be null
     */
    public static <K, V> void forAllDo(Map<K, V> map, Closure<K, V> closure) {
        if (map == null || closure == null) return;
        for (Map.Entry<K, V> kvEntry : map.entrySet()) {
            closure.execute(kvEntry.getKey(), kvEntry.getValue());
        }
    }

    /**
     * Transform the map by applying a Transformer to each element.
     * <p>
     * If the input map or transformer is null, there is no change made.
     *
     * @param map         the map to get the input from, may be null
     * @param transformer the transformer to perform, may be null
     */
    public static <K1, V1, K2, V2> Map<K2, V2> transform(Map<K1, V1> map, final Transformer<K1, V1, K2, V2> transformer) {
        if (map == null || transformer == null) return null;
        try {
            final Map<K2, V2> transMap = map.getClass().newInstance();
            forAllDo(map, new Closure<K1, V1>() {
                @Override
                public void execute(K1 key, V1 value) {
                    Pair<K2, V2> pair = transformer.transform(key, value);
                    transMap.put(pair.first, pair.second);
                }
            });
            return transMap;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return the string of map.
     *
     * @param map The map.
     * @return the string of map
     */
    public static String toString(Map map) {
        if (map == null) return "null";
        return map.toString();
    }

    public interface Closure<K, V> {
        void execute(K key, V value);
    }

    public interface Transformer<K1, V1, K2, V2> {
        Pair<K2, V2> transform(K1 k1, V1 v1);
    }
}
