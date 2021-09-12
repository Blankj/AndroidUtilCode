package com.blankj.utilcode.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/10
 *     desc  : utils about array
 * </pre>
 */
public class ArrayUtils {

    public static final int INDEX_NOT_FOUND = -1;

    private ArrayUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Returns a new array only of those given elements.
     *
     * @param array The array.
     * @return a new array only of those given elements.
     */
    @NonNull
    public static <T> T[] newArray(T... array) {
        return array;
    }

    @NonNull
    public static long[] newLongArray(long... array) {
        return array;
    }

    @NonNull
    public static int[] newIntArray(int... array) {
        return array;
    }

    @NonNull
    public static short[] newShortArray(short... array) {
        return array;
    }

    @NonNull
    public static char[] newCharArray(char... array) {
        return array;
    }

    @NonNull
    public static byte[] newByteArray(byte... array) {
        return array;
    }

    @NonNull
    public static double[] newDoubleArray(double... array) {
        return array;
    }

    @NonNull
    public static float[] newFloatArray(float... array) {
        return array;
    }

    @NonNull
    public static boolean[] newBooleanArray(boolean... array) {
        return array;
    }

    /**
     * Return the array is empty.
     *
     * @param array The array.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isEmpty(@Nullable Object array) {
        return getLength(array) == 0;
    }

    /**
     * Return the size of array.
     *
     * @param array The array.
     * @return the size of array
     */
    public static int getLength(@Nullable Object array) {
        if (array == null) return 0;
        return Array.getLength(array);
    }

