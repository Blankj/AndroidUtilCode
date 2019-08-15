package com.blankj.utilcode.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/12
 *     desc  : test ArrayUtils
 * </pre>
 */
public class ArrayUtilsTest extends BaseTest {

    @Test
    public void newArray() {
        System.out.println(ArrayUtils.toString(ArrayUtils.newArray(null)));
        System.out.println(ArrayUtils.toString(ArrayUtils.newArray((String) null)));
        System.out.println(ArrayUtils.toString(ArrayUtils.newArray(0, 1, 2, 3)));
        System.out.println(ArrayUtils.toString(ArrayUtils.newLongArray(0, 1, 2, 3)));
        System.out.println(ArrayUtils.toString(ArrayUtils.newIntArray(0, 1, 2, 3)));
        System.out.println(ArrayUtils.toString(ArrayUtils.newShortArray((short) 0, (short) 1, (short) 2, (short) 3)));
        System.out.println(ArrayUtils.toString(ArrayUtils.newCharArray('0', '1', '2', '3')));
        System.out.println(ArrayUtils.toString(ArrayUtils.newByteArray((byte) 0, (byte) 1, (byte) 2, (byte) 3)));
        System.out.println(ArrayUtils.toString(ArrayUtils.newDoubleArray(0, 1, 2, 3)));
        System.out.println(ArrayUtils.toString(ArrayUtils.newFloatArray(0, 1, 2, 3)));
        System.out.println(ArrayUtils.toString(ArrayUtils.newBooleanArray(false, true, false, true)));
    }

    @Test
    public void isEmpty() {
        Object nullArr = null;
        Object emptyArr = new int[]{};
        Assert.assertTrue(ArrayUtils.isEmpty(nullArr));
        Assert.assertTrue(ArrayUtils.isEmpty(emptyArr));
        Assert.assertFalse(ArrayUtils.isEmpty(new int[]{1}));
    }

    @Test
    public void getLength() {
        Object nullArr = null;
        Object emptyArr = new int[]{};
        Assert.assertEquals(0, ArrayUtils.getLength(nullArr));
        Assert.assertEquals(0, ArrayUtils.getLength(emptyArr));
        Assert.assertEquals(1, ArrayUtils.getLength(new int[]{1}));
    }

    @Test
    public void isSameLength() {
        Object emptyArr1 = new int[]{};
        Assert.assertTrue(ArrayUtils.isSameLength(null, emptyArr1));
        Assert.assertTrue(ArrayUtils.isSameLength(new boolean[0], emptyArr1));
    }

    @Test
    public void get() {
        Object emptyArr1 = new int[]{0, 1, 2};
        Assert.assertEquals(0, ArrayUtils.get(emptyArr1, 0));
        Assert.assertEquals(1, ArrayUtils.get(emptyArr1, 1));
        ArrayUtils.get(emptyArr1, 4);
    }

    @Test
    public void getOrDefault() {
        Object array = new int[]{0, 1, 2};
        Assert.assertEquals(0, ArrayUtils.get(array, 0));
        Assert.assertEquals(1, ArrayUtils.get(array, 1));
        Assert.assertEquals(-1, ArrayUtils.get(array, 4, -1));
        Assert.assertNull(ArrayUtils.get(array, 4));
    }

    @Test
    public void set() {
        int[] array = new int[]{0, -1, 2};
        ArrayUtils.set(array, 1, 1);
        Assert.assertArrayEquals(new int[]{0, 1, 2}, array);
    }

    @Test
    public void equals() {
        Assert.assertTrue(ArrayUtils.equals(null, (Object[]) null));
    }

    @Test
    public void reverse() {
        int[] array = new int[]{0, 1, 2, 3};
        ArrayUtils.reverse(array);
        Assert.assertArrayEquals(new int[]{3, 2, 1, 0}, array);
    }

    @Test
    public void copy() {
        int[] array = new int[]{0, 1, 2, 3};
        int[] copy = ArrayUtils.copy(array);
        Assert.assertArrayEquals(array, copy);
        Assert.assertNotSame(array, copy);
    }

    @Test
    public void subArray() {
        int[] array = new int[]{0, 1, 2, 3};
        int[] subArray = ArrayUtils.subArray(array, 1, 3);
        Assert.assertArrayEquals(new int[]{1, 2}, subArray);
    }

