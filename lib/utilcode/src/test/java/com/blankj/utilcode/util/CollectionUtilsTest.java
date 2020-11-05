package com.blankj.utilcode.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/12
 *     desc  : test CollectionUtils
 * </pre>
 */
public class CollectionUtilsTest extends BaseTest {

    @Test
    public void union() {
        ArrayList<String> l0 = CollectionUtils.newArrayList("00", "01", "02");
        ArrayList<String> l1 = CollectionUtils.newArrayList("00", "11", "12");

        Collection unionNullNull = CollectionUtils.union(null, null);
        Collection unionL0Null = CollectionUtils.union(l0, null);
        Collection unionNullL1 = CollectionUtils.union(null, l1);
        Collection unionL0L1 = CollectionUtils.union(l0, l1);

        System.out.println(unionNullNull);
        System.out.println(unionL0Null);
        System.out.println(unionNullL1);
        System.out.println(unionL0L1);

        Assert.assertNotSame(l0, unionL0Null);
        Assert.assertNotSame(l1, unionNullL1);
    }

    @Test
    public void intersection() {
        ArrayList<String> l0 = CollectionUtils.newArrayList("00", "01", "02");
        ArrayList<String> l1 = CollectionUtils.newArrayList("00", "11", "12");

        Collection intersectionNullNull = CollectionUtils.intersection(null, null);
        Collection intersectionL0Null = CollectionUtils.intersection(l0, null);
        Collection intersectionNullL1 = CollectionUtils.intersection(null, l1);
        Collection intersectionL0L1 = CollectionUtils.intersection(l0, l1);

        System.out.println(intersectionNullNull);
        System.out.println(intersectionL0Null);
        System.out.println(intersectionNullL1);
        System.out.println(intersectionL0L1);
    }

    @Test
    public void disjunction() {
        ArrayList<String> l0 = CollectionUtils.newArrayList("00", "01", "02");
        ArrayList<String> l1 = CollectionUtils.newArrayList("00", "11", "12");

        Collection disjunctionNullNull = CollectionUtils.disjunction(null, null);
        Collection disjunctionL0Null = CollectionUtils.disjunction(l0, null);
        Collection disjunctionNullL1 = CollectionUtils.disjunction(null, l1);
        Collection disjunctionL0L1 = CollectionUtils.disjunction(l0, l1);

        System.out.println(disjunctionNullNull);
        System.out.println(disjunctionL0Null);
        System.out.println(disjunctionNullL1);
        System.out.println(disjunctionL0L1);

        Assert.assertNotSame(l0, disjunctionL0Null);
        Assert.assertNotSame(l1, disjunctionNullL1);
    }

    @Test
    public void subtract() {
        ArrayList<String> l0 = CollectionUtils.newArrayList("00", "01", "02");
        ArrayList<String> l1 = CollectionUtils.newArrayList("00", "11", "12");

        Collection subtractNullNull = CollectionUtils.subtract(null, null);
        Collection subtractL0Null = CollectionUtils.subtract(l0, null);
        Collection subtractNullL1 = CollectionUtils.subtract(null, l1);
        Collection subtractL0L1 = CollectionUtils.subtract(l0, l1);

        System.out.println(subtractNullNull);
        System.out.println(subtractL0Null);
        System.out.println(subtractNullL1);
        System.out.println(subtractL0L1);

        Assert.assertNotSame(l0, subtractL0Null);
    }

    @Test
    public void containsAny() {
        ArrayList<String> l0 = CollectionUtils.newArrayList("00", "01", "02");
        ArrayList<String> l1 = CollectionUtils.newArrayList("00", "11", "12");

        Assert.assertFalse(CollectionUtils.containsAny(null, null));
        Assert.assertFalse(CollectionUtils.containsAny(l0, null));
        Assert.assertFalse(CollectionUtils.containsAny(null, l1));
        Assert.assertTrue(CollectionUtils.containsAny(l0, l1));
    }

    @Test
    public void getCardinalityMap() {
        System.out.println(CollectionUtils.getCardinalityMap(null));

        ArrayList<String> l0 = CollectionUtils.newArrayList("0", "0", "1", "1", "2");
        System.out.println(CollectionUtils.getCardinalityMap(l0));
    }

    @Test
    public void isSubCollection() {
        ArrayList<String> l0 = CollectionUtils.newArrayList("0", "1", "2");
        ArrayList<String> l1 = CollectionUtils.newArrayList("0", "0", "1", "1", "2");

        Assert.assertFalse(CollectionUtils.isSubCollection(null, null));
        Assert.assertFalse(CollectionUtils.isSubCollection(l0, null));
        Assert.assertFalse(CollectionUtils.isSubCollection(null, l0));
        Assert.assertTrue(CollectionUtils.isSubCollection(l0, l1));
        Assert.assertTrue(CollectionUtils.isSubCollection(l0, l0));
        Assert.assertFalse(CollectionUtils.isSubCollection(l1, l0));
    }