    public static boolean isSameLength(@Nullable Object array1, @Nullable Object array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * Get the value of the specified index of the array.
     *
     * @param array The array.
     * @param index The index into the array.
     * @return the value of the specified index of the array
     */
    @Nullable
    public static Object get(@Nullable Object array, int index) {
        return get(array, index, null);
    }

    /**
     * Get the value of the specified index of the array.
     *
     * @param array        The array.
     * @param index        The index into the array.
     * @param defaultValue The default value.
     * @return the value of the specified index of the array
     */
    @Nullable
    public static Object get(@Nullable Object array, int index, @Nullable Object defaultValue) {
        if (array == null) return defaultValue;
        try {
            return Array.get(array, index);
        } catch (Exception ignore) {
            return defaultValue;
        }
    }

    /**
     * Set the value of the specified index of the array.
     *
     * @param array The array.
     * @param index The index into the array.
     * @param value The new value of the indexed component.
     */
    public static void set(@Nullable Object array, int index, @Nullable Object value) {
        if (array == null) return;
        Array.set(array, index, value);
    }

    /**
     * Return whether the two arrays are equals.
     *
     * @param a  One array.
     * @param a2 The other array.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equals(@Nullable Object[] a, @Nullable Object[] a2) {
        return Arrays.deepEquals(a, a2);
    }

    public static boolean equals(boolean[] a, boolean[] a2) {
        return Arrays.equals(a, a2);
    }

    public static boolean equals(byte[] a, byte[] a2) {
        return Arrays.equals(a, a2);
    }

    public static boolean equals(char[] a, char[] a2) {
        return Arrays.equals(a, a2);
    }

    public static boolean equals(double[] a, double[] a2) {
        return Arrays.equals(a, a2);
    }

    public static boolean equals(float[] a, float[] a2) {
        return Arrays.equals(a, a2);
    }

    public static boolean equals(int[] a, int[] a2) {
        return Arrays.equals(a, a2);
    }

    public static boolean equals(short[] a, short[] a2) {
        return Arrays.equals(a, a2);
    }

    ///////////////////////////////////////////////////////////////////////////
    // reverse
    ///////////////////////////////////////////////////////////////////////////

    /**
     * <p>Reverses the order of the given array.</p>
     *
     * <p>There is no special handling for multi-dimensional arrays.</p>
     *
     * <p>This method does nothing for a <code>null</code> input array.</p>
     *
     * @param array the array to reverse, may be <code>null</code>
     */
    public static <T> void reverse(T[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        T tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(long[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        long tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(int[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        int tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(short[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        short tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(char[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        char tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(byte[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        byte tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(double[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        double tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(float[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        float tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(boolean[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        boolean tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // copy
    ///////////////////////////////////////////////////////////////////////////

    /**
     * <p>Copies the specified array and handling
     * <code>null</code>.</p>
     *
     * <p>The objects in the array are not cloned, thus there is no special
     * handling for multi-dimensional arrays.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array the array to shallow clone, may be <code>null</code>
     * @return the cloned array, <code>null</code> if <code>null</code> input
     */
    @Nullable
    public static <T> T[] copy(@Nullable T[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    @Nullable
    public static long[] copy(@Nullable long[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    @Nullable
    public static int[] copy(@Nullable int[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    @Nullable
    public static short[] copy(@Nullable short[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    @Nullable
    public static char[] copy(@Nullable char[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    @Nullable
    public static byte[] copy(@Nullable byte[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    @Nullable
    public static double[] copy(@Nullable double[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    @Nullable
    public static float[] copy(@Nullable float[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    @Nullable
    public static boolean[] copy(@Nullable boolean[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    @Nullable
    private static Object realCopy(@Nullable Object array) {
        if (array == null) return null;
        return realSubArray(array, 0, getLength(array));
    }

    ///////////////////////////////////////////////////////////////////////////
    // subArray
    ///////////////////////////////////////////////////////////////////////////

    @Nullable
    public static <T> T[] subArray(@Nullable T[] array, int startIndexInclusive, int endIndexExclusive) {
        //noinspection unchecked
        return (T[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    @Nullable
    public static long[] subArray(@Nullable long[] array, int startIndexInclusive, int endIndexExclusive) {
        return (long[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    @Nullable
    public static int[] subArray(@Nullable int[] array, int startIndexInclusive, int endIndexExclusive) {
        return (int[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    @Nullable
    public static short[] subArray(@Nullable short[] array, int startIndexInclusive, int endIndexExclusive) {
        return (short[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    @Nullable
    public static char[] subArray(@Nullable char[] array, int startIndexInclusive, int endIndexExclusive) {
        return (char[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    @Nullable
    public static byte[] subArray(@Nullable byte[] array, int startIndexInclusive, int endIndexExclusive) {
        return (byte[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    @Nullable
    public static double[] subArray(@Nullable double[] array, int startIndexInclusive, int endIndexExclusive) {
        return (double[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    @Nullable
    public static float[] subArray(@Nullable float[] array, int startIndexInclusive, int endIndexExclusive) {
        return (float[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    @Nullable
    public static boolean[] subArray(@Nullable boolean[] array, int startIndexInclusive, int endIndexExclusive) {
        return (boolean[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    @Nullable
    private static Object realSubArray(@Nullable Object array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        int length = getLength(array);
        if (endIndexExclusive > length) {
            endIndexExclusive = length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        Class type = array.getClass().getComponentType();
        if (newSize <= 0) {
            return Array.newInstance(type, 0);
        }
        Object subArray = Array.newInstance(type, newSize);
        System.arraycopy(array, startIndexInclusive, subArray, 0, newSize);
        return subArray;
    }

    ///////////////////////////////////////////////////////////////////////////
    // add
    ///////////////////////////////////////////////////////////////////////////

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.</p>
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     * whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.realAdd(null, null)      = [null]
     * ArrayUtils.realAdd(null, "a")       = ["a"]
     * ArrayUtils.realAdd(["a"], null)     = ["a", null]
     * ArrayUtils.realAdd(["a"], "b")      = ["a", "b"]
     * ArrayUtils.realAdd(["a", "b"], "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param array   the array to "realAdd" the element to, may be <code>null</code>
     * @param element the object to realAdd
     * @return A new array containing the existing elements plus the new element
     */
    @NonNull
    public static <T> T[] add(@Nullable T[] array, @Nullable T element) {
        Class type = array != null ? array.getClass() : (element != null ? element.getClass() : Object.class);
        return (T[]) realAddOne(array, element, type);
    }

    @NonNull
    public static boolean[] add(@Nullable boolean[] array, boolean element) {
        return (boolean[]) realAddOne(array, element, Boolean.TYPE);
    }

    @NonNull
    public static byte[] add(@Nullable byte[] array, byte element) {
        return (byte[]) realAddOne(array, element, Byte.TYPE);
    }

    @NonNull
    public static char[] add(@Nullable char[] array, char element) {
        return (char[]) realAddOne(array, element, Character.TYPE);
    }

    @NonNull
    public static double[] add(@Nullable double[] array, double element) {
        return (double[]) realAddOne(array, element, Double.TYPE);
    }

    @NonNull
    public static float[] add(@Nullable float[] array, float element) {
        return (float[]) realAddOne(array, element, Float.TYPE);
    }

    @NonNull
    public static int[] add(@Nullable int[] array, int element) {
        return (int[]) realAddOne(array, element, Integer.TYPE);
    }

    @NonNull
    public static long[] add(@Nullable long[] array, long element) {
        return (long[]) realAddOne(array, element, Long.TYPE);
    }

    @NonNull
    public static short[] add(@Nullable short[] array, short element) {
        return (short[]) realAddOne(array, element, Short.TYPE);
    }

    @NonNull
    private static Object realAddOne(@Nullable Object array, @Nullable Object element, Class newArrayComponentType) {
        Object newArray;
        int arrayLength = 0;
        if (array != null) {
            arrayLength = getLength(array);
            newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
            System.arraycopy(array, 0, newArray, 0, arrayLength);
        } else {
            newArray = Array.newInstance(newArrayComponentType, 1);
        }
        Array.set(newArray, arrayLength, element);
        return newArray;
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of <code>array1</code> followed
     * by all of the elements <code>array2</code>. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * ArrayUtils.add(null, null)     = null
     * ArrayUtils.add(array1, null)   = copy of array1
     * ArrayUtils.add(null, array2)   = copy of array2
     * ArrayUtils.add([], [])         = []
     * ArrayUtils.add([null], [null]) = [null, null]
     * ArrayUtils.add(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
     * </pre>
     *
     * @param array1 the first array whose elements are added to the new array, may be <code>null</code>
     * @param array2 the second array whose elements are added to the new array, may be <code>null</code>
     * @return The new array, <code>null</code> if <code>null</code> array inputs.
     * The type of the new array is the type of the first array.
     */
    @Nullable
    public static <T> T[] add(@Nullable T[] array1, @Nullable T[] array2) {
        return (T[]) realAddArr(array1, array2);
    }

    @Nullable
    public static boolean[] add(@Nullable boolean[] array1, @Nullable boolean[] array2) {
        return (boolean[]) realAddArr(array1, array2);
    }

    @Nullable
    public static char[] add(@Nullable char[] array1, @Nullable char[] array2) {
        return (char[]) realAddArr(array1, array2);
    }

    @Nullable
    public static byte[] add(@Nullable byte[] array1, @Nullable byte[] array2) {
        return (byte[]) realAddArr(array1, array2);
    }

    @Nullable
    public static short[] add(@Nullable short[] array1, @Nullable short[] array2) {
        return (short[]) realAddArr(array1, array2);
    }

    @Nullable
    public static int[] add(@Nullable int[] array1, @Nullable int[] array2) {
        return (int[]) realAddArr(array1, array2);
    }

    @Nullable
    public static long[] add(@Nullable long[] array1, @Nullable long[] array2) {
        return (long[]) realAddArr(array1, array2);
    }

    @Nullable
    public static float[] add(@Nullable float[] array1, @Nullable float[] array2) {
        return (float[]) realAddArr(array1, array2);
    }

    @Nullable
    public static double[] add(@Nullable double[] array1, @Nullable double[] array2) {
        return (double[]) realAddArr(array1, array2);
    }

    private static Object realAddArr(@Nullable Object array1, @Nullable Object array2) {
        if (array1 == null && array2 == null) return null;
        if (array1 == null) {
            return realCopy(array2);
        }
        if (array2 == null) {
            return realCopy(array1);
        }
        int len1 = getLength(array1);
        int len2 = getLength(array2);
        Object joinedArray = Array.newInstance(array1.getClass().getComponentType(), len1 + len2);
        System.arraycopy(array1, 0, joinedArray, 0, len1);
        System.arraycopy(array2, 0, joinedArray, len1, len2);
        return joinedArray;
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     * whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, 0, null)        = null
     * ArrayUtils.add(null, 0, ["a"])       = ["a"]
     * ArrayUtils.add(["a"], 1, null)       = ["a"]
     * ArrayUtils.add(["a"], 1, ["b"])      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], 2, ["c"]) = ["a", "b", "c"]
     * </pre>
     *
     * @param array1 the array to realAdd the element to, may be <code>null</code>
     * @param index  the position of the new object
     * @param array2 the array to realAdd
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (index < 0 || index > array.length).
     */
    @Nullable
    public static <T> T[] add(@Nullable T[] array1, int index, @Nullable T[] array2) {
        Class clss;
        if (array1 != null) {
            clss = array1.getClass().getComponentType();
        } else if (array2 != null) {
            clss = array2.getClass().getComponentType();
        } else {
            return null;
        }
        return (T[]) realAddArr(array1, index, array2, clss);
    }

    @Nullable
    public static boolean[] add(@Nullable boolean[] array1, int index, @Nullable boolean[] array2) {
        Object result = realAddArr(array1, index, array2, Boolean.TYPE);
        if (result == null) return null;
        return (boolean[]) result;
    }

    public static char[] add(@Nullable char[] array1, int index, @Nullable char[] array2) {
        Object result = realAddArr(array1, index, array2, Character.TYPE);
        if (result == null) return null;
        return (char[]) result;
    }

    @Nullable
    public static byte[] add(@Nullable byte[] array1, int index, @Nullable byte[] array2) {
        Object result = realAddArr(array1, index, array2, Byte.TYPE);
        if (result == null) return null;
        return (byte[]) result;
    }

    @Nullable
    public static short[] add(@Nullable short[] array1, int index, @Nullable short[] array2) {
        Object result = realAddArr(array1, index, array2, Short.TYPE);
        if (result == null) return null;
        return (short[]) result;
    }

    @Nullable
    public static int[] add(@Nullable int[] array1, int index, @Nullable int[] array2) {
        Object result = realAddArr(array1, index, array2, Integer.TYPE);
        if (result == null) return null;
        return (int[]) result;
    }

    @Nullable
    public static long[] add(@Nullable long[] array1, int index, @Nullable long[] array2) {
        Object result = realAddArr(array1, index, array2, Long.TYPE);
        if (result == null) return null;
        return (long[]) result;
    }

    @Nullable
    public static float[] add(@Nullable float[] array1, int index, @Nullable float[] array2) {
        Object result = realAddArr(array1, index, array2, Float.TYPE);
        if (result == null) return null;
        return (float[]) result;
    }

    @Nullable
    public static double[] add(@Nullable double[] array1, int index, @Nullable double[] array2) {
        Object result = realAddArr(array1, index, array2, Double.TYPE);
        if (result == null) return null;
        return (double[]) result;
    }

    @Nullable
    private static Object realAddArr(@Nullable Object array1, int index, @Nullable Object array2, Class clss) {
        if (array1 == null && array2 == null) return null;
        int len1 = getLength(array1);
        int len2 = getLength(array2);
        if (len1 == 0) {
            if (index != 0) {
                throw new IndexOutOfBoundsException("Index: " + index + ", array1 Length: 0");
            }
            return realCopy(array2);
        }
        if (len2 == 0) {
            return realCopy(array1);
        }
        if (index > len1 || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", array1 Length: " + len1);
        }
        Object joinedArray = Array.newInstance(array1.getClass().getComponentType(), len1 + len2);
        if (index == len1) {
            System.arraycopy(array1, 0, joinedArray, 0, len1);
            System.arraycopy(array2, 0, joinedArray, len1, len2);
        } else if (index == 0) {
            System.arraycopy(array2, 0, joinedArray, 0, len2);
            System.arraycopy(array1, 0, joinedArray, len2, len1);
        } else {
            System.arraycopy(array1, 0, joinedArray, 0, index);
            System.arraycopy(array2, 0, joinedArray, index, len2);
            System.arraycopy(array1, index, joinedArray, index + len2, len1 - index);
        }
        return joinedArray;
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     * whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, 0, null)      = [null]
     * ArrayUtils.add(null, 0, "a")       = ["a"]
     * ArrayUtils.add(["a"], 1, null)     = ["a", null]
     * ArrayUtils.add(["a"], 1, "b")      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], 3, "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param array   the array to realAdd the element to, may be <code>null</code>
     * @param index   the position of the new object
     * @param element the object to realAdd
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (index < 0 || index > array.length).
     */
    @NonNull
    public static <T> T[] add(@Nullable T[] array, int index, @Nullable T element) {
        Class clss;
        if (array != null) {
            clss = array.getClass().getComponentType();
        } else if (element != null) {
            clss = element.getClass();
        } else {
            return (T[]) new Object[]{null};
        }
        return (T[]) realAdd(array, index, element, clss);
    }

    @NonNull
    public static boolean[] add(@Nullable boolean[] array, int index, boolean element) {
        return (boolean[]) realAdd(array, index, element, Boolean.TYPE);
    }

    @NonNull
    public static char[] add(@Nullable char[] array, int index, char element) {
        return (char[]) realAdd(array, index, element, Character.TYPE);
    }

    @NonNull
    public static byte[] add(@Nullable byte[] array, int index, byte element) {
        return (byte[]) realAdd(array, index, element, Byte.TYPE);
    }

    @NonNull
    public static short[] add(@Nullable short[] array, int index, short element) {
        return (short[]) realAdd(array, index, element, Short.TYPE);
    }

    @NonNull
    public static int[] add(@Nullable int[] array, int index, int element) {
        return (int[]) realAdd(array, index, element, Integer.TYPE);
    }

    @NonNull
    public static long[] add(@Nullable long[] array, int index, long element) {
        return (long[]) realAdd(array, index, element, Long.TYPE);
    }

    @NonNull
    public static float[] add(@Nullable float[] array, int index, float element) {
        return (float[]) realAdd(array, index, element, Float.TYPE);
    }

    @NonNull
    public static double[] add(@Nullable double[] array, int index, double element) {
        return (double[]) realAdd(array, index, element, Double.TYPE);
    }

    @NonNull
    private static Object realAdd(@Nullable Object array, int index, @Nullable Object element, Class clss) {
        if (array == null) {
            if (index != 0) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
            }
            Object joinedArray = Array.newInstance(clss, 1);
            Array.set(joinedArray, 0, element);
            return joinedArray;
        }
        int length = Array.getLength(array);
        if (index > length || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }
        Object result = Array.newInstance(clss, length + 1);
        System.arraycopy(array, 0, result, 0, index);
        Array.set(result, index, element);
        if (index < length) {
            System.arraycopy(array, index, result, index + 1, length - index);
        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    // remove
    ///////////////////////////////////////////////////////////////////////////

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (substracts one from
     * their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.remove(["a"], 0)           = []
     * ArrayUtils.remove(["a", "b"], 0)      = ["b"]
     * ArrayUtils.remove(["a", "b"], 1)      = ["a"]
     * ArrayUtils.remove(["a", "b", "c"], 1) = ["a", "c"]
     * </pre>
     *
     * @param array the array to remove the element from, may be <code>null</code>
     * @param index the position of the element to be removed
     * @return A new array containing the existing elements except the element
     * at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (index < 0 || index >= array.length)
     */
    @Nullable
    public static Object[] remove(@Nullable Object[] array, int index) {
        if (array == null) return null;
        return (Object[]) remove((Object) array, index);
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (substracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <pre>
     * ArrayUtils.removeElement(null, "a")            = null
     * ArrayUtils.removeElement([], "a")              = []
     * ArrayUtils.removeElement(["a"], "b")           = ["a"]
     * ArrayUtils.removeElement(["a", "b"], "a")      = ["b"]
     * ArrayUtils.removeElement(["a", "b", "a"], "a") = ["b", "a"]
     * </pre>
     *
     * @param array   the array to remove the element from, may be <code>null</code>
     * @param element the element to be removed
     * @return A new array containing the existing elements except the first
     * occurrence of the specified element.
     */
    @Nullable
    public static Object[] removeElement(@Nullable Object[] array, @Nullable Object element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return copy(array);
        }
        return remove(array, index);
    }

    @Nullable
    public static boolean[] remove(@Nullable boolean[] array, int index) {
        if (array == null) return null;
        return (boolean[]) remove((Object) array, index);
    }

    @Nullable
    public static boolean[] removeElement(@Nullable boolean[] array, boolean element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return copy(array);
        }
        return remove(array, index);
    }

    @Nullable
    public static byte[] remove(@Nullable byte[] array, int index) {
        if (array == null) return null;
        return (byte[]) remove((Object) array, index);
    }

    @Nullable
    public static byte[] removeElement(@Nullable byte[] array, byte element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return copy(array);
        }
        return remove(array, index);
    }

    @Nullable
    public static char[] remove(@Nullable char[] array, int index) {
        if (array == null) return null;
        return (char[]) remove((Object) array, index);
    }

    @Nullable
    public static char[] removeElement(@Nullable char[] array, char element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return copy(array);
        }
        return remove(array, index);
    }

    @Nullable
    public static double[] remove(@Nullable double[] array, int index) {
        if (array == null) return null;
        return (double[]) remove((Object) array, index);
    }

    @Nullable
    public static double[] removeElement(@Nullable double[] array, double element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return copy(array);
        }
        //noinspection ConstantConditions
        return remove(array, index);
    }

    @Nullable
    public static float[] remove(@Nullable float[] array, int index) {
        if (array == null) return null;
        return (float[]) remove((Object) array, index);
    }

    @Nullable
    public static float[] removeElement(@Nullable float[] array, float element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return copy(array);
        }
        return remove(array, index);
    }

    @Nullable
    public static int[] remove(@Nullable int[] array, int index) {
        if (array == null) return null;
        return (int[]) remove((Object) array, index);
    }

    @Nullable
    public static int[] removeElement(@Nullable int[] array, int element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return copy(array);
        }
        return remove(array, index);
    }

    @Nullable
    public static long[] remove(@Nullable long[] array, int index) {
        if (array == null) return null;
        return (long[]) remove((Object) array, index);
    }

    @Nullable
    public static long[] removeElement(@Nullable long[] array, long element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return copy(array);
        }
        return remove(array, index);
    }

    @Nullable
    public static short[] remove(@Nullable short[] array, int index) {
        if (array == null) return null;
        return (short[]) remove((Object) array, index);
    }

    @Nullable
    public static short[] removeElement(@Nullable short[] array, short element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return copy(array);
        }
        return remove(array, index);
    }

    @NonNull
    private static Object remove(@NonNull Object array, int index) {
        int length = getLength(array);
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }

        Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }

        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    // object indexOf
    ///////////////////////////////////////////////////////////////////////////

    public static int indexOf(@Nullable Object[] array, @Nullable Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    public static int indexOf(@Nullable Object[] array, @Nullable final Object objectToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(@Nullable Object[] array, @Nullable Object objectToFind) {
        return lastIndexOf(array, objectToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable Object[] array, @Nullable Object objectToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i >= 0; i--) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(@Nullable Object[] array, @Nullable Object objectToFind) {
        return indexOf(array, objectToFind) != INDEX_NOT_FOUND;
    }

    ///////////////////////////////////////////////////////////////////////////
    // long indexOf
    ///////////////////////////////////////////////////////////////////////////

    public static int indexOf(@Nullable long[] array, long valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(@Nullable long[] array, long valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(@Nullable long[] array, long valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable long[] array, long valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(@Nullable long[] array, long valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    ///////////////////////////////////////////////////////////////////////////
    // int indexOf
    ///////////////////////////////////////////////////////////////////////////

    public static int indexOf(@Nullable int[] array, int valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(@Nullable int[] array, int valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(@Nullable int[] array, int valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable int[] array, int valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(@Nullable int[] array, int valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    ///////////////////////////////////////////////////////////////////////////
    // short indexOf
    ///////////////////////////////////////////////////////////////////////////

    public static int indexOf(@Nullable short[] array, short valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(@Nullable short[] array, short valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(@Nullable short[] array, short valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable short[] array, short valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(@Nullable short[] array, short valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    ///////////////////////////////////////////////////////////////////////////
    // char indexOf
    ///////////////////////////////////////////////////////////////////////////

    public static int indexOf(@Nullable char[] array, char valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(@Nullable char[] array, char valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(@Nullable char[] array, char valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable char[] array, char valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(@Nullable char[] array, char valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    ///////////////////////////////////////////////////////////////////////////
    // byte indexOf
    ///////////////////////////////////////////////////////////////////////////

    public static int indexOf(@Nullable byte[] array, byte valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(@Nullable byte[] array, byte valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(@Nullable byte[] array, byte valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable byte[] array, byte valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(@Nullable byte[] array, byte valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    ///////////////////////////////////////////////////////////////////////////
    // double indexOf
    ///////////////////////////////////////////////////////////////////////////

    public static int indexOf(@Nullable double[] array, double valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(@Nullable double[] array, double valueToFind, double tolerance) {
        return indexOf(array, valueToFind, 0, tolerance);
    }

    public static int indexOf(@Nullable double[] array, double valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int indexOf(@Nullable double[] array, double valueToFind, int startIndex, double tolerance) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        double min = valueToFind - tolerance;
        double max = valueToFind + tolerance;
        for (int i = startIndex; i < array.length; i++) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(@Nullable double[] array, double valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable double[] array, double valueToFind, double tolerance) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE, tolerance);
    }

    public static int lastIndexOf(@Nullable double[] array, double valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(@Nullable double[] array, double valueToFind, int startIndex, double tolerance) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        double min = valueToFind - tolerance;
        double max = valueToFind + tolerance;
        for (int i = startIndex; i >= 0; i--) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(@Nullable double[] array, double valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    public static boolean contains(@Nullable double[] array, double valueToFind, double tolerance) {
        return indexOf(array, valueToFind, 0, tolerance) != INDEX_NOT_FOUND;
    }

    ///////////////////////////////////////////////////////////////////////////
    // float indexOf
    ///////////////////////////////////////////////////////////////////////////

    public static int indexOf(@Nullable float[] array, float valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(@Nullable float[] array, float valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(@Nullable float[] array, float valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable float[] array, float valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(@Nullable float[] array, float valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    ///////////////////////////////////////////////////////////////////////////
    // bool indexOf
    ///////////////////////////////////////////////////////////////////////////

    public static int indexOf(@Nullable boolean[] array, boolean valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(@Nullable boolean[] array, boolean valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(@Nullable boolean[] array, boolean valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable boolean[] array, boolean valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(@Nullable boolean[] array, boolean valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    ///////////////////////////////////////////////////////////////////////////
    // char converters
    ///////////////////////////////////////////////////////////////////////////

    @Nullable
    public static char[] toPrimitive(@Nullable Character[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new char[0];
        }
        final char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].charValue();
        }
        return result;
    }

    @Nullable
    public static char[] toPrimitive(@Nullable Character[] array, char valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new char[0];
        }
        final char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            Character b = array[i];
            result[i] = (b == null ? valueForNull : b.charValue());
        }
        return result;
    }

    @Nullable
    public static Character[] toObject(@Nullable char[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Character[0];
        }
        final Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = new Character(array[i]);
        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    // long converters
    ///////////////////////////////////////////////////////////////////////////

    @Nullable
    public static long[] toPrimitive(@Nullable Long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new long[0];
        }
        final long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    @Nullable
    public static long[] toPrimitive(@Nullable Long[] array, long valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new long[0];
        }
        final long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            Long b = array[i];
            result[i] = (b == null ? valueForNull : b.longValue());
        }
        return result;
    }

    @Nullable
    public static Long[] toObject(@Nullable long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Long[0];
        }
        final Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = new Long(array[i]);
        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    // int converters
    ///////////////////////////////////////////////////////////////////////////

    @Nullable
    public static int[] toPrimitive(@Nullable Integer[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new int[0];
        }
        final int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    @Nullable
    public static int[] toPrimitive(@Nullable Integer[] array, int valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new int[0];
        }
        final int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            Integer b = array[i];
            result[i] = (b == null ? valueForNull : b.intValue());
        }
        return result;
    }

    @Nullable
    public static Integer[] toObject(@Nullable int[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Integer[0];
        }
        final Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = new Integer(array[i]);
        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    // short converters
    ///////////////////////////////////////////////////////////////////////////

    @Nullable
    public static short[] toPrimitive(@Nullable Short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new short[0];
        }
        final short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    @Nullable
    public static short[] toPrimitive(@Nullable Short[] array, short valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new short[0];
        }
        final short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            Short b = array[i];
            result[i] = (b == null ? valueForNull : b.shortValue());
        }
        return result;
    }

    @Nullable
    public static Short[] toObject(@Nullable short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Short[0];
        }
        final Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = new Short(array[i]);
        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    // byte converters
    ///////////////////////////////////////////////////////////////////////////

    @Nullable
    public static byte[] toPrimitive(@Nullable Byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new byte[0];
        }
        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    @Nullable
    public static byte[] toPrimitive(@Nullable Byte[] array, byte valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new byte[0];
        }
        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            Byte b = array[i];
            result[i] = (b == null ? valueForNull : b.byteValue());
        }
        return result;
    }

    @Nullable
    public static Byte[] toObject(@Nullable byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Byte[0];
        }
        final Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = new Byte(array[i]);
        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    // double converters
    ///////////////////////////////////////////////////////////////////////////

    @Nullable
    public static double[] toPrimitive(@Nullable Double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new double[0];
        }
        final double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    @Nullable
    public static double[] toPrimitive(@Nullable Double[] array, double valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new double[0];
        }
        final double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            Double b = array[i];
            result[i] = (b == null ? valueForNull : b.doubleValue());
        }
        return result;
    }

    @Nullable
    public static Double[] toObject(@Nullable double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Double[0];
        }
        final Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = new Double(array[i]);
        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    // float converters
    ///////////////////////////////////////////////////////////////////////////

    @Nullable
    public static float[] toPrimitive(@Nullable Float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new float[0];
        }
        final float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    @Nullable
    public static float[] toPrimitive(@Nullable Float[] array, float valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new float[0];
        }
        final float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            Float b = array[i];
            result[i] = (b == null ? valueForNull : b.floatValue());
        }
        return result;
    }

    @Nullable
    public static Float[] toObject(@Nullable float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Float[0];
        }
        final Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = new Float(array[i]);
        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    // boolean converters
    ///////////////////////////////////////////////////////////////////////////

    @Nullable
    public static boolean[] toPrimitive(@Nullable Boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new boolean[0];
        }
        final boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].booleanValue();
        }
        return result;
    }

    @Nullable
    public static boolean[] toPrimitive(@Nullable Boolean[] array, boolean valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new boolean[0];
        }
        final boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            Boolean b = array[i];
            result[i] = (b == null ? valueForNull : b.booleanValue());
        }
        return result;
    }

    @Nullable
    public static Boolean[] toObject(@Nullable boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new Boolean[0];
        }
        final Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (array[i] ? Boolean.TRUE : Boolean.FALSE);
        }
        return result;
    }

    @NonNull
    public static <T> List<T> asList(@Nullable T... array) {
        if (array == null || array.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.asList(array);
    }

    @NonNull
    public static <T> List<T> asUnmodifiableList(@Nullable T... array) {
        return Collections.unmodifiableList(asList(array));
    }

    @NonNull
    public static <T> List<T> asArrayList(@Nullable T... array) {
        List<T> list = new ArrayList<>();
        if (array == null || array.length == 0) return list;
        list.addAll(Arrays.asList(array));
        return list;
    }

    @NonNull
    public static <T> List<T> asLinkedList(@Nullable T... array) {
        List<T> list = new LinkedList<>();
        if (array == null || array.length == 0) return list;
        list.addAll(Arrays.asList(array));
        return list;
    }

    public static <T> void sort(@Nullable T[] array, Comparator<? super T> c) {
        if (array == null || array.length < 2) return;
        Arrays.sort(array, c);
    }

    public static void sort(@Nullable byte[] array) {
        if (array == null || array.length < 2) return;
        Arrays.sort(array);
    }

    public static void sort(@Nullable char[] array) {
        if (array == null || array.length < 2) return;
        Arrays.sort(array);
    }

    public static void sort(@Nullable double[] array) {
        if (array == null || array.length < 2) return;
        Arrays.sort(array);
    }

    public static void sort(@Nullable float[] array) {
        if (array == null || array.length < 2) return;
        Arrays.sort(array);
    }

    public static void sort(@Nullable int[] array) {
        if (array == null || array.length < 2) return;
        Arrays.sort(array);
    }

    public static void sort(@Nullable long[] array) {
        if (array == null || array.length < 2) return;
        Arrays.sort(array);
    }

    public static void sort(@Nullable short[] array) {
        if (array == null || array.length < 2) return;
        Arrays.sort(array);
    }

    /**
     * Executes the given closure on each element in the array.
     * <p>
     * If the input array or closure is null, there is no change made.
     *
     * @param array   The array.
     * @param closure the closure to perform, may be null
     */
    public static <E> void forAllDo(@Nullable Object array, @Nullable Closure<E> closure) {
        if (array == null || closure == null) return;
        if (array instanceof Object[]) {
            Object[] objects = (Object[]) array;
            for (int i = 0, length = objects.length; i < length; i++) {
                Object ele = objects[i];
                closure.execute(i, (E) ele);
            }
        } else if (array instanceof boolean[]) {
            boolean[] booleans = (boolean[]) array;
            for (int i = 0, length = booleans.length; i < length; i++) {
                boolean ele = booleans[i];
                closure.execute(i, (E) (ele ? Boolean.TRUE : Boolean.FALSE));
            }
        } else if (array instanceof byte[]) {
            byte[] bytes = (byte[]) array;
            for (int i = 0, length = bytes.length; i < length; i++) {
                byte ele = bytes[i];
                closure.execute(i, (E) Byte.valueOf(ele));
            }
        } else if (array instanceof char[]) {
            char[] chars = (char[]) array;
            for (int i = 0, length = chars.length; i < length; i++) {
                char ele = chars[i];
                closure.execute(i, (E) Character.valueOf(ele));
            }
        } else if (array instanceof short[]) {
            short[] shorts = (short[]) array;
            for (int i = 0, length = shorts.length; i < length; i++) {
                short ele = shorts[i];
                closure.execute(i, (E) Short.valueOf(ele));
            }
        } else if (array instanceof int[]) {
            int[] ints = (int[]) array;
            for (int i = 0, length = ints.length; i < length; i++) {
                int ele = ints[i];
                closure.execute(i, (E) Integer.valueOf(ele));
            }
        } else if (array instanceof long[]) {
            long[] longs = (long[]) array;
            for (int i = 0, length = longs.length; i < length; i++) {
                long ele = longs[i];
                closure.execute(i, (E) Long.valueOf(ele));
            }
        } else if (array instanceof float[]) {
            float[] floats = (float[]) array;
            for (int i = 0, length = floats.length; i < length; i++) {
                float ele = floats[i];
                closure.execute(i, (E) Float.valueOf(ele));
            }
        } else if (array instanceof double[]) {
            double[] doubles = (double[]) array;
            for (int i = 0, length = doubles.length; i < length; i++) {
                double ele = doubles[i];
                closure.execute(i, (E) Double.valueOf(ele));
            }
        } else {
            throw new IllegalArgumentException("Not an array: " + array.getClass());
        }
    }

    /**
     * Return the string of array.
     *
     * @param array The array.
     * @return the string of array
     */
    @NonNull
    public static String toString(@Nullable Object array) {
        if (array == null) return "null";
        if (array instanceof Object[]) {
            return Arrays.deepToString((Object[]) array);
        } else if (array instanceof boolean[]) {
            return Arrays.toString((boolean[]) array);
        } else if (array instanceof byte[]) {
            return Arrays.toString((byte[]) array);
        } else if (array instanceof char[]) {
            return Arrays.toString((char[]) array);
        } else if (array instanceof double[]) {
            return Arrays.toString((double[]) array);
        } else if (array instanceof float[]) {
            return Arrays.toString((float[]) array);
        } else if (array instanceof int[]) {
            return Arrays.toString((int[]) array);
        } else if (array instanceof long[]) {
            return Arrays.toString((long[]) array);
        } else if (array instanceof short[]) {
            return Arrays.toString((short[]) array);
        }
        throw new IllegalArgumentException("Array has incompatible type: " + array.getClass());
    }

    public interface Closure<E> {
        void execute(int index, E item);
    }
}
