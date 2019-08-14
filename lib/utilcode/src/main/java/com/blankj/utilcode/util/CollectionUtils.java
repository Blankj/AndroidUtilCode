package com.blankj.utilcode.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/26
 *     desc  : utils about collection
 * </pre>
 */
public final class CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    ///////////////////////////////////////////////////////////////////////////
    // listOf
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns a new read-only list of given elements.
     *
     * @param array The array.
     * @return a new read-only list of given elements
     */
    @SafeVarargs
    public static <E> List<E> newUnmodifiableList(E... array) {
        return Collections.unmodifiableList(newArrayList(array));
    }

    /**
     * Returns a new read-only list only of those given elements, that are not null.
     *
     * @param array The array.
     * @return a new read-only list only of those given elements, that are not null
     */
    @SafeVarargs
    public static <E> List<E> newUnmodifiableListNotNull(E... array) {
        return Collections.unmodifiableList(newArrayListNotNull(array));
    }

    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... array) {
        ArrayList<E> list = new ArrayList<>();
        if (array == null || array.length == 0) return list;
        for (E e : array) {
            list.add(e);
        }
        return list;
    }

    @SafeVarargs
    public static <E> ArrayList<E> newArrayListNotNull(E... array) {
        ArrayList<E> list = new ArrayList<>();
        if (array == null || array.length == 0) return list;
        for (E e : array) {
            if (e == null) continue;
            list.add(e);
        }
        return list;
    }

    @SafeVarargs
    public static <E> List<E> newLinkedList(E... array) {
        LinkedList<E> list = new LinkedList<>();
        if (array == null || array.length == 0) return list;
        for (E e : array) {
            list.add(e);
        }
        return list;
    }

    @SafeVarargs
    public static <E> List<E> newLinkedListNotNull(E... array) {
        LinkedList<E> list = new LinkedList<>();
        if (array == null || array.length == 0) return list;
        for (E e : array) {
            if (e == null) continue;
            list.add(e);
        }
        return list;
    }

    @SafeVarargs
    public static <E> HashSet<E> newHashSet(E... array) {
        HashSet<E> set = new HashSet<>();
        if (array == null || array.length == 0) return set;
        for (E e : array) {
            set.add(e);
        }
        return set;
    }

    @SafeVarargs
    public static <E> HashSet<E> newHashSetNotNull(E... array) {
        HashSet<E> set = new HashSet<>();
        if (array == null || array.length == 0) return set;
        for (E e : array) {
            if (e == null) continue;
            set.add(e);
        }
        return set;
    }

    @SafeVarargs
    public static <E> TreeSet<E> newTreeSet(Comparator<E> comparator, E... array) {
        TreeSet<E> set = new TreeSet<>(comparator);
        if (array == null || array.length == 0) return set;
        for (E e : array) {
            set.add(e);
        }
        return set;
    }

    @SafeVarargs
    public static <E> TreeSet<E> newTreeSetNotNull(Comparator<E> comparator, E... array) {
        TreeSet<E> set = new TreeSet<>(comparator);
        if (array == null || array.length == 0) return set;
        for (E e : array) {
            if (e == null) continue;
            set.add(e);
        }
        return set;
    }

    public static Collection newSynchronizedCollection(Collection collection) {
        return Collections.synchronizedCollection(collection);
    }

    public static Collection newUnmodifiableCollection(Collection collection) {
        return Collections.unmodifiableCollection(collection);
    }

    /**
     * Returns a {@link Collection} containing the union
     * of the given {@link Collection}s.
     * <p>
     * The cardinality of each element in the returned {@link Collection}
     * will be equal to the maximum of the cardinality of that element
     * in the two given {@link Collection}s.
     *
     * @param a the first collection
     * @param b the second collection
     * @return the union of the two collections
     * @see Collection#addAll
     */
    public static Collection union(final Collection a, final Collection b) {
        if (a == null && b == null) return new ArrayList();
        if (a == null) return new ArrayList<Object>(b);
        if (b == null) return new ArrayList<Object>(a);
        ArrayList<Object> list = new ArrayList<>();
        Map<Object, Integer> mapA = getCardinalityMap(a);
        Map<Object, Integer> mapB = getCardinalityMap(b);
        Set<Object> elts = new HashSet<Object>(a);
        elts.addAll(b);
        for (Object obj : elts) {
            for (int i = 0, m = Math.max(getFreq(obj, mapA), getFreq(obj, mapB)); i < m; i++) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * Returns a {@link Collection} containing the intersection
     * of the given {@link Collection}s.
     * <p>
     * The cardinality of each element in the returned {@link Collection}
     * will be equal to the minimum of the cardinality of that element
     * in the two given {@link Collection}s.
     *
     * @param a the first collection
     * @param b the second collection
     * @return the intersection of the two collections
     * @see Collection#retainAll
     */
    public static Collection intersection(final Collection a, final Collection b) {
        if (a == null || b == null) return new ArrayList();
        ArrayList<Object> list = new ArrayList<>();
        Map mapA = getCardinalityMap(a);
        Map mapB = getCardinalityMap(b);
        Set<Object> elts = new HashSet<Object>(a);
        elts.addAll(b);
        for (Object obj : elts) {
            for (int i = 0, m = Math.min(getFreq(obj, mapA), getFreq(obj, mapB)); i < m; i++) {
                list.add(obj);
            }
        }
        return list;
    }

    private static int getFreq(final Object obj, final Map freqMap) {
        Integer count = (Integer) freqMap.get(obj);
        if (count != null) {
            return count;
        }
        return 0;
    }

    /**
     * Returns a {@link Collection} containing the exclusive disjunction
     * (symmetric difference) of the given {@link Collection}s.
     * <p>
     * The cardinality of each element <i>e</i> in the returned {@link Collection}
     * will be equal to
     * <tt>max(cardinality(<i>e</i>,<i>a</i>),cardinality(<i>e</i>,<i>b</i>)) - min(cardinality(<i>e</i>,<i>a</i>),cardinality(<i>e</i>,<i>b</i>))</tt>.
     * <p>
     * This is equivalent to
     * <tt>{@link #subtract subtract}({@link #union union(a,b)},{@link #intersection intersection(a,b)})</tt>
     * or
     * <tt>{@link #union union}({@link #subtract subtract(a,b)},{@link #subtract subtract(b,a)})</tt>.
     *
     * @param a the first collection
     * @param b the second collection
     * @return the symmetric difference of the two collections
     */
    public static Collection disjunction(final Collection a, final Collection b) {
        if (a == null && b == null) return new ArrayList();
        if (a == null) return new ArrayList<Object>(b);
        if (b == null) return new ArrayList<Object>(a);
        ArrayList<Object> list = new ArrayList<>();
        Map mapA = getCardinalityMap(a);
        Map mapB = getCardinalityMap(b);
        Set<Object> elts = new HashSet<Object>(a);
        elts.addAll(b);
        for (Object obj : elts) {
            for (int i = 0, m = ((Math.max(getFreq(obj, mapA), getFreq(obj, mapB)))
                    - (Math.min(getFreq(obj, mapA), getFreq(obj, mapB)))); i < m; i++) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * Returns a new {@link Collection} containing <tt><i>a</i> - <i>b</i></tt>.
     * The cardinality of each element <i>e</i> in the returned {@link Collection}
     * will be the cardinality of <i>e</i> in <i>a</i> minus the cardinality
     * of <i>e</i> in <i>b</i>, or zero, whichever is greater.
     *
     * @param a the collection to subtract from
     * @param b the collection to subtract
     * @return a new collection with the results
     * @see Collection#removeAll
     */
    public static Collection subtract(final Collection a, final Collection b) {
        if (a == null) return new ArrayList();
        if (b == null) return new ArrayList<Object>(a);
        ArrayList<Object> list = new ArrayList<Object>(a);
        for (Object o : b) {
            list.remove(o);
        }
        return list;
    }

    /**
     * Returns <code>true</code> iff at least one element is in both collections.
     * <p>
     * In other words, this method returns <code>true</code> iff the
     * {@link #intersection} of <i>coll1</i> and <i>coll2</i> is not empty.
     *
     * @param coll1 the first collection
     * @param coll2 the first collection
     * @return <code>true</code> iff the intersection of the collections is non-empty
     * @see #intersection
     */
    public static boolean containsAny(final Collection coll1, final Collection coll2) {
        if (coll1 == null || coll2 == null) return false;
        if (coll1.size() < coll2.size()) {
            for (Object o : coll1) {
                if (coll2.contains(o)) {
                    return true;
                }
            }
        } else {
            for (Object o : coll2) {
                if (coll1.contains(o)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Returns a {@link Map} mapping each unique element in the given
     * {@link Collection} to an {@link Integer} representing the number
     * of occurrences of that element in the {@link Collection}.
     * <p>
     * Only those elements present in the collection will appear as
     * keys in the map.
     *
     * @param coll the collection to get the cardinality map for, must not be null
     * @return the populated cardinality map
     */
    public static Map<Object, Integer> getCardinalityMap(final Collection coll) {
        Map<Object, Integer> count = new HashMap<>();
        if (coll == null) return count;
        for (Object obj : coll) {
            Integer c = count.get(obj);
            if (c == null) {
                count.put(obj, 1);
            } else {
                count.put(obj, c + 1);
            }
        }
        return count;
    }

    /**
     * Returns <tt>true</tt> iff <i>a</i> is a sub-collection of <i>b</i>,
     * that is, iff the cardinality of <i>e</i> in <i>a</i> is less
     * than or equal to the cardinality of <i>e</i> in <i>b</i>,
     * for each element <i>e</i> in <i>a</i>.
     *
     * @param a the first (sub?) collection
     * @param b the second (super?) collection
     * @return <code>true</code> iff <i>a</i> is a sub-collection of <i>b</i>
     * @see #isProperSubCollection
     * @see Collection#containsAll
     */
    public static boolean isSubCollection(final Collection a, final Collection b) {
        if (a == null || b == null) return false;
        Map mapA = getCardinalityMap(a);
        Map mapB = getCardinalityMap(b);
        for (Object obj : a) {
            if (getFreq(obj, mapA) > getFreq(obj, mapB)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns <tt>true</tt> iff <i>a</i> is a <i>proper</i> sub-collection of <i>b</i>,
     * that is, iff the cardinality of <i>e</i> in <i>a</i> is less
     * than or equal to the cardinality of <i>e</i> in <i>b</i>,
     * for each element <i>e</i> in <i>a</i>, and there is at least one
     * element <i>f</i> such that the cardinality of <i>f</i> in <i>b</i>
     * is strictly greater than the cardinality of <i>f</i> in <i>a</i>.
     * <p>
     * The implementation assumes
     * <ul>
     * <li><code>a.size()</code> and <code>b.size()</code> represent the
     * total cardinality of <i>a</i> and <i>b</i>, resp. </li>
     * <li><code>a.size() < Integer.MAXVALUE</code></li>
     * </ul>
     *
     * @param a the first (sub?) collection
     * @param b the second (super?) collection
     * @return <code>true</code> iff <i>a</i> is a <i>proper</i> sub-collection of <i>b</i>
     * @see #isSubCollection
     * @see Collection#containsAll
     */
    public static boolean isProperSubCollection(final Collection a, final Collection b) {
        if (a == null || b == null) return false;
        return a.size() < b.size() && CollectionUtils.isSubCollection(a, b);
    }

    /**
     * Returns <tt>true</tt> iff the given {@link Collection}s contain
     * exactly the same elements with exactly the same cardinalities.
     * <p>
     * That is, iff the cardinality of <i>e</i> in <i>a</i> is
     * equal to the cardinality of <i>e</i> in <i>b</i>,
     * for each element <i>e</i> in <i>a</i> or <i>b</i>.
     *
     * @param a the first collection
     * @param b the second collection
     * @return <code>true</code> iff the collections contain the same elements with the same cardinalities.
     */
    public static boolean isEqualCollection(final Collection a, final Collection b) {
        if (a == null || b == null) return false;
        if (a.size() != b.size()) {
            return false;
        } else {
            Map mapA = getCardinalityMap(a);
            Map mapB = getCardinalityMap(b);
            if (mapA.size() != mapB.size()) {
                return false;
            } else {
                for (Object obj : mapA.keySet()) {
                    if (getFreq(obj, mapA) != getFreq(obj, mapB)) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    /**
     * Returns the number of occurrences of <i>obj</i> in <i>coll</i>.
     *
     * @param obj  the object to find the cardinality of
     * @param coll the collection to search
     * @return the the number of occurrences of obj in coll
     */
    public static <E> int cardinality(E obj, final Collection<E> coll) {
        if (coll == null) return 0;
        if (coll instanceof Set) {
            return (coll.contains(obj) ? 1 : 0);
        }
        int count = 0;
        if (obj == null) {
            for (E e : coll) {
                if (e == null) {
                    count++;
                }
            }
        } else {
            for (E e : coll) {
                if (obj.equals(e)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Finds the first element in the given collection which matches the given predicate.
     * <p>
     * If the input collection or predicate is null, or no element of the collection
     * matches the predicate, null is returned.
     *
     * @param collection the collection to search, may be null
     * @param predicate  the predicate to use, may be null
     * @return the first element of the collection which matches the predicate or null if none could be found
     */
    public static <E> E find(Collection<E> collection, Predicate<E> predicate) {
        if (collection == null || predicate == null) return null;
        for (E item : collection) {
            if (predicate.evaluate(item)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Executes the given closure on each element in the collection.
     * <p>
     * If the input collection or closure is null, there is no change made.
     *
     * @param collection the collection to get the input from, may be null
     * @param closure    the closure to perform, may be null
     */
    public static <E> void forAllDo(Collection<E> collection, Closure<E> closure) {
        if (collection == null || closure == null) return;
        int index = 0;
        for (E e : collection) {
            closure.execute(index++, e);
        }
    }

    /**
     * Filter the collection by applying a Predicate to each element. If the
     * predicate returns false, remove the element.
     * <p>
     * If the input collection or predicate is null, there is no change made.
     *
     * @param collection the collection to get the input from, may be null
     * @param predicate  the predicate to use as a filter, may be null
     */
    public static <E> void filter(Collection<E> collection, Predicate<E> predicate) {
        if (collection == null || predicate == null) return;
        for (Iterator it = collection.iterator(); it.hasNext(); ) {
            if (!predicate.evaluate((E) it.next())) {
                it.remove();
            }
        }
    }

    /**
     * Selects all elements from input collection which match the given predicate
     * into an output collection.
     * <p>
     * A <code>null</code> predicate matches no elements.
     *
     * @param inputCollection the collection to get the input from, may not be null
     * @param predicate       the predicate to use, may be null
     * @return the elements matching the predicate (new list)
     * @throws NullPointerException if the input collection is null
     */
    public static <E> Collection<E> select(Collection<E> inputCollection, Predicate<E> predicate) {
        if (inputCollection == null || predicate == null) return new ArrayList<>();
        ArrayList<E> answer = new ArrayList<>(inputCollection.size());
        for (E o : inputCollection) {
            if (predicate.evaluate(o)) {
                answer.add(o);
            }
        }
        return answer;
    }

    /**
     * Selects all elements from inputCollection which don't match the given predicate
     * into an output collection.
     * <p>
     * If the input predicate is <code>null</code>, the result is an empty list.
     *
     * @param inputCollection the collection to get the input from, may not be null
     * @param predicate       the predicate to use, may be null
     * @return the elements <b>not</b> matching the predicate (new list)
     * @throws NullPointerException if the input collection is null
     */
    public static <E> Collection<E> selectRejected(Collection<E> inputCollection, Predicate<E> predicate) {
        if (inputCollection == null || predicate == null) return new ArrayList<>();
        ArrayList<E> answer = new ArrayList<>(inputCollection.size());
        for (E o : inputCollection) {
            if (!predicate.evaluate(o)) {
                answer.add(o);
            }
        }
        return answer;
    }

    /**
     * Transform the collection by applying a Transformer to each element.
     * <p>
     * If the input collection or transformer is null, there is no change made.
     * <p>
     * This routine is best for Lists, for which set() is used to do the
     * transformations "in place."  For other Collections, clear() and addAll()
     * are used to replace elements.
     * <p>
     * If the input collection controls its input, such as a Set, and the
     * Transformer creates duplicates (or are otherwise invalid), the
     * collection may reduce in size due to calling this method.
     *
     * @param collection  the collection to get the input from, may be null
     * @param transformer the transformer to perform, may be null
     */
    public static <E1, E2> void transform(Collection<E1> collection, Transformer<E1, E2> transformer) {
        if (collection == null || transformer == null) return;
        if (collection instanceof List) {
            List list = (List) collection;
            for (ListIterator it = list.listIterator(); it.hasNext(); ) {
                it.set(transformer.transform((E1) it.next()));
            }
        } else {
            Collection resultCollection = collect(collection, transformer);
            collection.clear();
            collection.addAll(resultCollection);
        }
    }


    /**
     * Returns a new Collection consisting of the elements of inputCollection transformed
     * by the given transformer.
     * <p>
     * If the input transformer is null, the result is an empty list.
     *
     * @param inputCollection the collection to get the input from, may be null
     * @param transformer     the transformer to use, may be null
     * @return the transformed result (new list)
     */
    public static <E1, E2> Collection<E2> collect(final Collection<E1> inputCollection,
                                                  final Transformer<E1, E2> transformer) {
        List<E2> answer = new ArrayList<>();
        if (inputCollection == null || transformer == null) return answer;
        for (E1 e1 : inputCollection) {
            answer.add(transformer.transform(e1));
        }
        return answer;
    }

    /**
     * Counts the number of elements in the input collection that match the predicate.
     * <p>
     * A <code>null</code> collection or predicate matches no elements.
     *
     * @param collection the collection to get the input from, may be null
     * @param predicate  the predicate to use, may be null
     * @return the number of matches for the predicate in the collection
     */
    public static <E> int countMatches(Collection<E> collection, Predicate<E> predicate) {
        if (collection == null || predicate == null) return 0;
        int count = 0;
        for (E o : collection) {
            if (predicate.evaluate(o)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Answers true if a predicate is true for at least one element of a collection.
     * <p>
     * A <code>null</code> collection or predicate returns false.
     *
     * @param collection the collection to get the input from, may be null
     * @param predicate  the predicate to use, may be null
     * @return true if at least one element of the collection matches the predicate
     */
    public static <E> boolean exists(Collection<E> collection, Predicate<E> predicate) {
        if (collection == null || predicate == null) return false;
        for (E o : collection) {
            if (predicate.evaluate(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an element to the collection unless the element is null.
     *
     * @param collection the collection to add to, may be null
     * @param object     the object to add, if null it will not be added
     * @return true if the collection changed
     */
    public static <E> boolean addIgnoreNull(Collection<E> collection, E object) {
        if (collection == null) return false;
        return (object != null && collection.add(object));
    }

    /**
     * Adds all elements in the iteration to the given collection.
     *
     * @param collection the collection to add to, may be null
     * @param iterator   the iterator of elements to add, may be null
     */
    public static <E> void addAll(Collection<E> collection, Iterator<E> iterator) {
        if (collection == null || iterator == null) return;
        while (iterator.hasNext()) {
            collection.add(iterator.next());
        }
    }

    /**
     * Adds all elements in the enumeration to the given collection.
     *
     * @param collection  the collection to add to, may be null
     * @param enumeration the enumeration of elements to add, may be null
     */
    public static <E> void addAll(Collection<E> collection, Enumeration<E> enumeration) {
        if (collection == null || enumeration == null) return;
        while (enumeration.hasMoreElements()) {
            collection.add(enumeration.nextElement());
        }
    }

    /**
     * Adds all elements in the array to the given collection.
     *
     * @param collection the collection to add to, may be null
     * @param elements   the array of elements to add, may be null
     */
    public static <E> void addAll(Collection<E> collection, E[] elements) {
        if (collection == null || elements == null || elements.length == 0) return;
        collection.addAll(Arrays.asList(elements));
    }

    /**
     * Returns the <code>index</code>-th value in <code>object</code>, throwing
     * <code>IndexOutOfBoundsException</code> if there is no such element or
     * <code>IllegalArgumentException</code> if <code>object</code> is not an
     * instance of one of the supported types.
     * <p>
     * The supported types, and associated semantics are:
     * <ul>
     * <li> Map -- the value returned is the <code>Map.Entry</code> in position
     * <code>index</code> in the map's <code>entrySet</code> iterator,
     * if there is such an entry.</li>
     * <li> List -- this method is equivalent to the list's get method.</li>
     * <li> Array -- the <code>index</code>-th array entry is returned,
     * if there is such an entry; otherwise an <code>IndexOutOfBoundsException</code>
     * is thrown.</li>
     * <li> Collection -- the value returned is the <code>index</code>-th object
     * returned by the collection's default iterator, if there is such an element.</li>
     * <li> Iterator or Enumeration -- the value returned is the
     * <code>index</code>-th object in the Iterator/Enumeration, if there
     * is such an element.  The Iterator/Enumeration is advanced to
     * <code>index</code> (or to the end, if <code>index</code> exceeds the
     * number of entries) as a side effect of this method.</li>
     * </ul>
     *
     * @param object the object to get a value from
     * @param index  the index to get
     * @return the object at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     * @throws IllegalArgumentException  if the object type is invalid
     */
    public static Object get(Object object, int index) {
        if (object == null) return null;
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative: " + index);
        }
        if (object instanceof Map) {
            Map map = (Map) object;
            Iterator iterator = map.entrySet().iterator();
            return get(iterator, index);
        } else if (object instanceof List) {
            return ((List) object).get(index);
        } else if (object instanceof Object[]) {
            return ((Object[]) object)[index];
        } else if (object instanceof Iterator) {
            Iterator it = (Iterator) object;
            while (it.hasNext()) {
                index--;
                if (index == -1) {
                    return it.next();
                } else {
                    it.next();
                }
            }
            throw new IndexOutOfBoundsException("Entry does not exist: " + index);
        } else if (object instanceof Collection) {
            Iterator iterator = ((Collection) object).iterator();
            return get(iterator, index);
        } else if (object instanceof Enumeration) {
            Enumeration it = (Enumeration) object;
            while (it.hasMoreElements()) {
                index--;
                if (index == -1) {
                    return it.nextElement();
                } else {
                    it.nextElement();
                }
            }
            throw new IndexOutOfBoundsException("Entry does not exist: " + index);
        } else {
            try {
                return Array.get(object, index);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
    }

    /**
     * Gets the size of the collection/iterator specified.
     * <p>
     * This method can handles objects as follows
     * <ul>
     * <li>Collection - the collection size
     * <li>Map - the map size
     * <li>Array - the array size
     * <li>Iterator - the number of elements remaining in the iterator
     * <li>Enumeration - the number of elements remaining in the enumeration
     * </ul>
     *
     * @param object the object to get the size of
     * @return the size of the specified collection
     * @throws IllegalArgumentException thrown if object is not recognised or null
     */
    public static int size(Object object) {
        if (object == null) return 0;
        int total = 0;
        if (object instanceof Map) {
            total = ((Map) object).size();
        } else if (object instanceof Collection) {
            total = ((Collection) object).size();
        } else if (object instanceof Object[]) {
            total = ((Object[]) object).length;
        } else if (object instanceof Iterator) {
            Iterator it = (Iterator) object;
            while (it.hasNext()) {
                total++;
                it.next();
            }
        } else if (object instanceof Enumeration) {
            Enumeration it = (Enumeration) object;
            while (it.hasMoreElements()) {
                total++;
                it.nextElement();
            }
        } else {
            try {
                total = Array.getLength(object);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
        return total;
    }

    /**
     * Checks if the specified collection/array/iterator is empty.
     * <p>
     * This method can handles objects as follows
     * <ul>
     * <li>Collection - via collection isEmpty
     * <li>Map - via map isEmpty
     * <li>Array - using array size
     * <li>Iterator - via hasNext
     * <li>Enumeration - via hasMoreElements
     * </ul>
     * <p>
     * Note: This method is named to avoid clashing with
     * {@link #isEmpty(Collection)}.
     *
     * @param object the object to get the size of, not null
     * @return true if empty
     * @throws IllegalArgumentException thrown if object is not recognised or null
     */
    public static boolean sizeIsEmpty(Object object) {
        if (object == null) return true;
        if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        } else if (object instanceof Map) {
            return ((Map) object).isEmpty();
        } else if (object instanceof Object[]) {
            return ((Object[]) object).length == 0;
        } else if (object instanceof Iterator) {
            return !((Iterator) object).hasNext();
        } else if (object instanceof Enumeration) {
            return !((Enumeration) object).hasMoreElements();
        } else {
            try {
                return Array.getLength(object) == 0;
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
    }

    /**
     * Null-safe check if the specified collection is empty.
     * <p>
     * Null returns true.
     *
     * @param coll the collection to check, may be null
     * @return true if empty or null
     */
    public static boolean isEmpty(Collection coll) {
        return coll == null || coll.size() == 0;
    }

    /**
     * Null-safe check if the specified collection is not empty.
     * <p>
     * Null returns false.
     *
     * @param coll the collection to check, may be null
     * @return true if non-null and non-empty
     */
    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }


    /**
     * Returns a collection containing all the elements in <code>collection</code>
     * that are also in <code>retain</code>. The cardinality of an element <code>e</code>
     * in the returned collection is the same as the cardinality of <code>e</code>
     * in <code>collection</code> unless <code>retain</code> does not contain <code>e</code>, in which
     * case the cardinality is zero. This method is useful if you do not wish to modify
     * the collection <code>c</code> and thus cannot call <code>c.retainAll(retain);</code>.
     *
     * @param collection the collection whose contents are the target of the #retailAll operation
     * @param retain     the collection containing the elements to be retained in the returned collection
     * @return a <code>Collection</code> containing all the elements of <code>collection</code>
     * that occur at least once in <code>retain</code>.
     */
    public static <E> Collection<E> retainAll(Collection<E> collection, Collection<E> retain) {
        if (collection == null || retain == null) return new ArrayList<>();
        List<E> list = new ArrayList<>();
        for (E item : collection) {
            if (retain.contains(item)) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * Removes the elements in <code>remove</code> from <code>collection</code>. That is, this
     * method returns a collection containing all the elements in <code>c</code>
     * that are not in <code>remove</code>. The cardinality of an element <code>e</code>
     * in the returned collection is the same as the cardinality of <code>e</code>
     * in <code>collection</code> unless <code>remove</code> contains <code>e</code>, in which
     * case the cardinality is zero. This method is useful if you do not wish to modify
     * the collection <code>c</code> and thus cannot call <code>collection.removeAll(remove);</code>.
     *
     * @param collection the collection from which items are removed (in the returned collection)
     * @param remove     the items to be removed from the returned <code>collection</code>
     * @return a <code>Collection</code> containing all the elements of <code>collection</code> except
     * any elements that also occur in <code>remove</code>.
     */
    public static <E> Collection<E> removeAll(Collection<E> collection, Collection<E> remove) {
        if (collection == null) return new ArrayList<>();
        if (remove == null) return new ArrayList<>(collection);
        List<E> list = new ArrayList<>();
        for (E obj : collection) {
            if (!remove.contains(obj)) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * Randomly permutes the specified list using a default source of randomness.
     *
     * @param list the list to be shuffled.
     * @throws UnsupportedOperationException if the specified list or
     *                                       its list-iterator does not support the <tt>set</tt> operation.
     */
    public static <T> void shuffle(List<T> list) {
        Collections.shuffle(list);
    }

    /**
     * Return the string of collection.
     *
     * @param collection The collection.
     * @return the string of collection
     */
    public static String toString(Collection collection) {
        if (collection == null) return "null";
        return collection.toString();
    }

    public interface Closure<E> {
        void execute(int index, E item);
    }

    public interface Transformer<E1, E2> {
        E2 transform(E1 input);
    }

    public interface Predicate<E> {
        boolean evaluate(E item);
    }
}