    @Test
    public void add() {
        int[] array = new int[]{0, 1, 2, 3};
        int[] addLastOne = ArrayUtils.add(array, 4);
        int[] addFirstOne = ArrayUtils.add(array, 0, -1);
        int[] addArr = ArrayUtils.add(array, new int[]{4, 5});
        int[] addFirstArr = ArrayUtils.add(array, 0, new int[]{-2, -1});
        int[] addMidArr = ArrayUtils.add(array, 2, new int[]{1, 2});
        Assert.assertArrayEquals(new int[]{0, 1, 2, 3, 4}, addLastOne);
        Assert.assertArrayEquals(new int[]{-1, 0, 1, 2, 3}, addFirstOne);
        Assert.assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5}, addArr);
        Assert.assertArrayEquals(new int[]{-2, -1, 0, 1, 2, 3}, addFirstArr);
        Assert.assertArrayEquals(new int[]{0, 1, 1, 2, 2, 3}, addMidArr);
    }

    @Test
    public void remove() {
        int[] array = new int[]{0, 1, 2, 3};
        int[] remove = ArrayUtils.remove(array, 0);
        Assert.assertArrayEquals(new int[]{1, 2, 3}, remove);
    }

    @Test
    public void removeElement() {
        int[] array = new int[]{0, 1, 2, 3};
        int[] remove = ArrayUtils.removeElement(array, 0);
        Assert.assertArrayEquals(new int[]{1, 2, 3}, remove);
    }

    @Test
    public void indexOf() {
        int[] array = new int[]{0, 1, 2, 3};
        int i = ArrayUtils.indexOf(array, 0);
        int i1 = ArrayUtils.indexOf(array, -1);
        int i2 = ArrayUtils.indexOf(array, 0, 1);
        Assert.assertEquals(0, i);
        Assert.assertEquals(-1, i1);
        Assert.assertEquals(-1, i2);
    }

    @Test
    public void lastIndexOf() {
        int[] array = new int[]{0, 1, 2, 3, 0};
        int i = ArrayUtils.lastIndexOf(array, 0);
        int i1 = ArrayUtils.lastIndexOf(array, -1);
        int i2 = ArrayUtils.lastIndexOf(array, 0, 1);
        Assert.assertEquals(4, i);
        Assert.assertEquals(-1, i1);
        Assert.assertEquals(0, i2);
    }

    @Test
    public void contains() {
        int[] array = new int[]{0, 1, 2, 3};
        Assert.assertTrue(ArrayUtils.contains(array, 0));
        Assert.assertFalse(ArrayUtils.contains(array, 4));
    }

    @Test
    public void toPrimitive() {
        int[] primitiveArray = new int[]{0, 1, 2, 3};
        Integer[] array = new Integer[]{0, 1, 2, 3};
        Assert.assertArrayEquals(primitiveArray, ArrayUtils.toPrimitive(array));
        int[] primitiveArray1 = new int[]{0, 1, 2, 3, 0};
        Integer[] array1 = new Integer[]{0, 1, 2, 3, null};
        Assert.assertArrayEquals(primitiveArray1, ArrayUtils.toPrimitive(array1, 0));
    }

    @Test
    public void toObject() {
        int[] primitiveArray = new int[]{0, 1, 2, 3};
        Integer[] array = new Integer[]{0, 1, 2, 3};
        Assert.assertArrayEquals(array, ArrayUtils.toObject(primitiveArray));
    }

    @Test
    public void asList() {
        Assert.assertEquals(0, ArrayUtils.asList(null).size());

        List<Integer> list = ArrayUtils.asList(1, 2, 3, 4);
        System.out.println(list);
    }

    @Test
    public void sort() {
        int[] ints = {2, 1, 0, 1, 2, 3};
        ArrayUtils.sort(ints);
        System.out.println(ArrayUtils.toString(ints));
    }

    @Test
    public void forAllDo() {
        ArrayUtils.forAllDo(null, null);
        int[] array = new int[]{0, 1, 2, 3};
        ArrayUtils.forAllDo(array, new ArrayUtils.Closure<Integer>() {
            @Override
            public void execute(int index, Integer item) {
                System.out.println(index + ", " + item);
            }
        });
    }
}