    @Test
    public void isProperSubCollection() {
        ArrayList<String> l0 = CollectionUtils.newArrayList("0", "1", "2");
        ArrayList<String> l1 = CollectionUtils.newArrayList("0", "0", "1", "1", "2");

        Assert.assertFalse(CollectionUtils.isProperSubCollection(null, null));
        Assert.assertFalse(CollectionUtils.isProperSubCollection(l0, null));
        Assert.assertFalse(CollectionUtils.isProperSubCollection(null, l0));
        Assert.assertTrue(CollectionUtils.isProperSubCollection(l0, l1));
        Assert.assertFalse(CollectionUtils.isProperSubCollection(l0, l0));
        Assert.assertFalse(CollectionUtils.isProperSubCollection(l1, l0));
    }

    @Test
    public void isEqualCollection() {
        ArrayList<String> l0 = CollectionUtils.newArrayList("0", "1", "2");
        ArrayList<String> l1 = CollectionUtils.newArrayList("0", "0", "1", "1", "2");

        Assert.assertFalse(CollectionUtils.isEqualCollection(null, null));
        Assert.assertFalse(CollectionUtils.isEqualCollection(l0, null));
        Assert.assertFalse(CollectionUtils.isEqualCollection(null, l0));
        Assert.assertTrue(CollectionUtils.isEqualCollection(l0, l0));
        Assert.assertFalse(CollectionUtils.isEqualCollection(l0, l1));
    }

    @Test
    public void cardinality() {
        ArrayList<String> list = CollectionUtils.newArrayList("0", "1", "1", "2");
        Assert.assertEquals(0, CollectionUtils.cardinality(null, null));
        Assert.assertEquals(0, CollectionUtils.cardinality(null, list));
        list.add(null);
        Assert.assertEquals(1, CollectionUtils.cardinality(null, list));
        Assert.assertEquals(2, CollectionUtils.cardinality("1", list));
    }

    @Test
    public void find() {
        ArrayList<String> list = CollectionUtils.newArrayList("0", "1", "1", "2");

        Assert.assertNull(CollectionUtils.find(null, null));
        Assert.assertNull(CollectionUtils.find(list, null));
        Assert.assertNull(CollectionUtils.find(null, new CollectionUtils.Predicate<String>() {
            @Override
            public boolean evaluate(String item) {
                return true;
            }
        }));
        Assert.assertEquals("1", CollectionUtils.find(list, new CollectionUtils.Predicate<String>() {
            @Override
            public boolean evaluate(String item) {
                return "1".equals(item);
            }
        }));
    }

    @Test
    public void forAllDo() {
        ArrayList<String> list = CollectionUtils.newArrayList("0", "1", "1", "2");

        CollectionUtils.forAllDo(null, null);
        CollectionUtils.forAllDo(list, null);
        CollectionUtils.forAllDo(null, new CollectionUtils.Closure<Object>() {
            @Override
            public void execute(int index, Object item) {
                System.out.println(index + ": " + index);
            }
        });

        CollectionUtils.forAllDo(list, new CollectionUtils.Closure<String>() {
            @Override
            public void execute(int index, String item) {
                System.out.println(index + ": " + index);
            }
        });
    }

    @Test
    public void filter() {
        ArrayList<Integer> l0 = null;
        ArrayList<Integer> l1 = CollectionUtils.newArrayList(0, 1, 2, 3);

        CollectionUtils.filter(l0, null);
        Assert.assertNull(l0);

        CollectionUtils.filter(l0, new CollectionUtils.Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer item) {
                return item > 1;
            }
        });
        Assert.assertNull(l0);

        CollectionUtils.filter(l1, null);
        Assert.assertEquals(l1, l1);

        CollectionUtils.filter(l1, new CollectionUtils.Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer item) {
                return item > 1;
            }
        });
        Assert.assertTrue(CollectionUtils.isEqualCollection(CollectionUtils.newArrayList(2, 3), l1));
    }

    @Test
    public void select() {
        ArrayList<Integer> list = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertEquals(0, CollectionUtils.select(null, null).size());
        Assert.assertEquals(0, CollectionUtils.select(list, null).size());
        Assert.assertEquals(0, CollectionUtils.select(null, new CollectionUtils.Predicate<Object>() {
            @Override
            public boolean evaluate(Object item) {
                return true;
            }
        }).size());

        Assert.assertTrue(CollectionUtils.isEqualCollection(
                CollectionUtils.newArrayList(2, 3),
                CollectionUtils.select(list, new CollectionUtils.Predicate<Integer>() {
                    @Override
                    public boolean evaluate(Integer item) {
                        return item > 1;
                    }
                })));

        Collection<Integer> list1 = CollectionUtils.select(list, new CollectionUtils.Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer item) {
                return true;
            }
        });

        Assert.assertTrue(CollectionUtils.isEqualCollection(list, list1));
        Assert.assertNotSame(list, list1);
    }


    @Test
    public void selectRejected() {
        ArrayList<Integer> list = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertEquals(0, CollectionUtils.selectRejected(null, null).size());
        Assert.assertEquals(0, CollectionUtils.selectRejected(list, null).size());
        Assert.assertEquals(0, CollectionUtils.selectRejected(null, new CollectionUtils.Predicate<Object>() {
            @Override
            public boolean evaluate(Object item) {
                return true;
            }
        }).size());

        Assert.assertTrue(CollectionUtils.isEqualCollection(
                CollectionUtils.newArrayList(0, 1),
                CollectionUtils.selectRejected(list, new CollectionUtils.Predicate<Integer>() {
                    @Override
                    public boolean evaluate(Integer item) {
                        return item > 1;
                    }
                })));


        Collection<Integer> list1 = CollectionUtils.selectRejected(list, new CollectionUtils.Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer item) {
                return false;
            }
        });

        Assert.assertTrue(CollectionUtils.isEqualCollection(list, list1));
        Assert.assertNotSame(list, list1);
    }

    @Test
    public void transform() {
        ArrayList<Integer> l0 = null;
        ArrayList<Integer> l1 = CollectionUtils.newArrayList(0, 1, 2, 3);

        CollectionUtils.transform(l0, null);
        Assert.assertNull(l0);

        CollectionUtils.transform(l0, new CollectionUtils.Transformer<Integer, Object>() {
            @Override
            public Object transform(Integer input) {
                return "int: " + input;
            }
        });
        Assert.assertNull(l0);

        CollectionUtils.transform(l1, null);
        Assert.assertEquals(l1, l1);

        CollectionUtils.transform(l1, new CollectionUtils.Transformer<Integer, String>() {
            @Override
            public String transform(Integer input) {
                return String.valueOf(input);
            }
        });
        Assert.assertTrue(CollectionUtils.isEqualCollection(CollectionUtils.newArrayList("0", "1", "2", "3"), l1));
    }

    @Test
    public void collect() {
        ArrayList<Integer> list = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertTrue(CollectionUtils.isEmpty(CollectionUtils.collect(null, null)));
        Assert.assertTrue(CollectionUtils.isEmpty(CollectionUtils.collect(list, null)));
        Assert.assertTrue(CollectionUtils.isEmpty(CollectionUtils.collect(null, new CollectionUtils.Transformer() {
            @Override
            public Object transform(Object input) {
                return null;
            }
        })));

        Assert.assertTrue(CollectionUtils.isEqualCollection(
                CollectionUtils.newArrayList("0", "1", "2", "3"),
                CollectionUtils.collect(list, new CollectionUtils.Transformer<Integer, String>() {
                    @Override
                    public String transform(Integer input) {
                        return String.valueOf(input);
                    }
                })));
    }

    @Test
    public void countMatches() {
        ArrayList<Integer> list = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertEquals(0, CollectionUtils.countMatches(null, null));
        Assert.assertEquals(0, CollectionUtils.countMatches(list, null));
        Assert.assertEquals(0, CollectionUtils.countMatches(null, new CollectionUtils.Predicate<Object>() {
            @Override
            public boolean evaluate(Object item) {
                return false;
            }
        }));
        Assert.assertEquals(2, CollectionUtils.countMatches(list, new CollectionUtils.Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer item) {
                return item > 1;
            }
        }));
    }

    @Test
    public void exists() {
        ArrayList<Integer> list = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertFalse(CollectionUtils.exists(null, null));
        Assert.assertFalse(CollectionUtils.exists(list, null));
        Assert.assertFalse(CollectionUtils.exists(null, new CollectionUtils.Predicate<Object>() {
            @Override
            public boolean evaluate(Object item) {
                return false;
            }
        }));
        Assert.assertTrue(CollectionUtils.exists(list, new CollectionUtils.Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer item) {
                return item > 1;
            }
        }));
    }

    @Test
    public void addIgnoreNull() {
        ArrayList<Integer> list = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertFalse(CollectionUtils.addIgnoreNull(null, null));
        Assert.assertFalse(CollectionUtils.addIgnoreNull(null, 1));

        CollectionUtils.addIgnoreNull(list, null);
        Assert.assertEquals(CollectionUtils.newArrayList(0, 1, 2, 3), list);

        CollectionUtils.addIgnoreNull(list, 4);
        Assert.assertEquals(CollectionUtils.newArrayList(0, 1, 2, 3, 4), list);
    }

    @Test
    public void addAll() {
        ArrayList<Integer> l0 = null;
        ArrayList<Integer> l1 = CollectionUtils.newArrayList(0, 1, 2, 3);
        CollectionUtils.addAll(l0, (Iterator<Integer>) null);
        Assert.assertNull(l0);
        CollectionUtils.addAll(l0, (Enumeration<Integer>) null);
        Assert.assertNull(l0);
        CollectionUtils.addAll(l0, (Integer[]) null);
        Assert.assertNull(l0);

        CollectionUtils.addAll(l1, (Iterator<Integer>) null);
        Assert.assertTrue(CollectionUtils.isEqualCollection(CollectionUtils.newArrayList(0, 1, 2, 3), l1));
        CollectionUtils.addAll(l1, (Enumeration<Integer>) null);
        Assert.assertTrue(CollectionUtils.isEqualCollection(CollectionUtils.newArrayList(0, 1, 2, 3), l1));
        CollectionUtils.addAll(l1, (Integer[]) null);
        Assert.assertTrue(CollectionUtils.isEqualCollection(CollectionUtils.newArrayList(0, 1, 2, 3), l1));

        CollectionUtils.addAll(l1, CollectionUtils.newArrayList(4, 5).iterator());
        Assert.assertTrue(CollectionUtils.isEqualCollection(CollectionUtils.newArrayList(0, 1, 2, 3, 4, 5), l1));
    }

    @Test
    public void get() {
        ArrayList<Integer> l0 = null;
        ArrayList<Integer> l1 = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertNull(CollectionUtils.get(l0, 0));
        Assert.assertEquals(0, CollectionUtils.get(l1, 0));
    }

    @Test
    public void size() {
        ArrayList<Integer> l0 = null;
        ArrayList<Integer> l1 = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertEquals(0, CollectionUtils.size(l0));
        Assert.assertEquals(4, CollectionUtils.size(l1));
    }

    @Test
    public void sizeIsEmpty() {
        ArrayList<Integer> l0 = null;
        ArrayList<Integer> l1 = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertTrue(CollectionUtils.sizeIsEmpty(l0));
        Assert.assertFalse(CollectionUtils.sizeIsEmpty(l1));
    }

    @Test
    public void isEmpty() {
        ArrayList<Integer> l0 = null;
        ArrayList<Integer> l1 = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertTrue(CollectionUtils.isEmpty(l0));
        Assert.assertFalse(CollectionUtils.isEmpty(l1));
    }

    @Test
    public void isNotEmpty() {
        ArrayList<Integer> l0 = null;
        ArrayList<Integer> l1 = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertFalse(CollectionUtils.isNotEmpty(l0));
        Assert.assertTrue(CollectionUtils.isNotEmpty(l1));
    }

    @Test
    public void retainAll() {
        ArrayList<Integer> list = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertEquals(0, CollectionUtils.retainAll(null, null).size());
        Assert.assertEquals(0, CollectionUtils.retainAll(list, null).size());
        Assert.assertEquals(0, CollectionUtils.retainAll(null, list).size());

        Assert.assertTrue(CollectionUtils.isEqualCollection(
                CollectionUtils.newArrayList(0, 1, 2, 3),
                CollectionUtils.retainAll(list, list)
        ));

        Assert.assertTrue(CollectionUtils.isEqualCollection(
                CollectionUtils.newArrayList(0, 1),
                CollectionUtils.retainAll(list, CollectionUtils.newArrayList(0, 1, 6))
        ));
    }

    @Test
    public void removeAll() {
        ArrayList<Integer> list = CollectionUtils.newArrayList(0, 1, 2, 3);

        Assert.assertEquals(0, CollectionUtils.removeAll(null, null).size());
        Assert.assertTrue(CollectionUtils.isEqualCollection(
                CollectionUtils.newArrayList(0, 1, 2, 3),
                CollectionUtils.removeAll(list, null)
        ));
        Assert.assertEquals(0, CollectionUtils.removeAll(null, list).size());

        Assert.assertTrue(CollectionUtils.isEqualCollection(
                CollectionUtils.newArrayList(2, 3),
                CollectionUtils.removeAll(list, CollectionUtils.newArrayList(0, 1, 6))
        ));
    }
}