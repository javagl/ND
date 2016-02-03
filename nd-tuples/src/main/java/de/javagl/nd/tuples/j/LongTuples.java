/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.javagl.nd.tuples.j;

import java.nio.LongBuffer;
import java.util.AbstractList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;

import de.javagl.nd.tuples.d.DoubleTuple;
import de.javagl.nd.tuples.d.DoubleTuples;
import de.javagl.nd.tuples.d.MutableDoubleTuple;

/**
 * Utility methods related to {@link LongTuple}s.<br>
 * <br>
 * Many of the methods in this class receive a mutable <code>result</code> 
 * tuple as the last parameter. Unless otherwise noted, this tuple will be 
 * returned by the function call. If the given <code>result</code> tuple is 
 * <code>null</code>, then a new tuple will be created and returned.<br>
 * <br>
 * Unless otherwise noted: The <code>result</code> tuple may be identical
 * to any of the input tuples.
 * <br>
 * Unless otherwise noted: The tuples that serve as the input for the
 * methods may not be <code>null</code>
 */
public class LongTuples
{
    /**
     * Size of the pool for frequently used zero-tuples
     */
    private static final int ZERO_POOL_SIZE = 5;

    /**
     * The pool of frequently used zero-tuples.
     */
    private static final LongTuple ZEROS[] = new LongTuple[ZERO_POOL_SIZE];
    static
    {
        for (int i=0; i<ZERO_POOL_SIZE; i++)
        {
            ZEROS[i] = createZero(i);
        }
    }

    /**
     * Creates a new zero tuple with the given size.
     *
     * @param size The size
     * @return The zero tuple
     */
    private static LongTuple createZero(final int size)
    {
        return constant(size, 0);
    }

    /**
     * Returns a tuple with the specified size, containing
     * all zeros.
     *
     * @param size The size
     * @return The zero tuple.
     * @throws IllegalArgumentException If the size is smaller than 0
     */
    public static LongTuple zero(int size)
    {
        if (size >= 0 && size < ZERO_POOL_SIZE)
        {
            return ZEROS[size];
        }
        return createZero(size);
    }

    /**
     * Returns a tuple with the specified size, where each
     * element is the given value. Note that the returned
     * tuple is storage-efficient, meaning that it its
     * memory requirements are not depending on the given
     * size.
     *
     * @param size The size
     * @param value The value
     * @return The tuple.
     * @throws IllegalArgumentException If the size is smaller than 0
     */
    public static LongTuple constant(int size, long value)
    {
        return new ConstantLongTuple(size, value);
    }

    /**
     * Creates a new tuple with the given size.
     *
     * @param size The size.
     * @return The new tuple.
     * @throws IllegalArgumentException If the size is smaller than 0
     */
    public static MutableLongTuple create(int size)
    {
        return new DefaultLongTuple(size);
    }

    /**
     * Creates a new tuple which is a copy of 
     * the given one.
     *
     * @param other The other tuple.
     * @return The new tuple.
     * @throws NullPointerException If the other tuple is <code>null</code>
     */
    public static MutableLongTuple copy(LongTuple other)
    {
        return new DefaultLongTuple(other);
    }

    /**
     * Creates a new tuple with the given values.
     * The given array will be cloned, so that changes in the given array
     * will not be visible in the returned tuple. In order to create
     * a <i>view</i> on a given array, {@link #wrap(long...)} may be
     * used. 
     *
     * @param values The values
     * @return The new tuple
     * @throws NullPointerException If the given array is <code>null</code>
     */
    public static MutableLongTuple of(long ... values)
    {
        return new DefaultLongTuple(values.clone());
    }

    /**
     * Creates a new tuple that is a <i>view</i>
     * on the given values. Changes in the returned tuple will be
     * visible in the given array, and vice versa.
     * 
     * @param values The values
     * @return The view on the given values
     * @throws NullPointerException If the given array is <code>null</code>
     */
    public static MutableLongTuple wrap(long ... values)
    {
        return new DefaultLongTuple(values);
    }

    /**
     * Returns a new tuple that is a <i>view</i> on the given buffer.
     * 
     * @param buffer The buffer for the tuple
     * @throws NullPointerException If the given buffer is <code>null</code>
     * @return The new tuple
     */
    public static MutableLongTuple wrap(LongBuffer buffer)
    {
        return new BufferLongTuple(buffer);
    }

    /**
     * Returns a new tuple that is a <i>view</i> on the
     * specified portion of the given array
     * 
     * @param data The data for the tuple
     * @param offset The offset in the array
     * @param size The size of the tuple
     * @throws NullPointerException If the given data array is <code>null</code>
     * @throws IllegalArgumentException If the given offset is negative,
     * or if <code>offset+size &gt;= data.length</code>
     * @return The new tuple
     */
    public static MutableLongTuple wrap(
        long[] data, int offset, int size)
    {
        return new ArrayLongTuple(data, offset, size);
    }


    /**
     * Creates a new tuple that is a <i>view</i>
     * on the specified portion of the given parent. Changes in the
     * parent will be visible in the returned tuple.
     * 
     * @param parent The parent tuple
     * @param fromIndex The start index in the parent, inclusive
     * @param toIndex The end index in the parent, exclusive
     * @throws NullPointerException If the given parent is <code>null</code>
     * @throws IllegalArgumentException If the given indices are invalid.
     * This is the case when <code>fromIndex &lt; 0</code>, 
     * <code>fromIndex &gt; toIndex</code>, or 
     * <code>toIndex &gt; {@link Tuple#getSize() parent.getSize()}</code>,
     * @return The new tuple
     */
    static LongTuple createSubTuple(
        LongTuple parent, int fromIndex, int toIndex)
    {
        return new SubLongTuple(parent, fromIndex, toIndex);
    }

    /**
     * Creates a new tuple that is a <i>view</i>
     * on the specified portion of the given parent. Changes in the
     * parent will be visible in the returned tuple, and vice versa.
     * 
     * @param parent The parent tuple
     * @param fromIndex The start index in the parent, inclusive
     * @param toIndex The end index in the parent, exclusive
     * @throws NullPointerException If the given parent is <code>null</code>
     * @throws IllegalArgumentException If the given indices are invalid.
     * This is the case when <code>fromIndex &lt; 0</code>, 
     * <code>fromIndex &gt; toIndex</code>, or 
     * <code>toIndex &gt; {@link Tuple#getSize() parent.getSize()}</code>,
     * @return The new tuple
     */
    static MutableLongTuple createSubTuple(
        MutableLongTuple parent, int fromIndex, int toIndex)
    {
        return new MutableSubLongTuple(parent, fromIndex, toIndex);
    }


    /**
     * Creates a new array from the contents of the given tuple
     * 
     * @param t The tuple
     * @return The array
     */
    public static long[] toArray(LongTuple t)
    {
        int d = t.getSize();
        long result[] = new long[d];
        for (int i=0; i<d; i++)
        {
            result[i] = t.get(i);
        }
        return result;
    }

    /**
     * Returns a <i>view</i> on the given tuple as an unmodifiable list.
     * Changes in the backing tuple will be visible in the returned list.
     * 
     * @param t The tuple
     * @return The list
     * @throws NullPointerException If the given tuple is <code>null</code>
     */
    public static List<Long> asList(final LongTuple t)
    {
        if (t == null)
        {
            throw new NullPointerException("The tuple may not be null");
        }
        return new AbstractList<Long>()
        {
            @Override
            public Long get(int index)
            {
                return t.get(index);
            }

            @Override
            public int size()
            {
                return t.getSize();
            }
        };
    }

    /**
     * Returns a <i>view</i> on the given tuple as a list that does not
     * allow <i>structural</i> modifications. Changes in the list will
     * write through to the given tuple. Changes in the backing tuple 
     * will be visible in the returned list. The list will not permit
     * <code>null</code> elements.
     * 
     * @param t The tuple
     * @return The list
     * @throws NullPointerException If the given tuple is <code>null</code>
     */
    public static List<Long> asList(final MutableLongTuple t)
    {
        if (t == null)
        {
            throw new NullPointerException("The tuple may not be null");
        }
        return new AbstractList<Long>()
        {
            @Override
            public Long get(int index)
            {
                return t.get(index);
            }

            @Override
            public int size()
            {
                return t.getSize();
            }

            @Override
            public Long set(int index, Long element)
            {
                long oldValue = t.get(index);
                t.set(index, element);
                return oldValue;
            }
        };
    }


    /**
     * Set all elements of the given tuple to the given value
     * 
     * @param t The tuple
     * @param v The value
     */
    public static void set(MutableLongTuple t, long v)
    {
        for (int i=0; i<t.getSize(); i++)
        {
            t.set(i, v);
        }
    }


    /**
     * Reverse the given tuple. This will create a new tuple whose elements
     * are the same as in the given tuple, but in reverse order.<br>
     * <br>
     * In order to create a reversed <i>view</i> on a tuple, the 
     * {@link #reversed(LongTuple)} method may be used.   
     * 
     * @param t The input tuple
     * @param result The result tuple
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple reverse(
        LongTuple t, MutableLongTuple result)
    {
        result = validate(t, result);
        if (t == result)
        {
            int n = t.getSize();
            int nh = n / 2;
            for (int i = 0; i < nh; i++)
            {
                long temp = result.get(i);
                result.set(i, result.get(n - 1 - i));
                result.set(n - 1 - i, temp);
            }
        }
        else
        {
            int n = t.getSize();
            for (int i = 0; i < n; i++)
            {
                result.set(i, t.get(n - 1 - i));
            }
        }
        return result;
    }    

    /**
     * Creates a new tuple that is a reversed <i>view</i> on the given
     * tuple. Changes in the given tuple will be visible in the returned
     * tuple.<br>
     * <br>
     * In order to create a new, reversed tuple from a given one, the
     * {@link #reverse(LongTuple, MutableLongTuple)} method may 
     * be used.
     * 
     * @param t The tuple
     * @return The reversed view on the tuple
     * @throws NullPointerException If the given tuple is <code>null</code>
     */
    public static LongTuple reversed(LongTuple t)
    {
        Objects.requireNonNull(t, "The input tuple is null");
        return new AbstractLongTuple()
        {
            @Override
            public int getSize()
            {
                return t.getSize();
            }

            @Override
            public long get(int index)
            {
                return t.get(t.getSize() - 1 - index);
            }
        };
    }

    /**
     * Creates a new tuple that is a reversed <i>view</i> on the given
     * tuple. Changes in the given tuple will be visible in the returned
     * tuple, and vice versa.<br>
     * <br>
     * In order to create a new, reversed tuple from a given one, the
     * {@link #reverse(LongTuple, MutableLongTuple)} method may 
     * be used.
     * 
     * @param t The tuple
     * @return The reversed view on the tuple
     * @throws NullPointerException If the given tuple is <code>null</code>
     */
    public static MutableLongTuple reversed(MutableLongTuple t)
    {
        Objects.requireNonNull(t, "The input tuple is null");
        return new AbstractMutableLongTuple()
        {
            @Override
            public int getSize()
            {
                return t.getSize();
            }

            @Override
            public long get(int index)
            {
                return t.get(t.getSize() - 1 - index);
            }

            @Override
            public void set(int index, long value)
            {
                t.set(t.getSize() - 1 - index, value);
            }
        };
    }

    /**
     * Add an element with the given value at the given index to the given 
     * tuple, creating a new tuple whose {@link Tuple#getSize() size} is
     * one larger than that of the given tuple.
     *  
     * @param t The tuple
     * @param index The index where the element should be added
     * @param value The value of the new element
     * @param result The result tuple
     * @return The result tuple
     * @throws IndexOutOfBoundsException If the given index is negative
     * or greater than the {@link Tuple#getSize() size} of the given
     * tuple
     * @throws IllegalArgumentException If the given result tuple is not
     * <code>null</code> and its {@link Tuple#getSize() size} is not 
     * the size of the input tuple plus one.
     */
    public static MutableLongTuple insertElementAt(
        MutableLongTuple t, int index, long value, 
        MutableLongTuple result)
    {
        if (index < 0)
        {
            throw new IndexOutOfBoundsException(
                "Index "+index+" is negative");
        }
        if (index > t.getSize()) // Note: index==t.getSize() is valid!
        {
            throw new IndexOutOfBoundsException(
                "Index "+index+", size "+t.getSize());
        }
        if (result == null)
        {
            result = LongTuples.create(t.getSize() + 1);
        }
        else if (result.getSize() != t.getSize() + 1)
        {
            throw new IllegalArgumentException(
                "Input size is " + t.getSize() + ", result size must be " +
                (t.getSize() + 1) + " but is " + result.getSize());
        }
        int counter = 0;
        for (int i=0; i<index; i++)
        {
            result.set(counter, t.get(i));
            counter++;
        }
        result.set(counter, value);
        counter++;
        for (int i=index; i<t.getSize(); i++)
        {
            result.set(counter, t.get(i));
            counter++;
        }
        return result;
    }

    /**
     * Remove the element at the given index from the given tuple, 
     * creating a new tuple whose {@link Tuple#getSize() size} is
     * one smaller than that of the given tuple.
     *  
     * @param t The tuple
     * @param index The index of the element that should be removed
     * @param result The result tuple
     * @return The result tuple
     * @throws IndexOutOfBoundsException If the given index is negative
     * or not smaller than the {@link Tuple#getSize() size} of the given
     * tuple
     * @throws IllegalArgumentException If the given result tuple is not
     * <code>null</code> and its {@link Tuple#getSize() size} is not 
     * the size of the input tuple minus one.
     */
    public static MutableLongTuple removeElementAt(
        MutableLongTuple t, int index, MutableLongTuple result)
    {
        Utils.checkForValidIndex(index, t.getSize());
        if (result == null)
        {
            result = LongTuples.create(t.getSize() - 1);
        }
        else if (result.getSize() != t.getSize() - 1)
        {
            throw new IllegalArgumentException(
                "Input size is " + t.getSize() + ", result size must be " +
                (t.getSize() - 1) + " but is " + result.getSize());
        }
        int counter = 0;
        for (int i=0; i<index; i++)
        {
            result.set(counter, t.get(i));
            counter++;
        }
        for (int i=index+1; i<t.getSize(); i++)
        {
            result.set(counter, t.get(i));
            counter++;
        }
        return result;
    }


    /**
     * Clamp the elements of the given tuple to be in the specified range,
     * and write the result into the given result tuple.
     * 
     * @param t The input tuple
     * @param min The minimum value
     * @param max The maximum value
     * @param result The result tuple
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple clamp(
        LongTuple t, long min, long max, MutableLongTuple result)
    {
        result = validate(t, result);
        for (int i=0; i<result.getSize(); i++)
        {
            long v = t.get(i);
            long r = Math.min(max, Math.max(min, v));
            result.set(i, r);
        }
        return result;
    }

    /**
     * Clamp the elements of the given tuple to be in the specified range,
     * and write the result into the given result tuple.
     * 
     * @param t The input tuple
     * @param min The minimum values
     * @param max The maximum values
     * @param result The result tuple
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple clamp(
        LongTuple t, LongTuple min, LongTuple max, 
        MutableLongTuple result)
    {
        Utils.checkForEqualSize(min, max);
        result = validate(t, result);
        for (int i=0; i<result.getSize(); i++)
        {
            long v = t.get(i);
            long minV = min.get(i);
            long maxV = max.get(i);
            long r = Math.min(maxV, Math.max(minV, v));
            result.set(i, r);
        }
        return result;
    }









    /**
     * Negates the given tuple, and store the result in the given result tuple.

     * @param t0 The tuple to negate
     * @param result The tuple that will store the result
     * @return The result tuple
     */
    public static MutableLongTuple negate(
        LongTuple t0, MutableLongTuple result)
    {
        return LongTupleFunctions.apply(
            t0, (a)->(-a), result);
    }

    /**
     * Add the given input tuples, and store the result in
     * the given result tuple.
     *
     * @param t0 The first input tuple
     * @param t1 The second input tuple
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple add(
        LongTuple t0, LongTuple t1, MutableLongTuple result)
    {
        return LongTupleFunctions.apply(
            t0, t1, (a,b)->(a+b), result);
    }

    /**
     * Subtract the given input tuples, and store the result in
     * the given result tuple.
     *
     * @param t0 The first input tuple
     * @param t1 The second input tuple
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple subtract(
        LongTuple t0, LongTuple t1, MutableLongTuple result)
    {
        return LongTupleFunctions.apply(
            t0, t1, (a,b)->(a-b), result);
    }

    /**
     * Multiply the elements of the given input tuples, and store 
     * the results in the given result tuple.
     *
     * @param t0 The first input tuple
     * @param t1 The second input tuple
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple multiply(
        LongTuple t0, LongTuple t1, MutableLongTuple result)
    {
        return LongTupleFunctions.apply(
            t0, t1, (a,b)->(a*b), result);
    }

    /**
     * Divide the elements of the given input tuples, and store 
     * the results in the given result tuple.
     *
     * @param t0 The first input tuple
     * @param t1 The second input tuple
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple divide(
        LongTuple t0, LongTuple t1, MutableLongTuple result)
    {
        return LongTupleFunctions.apply(
            t0, t1, (a,b)->(a/b), result);
    }



    /**
     * Add the given value to all elements of the given input
     * tuple, and store the result in the given result tuple.
     *
     * @param t0 The first input tuple
     * @param value The value to add
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuple do not
     * have the same {@link Tuple#getSize() size}
     * as the result tuple
     */
    public static MutableLongTuple add(
        LongTuple t0, long value, MutableLongTuple result)
    {
        return LongTupleFunctions.apply(
            t0, (a)->(a+value), result);
    }

    /**
     * Subtract the given value from all elements of the given input
     * tuple, and store the result in the given result tuple.
     *
     * @param t0 The first input tuple
     * @param value The value to add
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuple do not
     * have the same {@link Tuple#getSize() size}
     * as the result tuple
     */
    public static MutableLongTuple subtract(
        LongTuple t0, long value, MutableLongTuple result)
    {
        return LongTupleFunctions.apply(
            t0, (a)->(a-value), result);
    }


    /**
     * Multiply the given input tuple with the given factor, and
     * store the result in the given result tuple.
     *
     * @param t0 The input tuple
     * @param factor The scaling factor
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple multiply(
        LongTuple t0, long factor, MutableLongTuple result)
    {
        return LongTupleFunctions.apply(
            t0, (a)->(a*factor), result);
    }


    /**
     * Computes <code>t0+factor*t1</code>, and stores the result in the given 
     * result tuple.
     *
     * @param t0 The first input tuple
     * @param factor The scaling factor
     * @param t1 The second input tuple
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple addScaled(
        LongTuple t0, long factor, LongTuple t1, 
        MutableLongTuple result)
    {
        return LongTupleFunctions.apply(
            t0, t1, (a,b)->(a+factor*b), result);
    }


    /**
     * Computes the dot product of the given tuples
     *
     * @param t0 The first input tuple
     * @param t1 The second input tuple
     * @return The dot product
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static long dot(LongTuple t0, LongTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        long result = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            result += t0.get(i) * t1.get(i);
        }
        return result;
    }



    /**
     * Computes the minimum value that occurs in the given tuple,
     * or <code>Long.MAX_VALUE</code> if the given tuple has a
     * size of 0.
     *
     * @param t The input tuple
     * @return The minimum value
     */
    public static long min(LongTuple t)
    {
        return LongTupleFunctions.reduce(
            t, Long.MAX_VALUE, Math::min);
    }

    /**
     * Computes the maximum value that occurs in the given tuple,
     * or <code>Long.MIN_VALUE</code> if the given tuple has a
     * size of 0.
     *
     * @param t The input tuple
     * @return The maximum value
     */
    public static long max(LongTuple t)
    {
        return LongTupleFunctions.reduce(
            t, Long.MIN_VALUE, Math::max);
    }

    /**
     * Compute the <i>element-wise</i> minimum of the the given input
     * tuples, and store the result in the given
     * result tuple.
     *
     * @param t0 The first input tuple
     * @param t1 The second input tuple
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple min(
        LongTuple t0, LongTuple t1, MutableLongTuple result)
    {
        return LongTupleFunctions.apply(
            t0, t1, Math::min, result);
    }

    /**
     * Compute the <i>element-wise</i> maximum of the the given input
     * tuples, and store the result in the given
     * result tuple.
     *
     * @param t0 The first input tuple
     * @param t1 The second input tuple
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple max(
        LongTuple t0, LongTuple t1, MutableLongTuple result)
    {
        return LongTupleFunctions.apply(
            t0, t1, Math::max, result);
    }


    /**
     * Compares two tuples lexicographically, starting with
     * the elements of the lowest index.
     *
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return -1 if the first tuple is lexicographically
     * smaller than the second, +1 if it is larger, and
     * 0 if they are equal.
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static int compareLexicographically(LongTuple t0, LongTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        for (int i=0; i<t0.getSize(); i++)
        {
            if (t0.get(i) < t1.get(i))
            {
                return -1;
            }
            else if (t0.get(i) > t1.get(i))
            {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Compares two tuples colexicographically, starting with
     * the elements of the highest index.
     *
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return -1 if the first tuple is colexicographically
     * smaller than the second, +1 if it is larger, and
     * 0 if they are equal.
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static int compareColexicographically(LongTuple t0, LongTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        for (int i=t0.getSize()-1; i>=0; i--)
        {
            if (t0.get(i) < t1.get(i))
            {
                return -1;
            }
            else if (t0.get(i) > t1.get(i))
            {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Returns a comparator that compares tuples based on the specified 
     * {@link Order}. If the given order is <code>null</code>, then 
     * <code>null</code> will be returned.
     * 
     * @param order The {@link Order}
     * @return The comparator.
     */
    public static Comparator<LongTuple> comparator(Order order)
    {
        if (order == Order.LEXICOGRAPHICAL)
        {
            return (t0, t1) -> compareLexicographically(t0, t1);
        }
        if (order == Order.COLEXICOGRAPHICAL)
        {
            return (t0, t1) -> compareColexicographically(t0, t1);
        }
        return null;
    }


    /**
     * Returns whether one tuple is <i>element-wise</i>
     * greater than the other.
     *
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return Whether each element of the first tuple
     * is greater than the corresponding element of the
     * second tuple.
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static boolean areElementsGreaterThan(
        LongTuple t0, LongTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        for (int i=0; i<t0.getSize(); i++)
        {
            if (t0.get(i) <= t1.get(i))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether one tuple is <i>element-wise</i>
     * greater than or equal to the other.
     *
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return Whether each element of the first tuple
     * is greater than or equal to the corresponding element of the
     * second tuple.
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    public static boolean areElementsGreaterThanOrEqual(
        LongTuple t0, LongTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        for (int i=0; i<t0.getSize(); i++)
        {
            if (t0.get(i) < t1.get(i))
            {
                return false;
            }
        }
        return true;
    }


    /**
     * Returns whether one tuple is <i>element-wise</i>
     * less than the other
     *
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return Whether each element of the first tuple
     * is smaller than the corresponding element of the
     * second tuple.
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static boolean areElementsLessThan(
        LongTuple t0, LongTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        for (int i=0; i<t0.getSize(); i++)
        {
            if (t0.get(i) >= t1.get(i))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether one tuple is <i>element-wise</i>
     * less than or equal to the other
     *
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return Whether each element of the first tuple
     * is less than or equal to the corresponding element of the
     * second tuple.
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    public static boolean areElementsLessThanOrEqual(
        LongTuple t0, LongTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        for (int i=0; i<t0.getSize(); i++)
        {
            if (t0.get(i) > t1.get(i))
            {
                return false;
            }
        }
        return true;
    }


    /**
     * Returns whether all elements of the given tuple 
     * are greater than the given value.
     *
     * @param t The tuple
     * @param value The value to compare to
     * @return Whether each element of the tuple
     * is greater than the given value
     */
    public static boolean areElementsGreaterThan(
        LongTuple t, long value)
    {
        for (int i=0; i<t.getSize(); i++)
        {
            if (t.get(i) <= value)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether all elements of the given tuple 
     * are greater than or equal to the given value.
     *
     * @param t The tuple
     * @param value The value to compare to
     * @return Whether each element of the tuple
     * is greater than or equal to the given value
     */
    public static boolean areElementsGreaterThanOrEqual(
        LongTuple t, long value)
    {
        for (int i=0; i<t.getSize(); i++)
        {
            if (t.get(i) < value)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether all elements of the given tuple 
     * are less than the given value.
     *
     * @param t The tuple
     * @param value The value to compare to
     * @return Whether each element of the tuple
     * is less than the given value
     */
    public static boolean areElementsLessThan(
        LongTuple t, long value)
    {
        for (int i=0; i<t.getSize(); i++)
        {
            if (t.get(i) >= value)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether all elements of the given tuple 
     * are less than or equal to the given value.
     *
     * @param t The tuple
     * @param value The value to compare to
     * @return Whether each element of the tuple
     * is less than or equal to the given value
     */
    public static boolean areElementsLessThanOrEqual(
        LongTuple t, long value)
    {
        for (int i=0; i<t.getSize(); i++)
        {
            if (t.get(i) > value)
            {
                return false;
            }
        }
        return true;
    }







    /**
     * If the given result is <code>null</code>, then a new tuple with
     * the same size as the given tuple will be created and
     * returned. Otherwise, it will be checked whether the given
     * tuples have equal sizes.
     * 
     * @param t The reference tuple
     * @param result The result tuple
     * @return The result tuple
     * @throws NullPointerException If the given reference tuple is 
     * <code>null</code>
     * @throws IllegalArgumentException If the given result tuple is
     * not <code>null</code> and does not have the same 
     * {@link Tuple#getSize() size} as the given tuple 
     */
    static MutableLongTuple validate(
        LongTuple t, MutableLongTuple result)
    {
        if (result == null)
        {
            result = create(t.getSize());
        }
        else
        {
            Utils.checkForEqualSize(t, result);
        }
        return result;
    }

    /**
     * Creates a string representation of the given tuple
     * 
     * @param tuple The tuple
     * @return The string
     */
    static String toString(LongTuple tuple)
    {
        if (tuple == null)
        {
            return "null";
        }
        return toString(tuple, tuple.getSize(), tuple.getSize());
    }

    /**
     * Creates a string representation of the given tuple
     * 
     * @param tuple The tuple
     * @param locale The locale. If this is <code>null</code>, then a 
     * canonical string representation of the elements will be used.
     * @param format The format. If this is <code>null</code>, then a 
     * canonical string representation of the elements will be used.
     * @return The string
     */
    static String toString(LongTuple tuple, Locale locale, String format)
    {
        if (tuple == null)
        {
            return "null";
        }
        return toString(tuple, locale, format, 
            tuple.getSize(), tuple.getSize());
    }

    /**
     * Returns a string representation of the given tuple using 
     * {@link #toString(LongTuple, Locale, String, int, int)} with
     * the default locale and format.
     * 
     * @param tuple The tuple
     * @param maxNumLeadingElements The maximum number of leading elements 
     * @param maxNumTrailingElements The maximum number of trailing elements
     * @return The string
     */
    public static String toString(LongTuple tuple, 
        int maxNumLeadingElements, int maxNumTrailingElements)
    {
        return toString(tuple, null, null, 
            maxNumLeadingElements, maxNumTrailingElements);
    }

    /**
     * Returns a string representation of the given tuple. If the given
     * tuple has a {@link Tuple#getSize() size} that is larger than
     * the sum of the given numbers of leading and trailing elements, then 
     * the string contents will be abbreviated with an ellipsis: "...".<br>
     * <br>
     * Examples for a tuple with size 6: <br>
     * <code>toString(t, 3, 1) : (0, 0, 0, ..., 0)</code><br>
     * <code>toString(t, 2, 0) : (0, 0, ...)</code><br>
     * <code>toString(t, 0, 2) : (..., 0, 0)</code><br>
     * <code>toString(t, 9, 9) : (0, 0, 0, 0, 0, 0)</code><br>
     * <br>
     * The given numbers of elements will be clamped to be nonnegative.<br>
     * <br>
     * @param tuple The tuple
     * @param locale The locale. If this is <code>null</code>, then a 
     * canonical string representation of the elements will be used.
     * @param format The format. If this is <code>null</code>, then a 
     * canonical string representation of the elements will be used.
     * @param maxNumLeadingElements The maximum number of leading elements 
     * @param maxNumTrailingElements The maximum number of trailing elements
     * @return The string
     */
    public static String toString(LongTuple tuple, 
        Locale locale, String format,
        int maxNumLeadingElements, int maxNumTrailingElements)
    {
        if (tuple == null)
        {
            return "null";
        }
        maxNumLeadingElements = Math.max(0, maxNumLeadingElements);
        maxNumTrailingElements = Math.max(0, maxNumTrailingElements);
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int n = tuple.getSize();
        if (n <= maxNumLeadingElements+maxNumTrailingElements)
        {
            appendRange(sb, tuple, locale, format, 0, n);
        }
        else
        {
            appendRange(sb, tuple, locale, format, 0, maxNumLeadingElements);
            if (maxNumLeadingElements > 0)
            {
                sb.append(", ");
            }
            sb.append("...");
            if (maxNumTrailingElements > 0)
            {
                sb.append(", ");
            }
            appendRange(sb, tuple, locale, format, n-maxNumTrailingElements, n);
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Append the string representation of the specified range of the given
     * tuple to the given string builder
     *  
     * @param sb The string builder
     * @param tuple The tuple
     * @param locale The locale. If this is <code>null</code>, then a 
     * canonical string representation of the elements will be used.
     * @param format The format. If this is <code>null</code>, then a 
     * canonical string representation of the elements will be used.
     * @param min The minimum index to append, inclusive
     * @param max The maximum index to append, exclusive
     */
    private static void appendRange(
        StringBuilder sb, LongTuple tuple, 
        Locale locale, String format, int min, int max)
    {
        for (int i=min; i<max; i++)
        {
            if (i > min)
            {
                sb.append(", ");
            }
            if (locale != null && format != null)
            {
                sb.append(String.format(locale, format, tuple.get(i)));
            }
            else
            {
                sb.append(String.valueOf(tuple.get(i)));
            }
        }
    }


    /**
     * Returns a hash code for the given tuple
     * 
     * @param tuple The tuple
     * @return The hash code
     */
    static int hashCode(LongTuple tuple)
    {
        if (tuple == null)
        {
            return 0;
        }

        int result = 0;
        for (int i = 0; i < tuple.getSize(); i++)
        {
            long value = tuple.get(i);
            result += Long.hashCode(value);
        }
        return result;
    }

    /**
     * Returns whether the given tuple equals the given object
     * 
     * @param tuple The tuple
     * @param object The object
     * @return Whether the tuple equals the given object
     */
    static boolean equals(LongTuple tuple, Object object)
    {
        if (tuple == object)
        {
            return true;
        }
        if (object == null)
        {
            return false;
        }
        if (!(object instanceof LongTuple))
        {
            return false;
        }
        LongTuple other = (LongTuple)object;
        if (other.getSize() != tuple.getSize())
        {
            return false;
        }
        for (int i=0; i<tuple.getSize(); i++)
        {
            if (tuple.get(i) != other.get(i))
            {
                return false;
            }
        }
        return true;
    }


    /**
     * Returns whether the given tuples are equal up to the given epsilon.
     * That is, returns whether they have the same {@link Tuple#getSize() size},
     * and the absolute difference between two corresponding elements is less 
     * than or equal to the given epsilon.
     * 
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @param epsilon The epsilon
     * @return Whether the tuples are epsilon-equal
     */
    static boolean epsilonEquals(
        LongTuple t0, LongTuple t1, long epsilon)
    {
        if (t0 == t1)
        {
            return true;
        }
        int n0 = t0.getSize();
        int n1 = t1.getSize();
        if (n0 != n1)
        {
            return false;
        }
        for (int i=0; i<n0; i++)
        {
            long v0 = t0.get(i);
            long v1 = t1.get(i);
            if (Math.abs(v0-v1) > epsilon)
            {
                return false;
            }
        }
        return true;
    }






    //=========================================================================
    // Start of custom methods


    /**
     * Convert the given {@link LongTuple} into a {@link DoubleTuple}
     *
     * @param longTuple The {@link LongTuple}
     * @return The {@link MutableDoubleTuple}
     */
    public static MutableDoubleTuple toDoubleTuple(LongTuple longTuple)
    {
        int d = longTuple.getSize();
        MutableDoubleTuple doubleTuple = DoubleTuples.create(d);
        for (int i=0; i<d; i++)
        {
            doubleTuple.set(i, longTuple.get(i));
        }
        return doubleTuple;
    }

    /**
     * Creates a new {@link MutableLongTuple} with the given value.
     *
     * @param x The x coordinate
     * @return The new tuple
     */
    public static MutableLongTuple of(long x)
    {
        return new DefaultLongTuple(new long[]{ x });
    }

    /**
     * Creates a new {@link MutableLongTuple} with the given values.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The new tuple
     */
    public static MutableLongTuple of(int x, int y)
    {
        return new DefaultLongTuple(new long[]{ x, y });
    }

    /**
     * Creates a new {@link MutableLongTuple} with the given values.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return The new tuple
     */
    public static MutableLongTuple of(long x, long y, long z)
    {
        return new DefaultLongTuple(new long[]{ x, y, z });
    }

    /**
     * Creates a new {@link MutableLongTuple} with the given values.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @param w The w coordinate
     * @return The new tuple
     */
    public static MutableLongTuple of(long x, long y, long z, long w)
    {
        return new DefaultLongTuple(new long[]{ x, y, z, w });
    }

    /**
     * Lexicographically increment the given tuple in the given range, and
     * store the result in the given result tuple. It is assumed that the
     * elements of the given tuple are
     * {@link #areElementsGreaterThanOrEqual(LongTuple, LongTuple) greater than
     * or equal to} the values in the given minimum tuple.<br>
     * <br>
     * Note that in contrast to most other methods in this class, the
     * given result tuple may <b>not</b> be <code>null</code> (but it
     * may be identical to the input tuple).
     *
     * @param t The input tuple
     * @param min The minimum values
     * @param max The maximum values
     * @param result The result tuple
     * @return Whether the tuple could be incremented without causing an
     * overflow
     */
    public static boolean incrementLexicographically(
        LongTuple t, LongTuple min, LongTuple max, MutableLongTuple result)
    {
        Utils.checkForEqualSize(t, min);
        Utils.checkForEqualSize(t, max);
        Utils.checkForEqualSize(t, result);
        if (result != t)
        {
            result.set(t);
        }
        return incrementLexicographically(
            result, min, max, result.getSize()-1);
    }

    /**
     * Recursively increment the given tuple lexicographically, starting at
     * the given index.
     *
     * @param current The tuple to increment
     * @param min The minimum values
     * @param max The maximum values
     * @param index The index
     * @return Whether the tuple could be incremented
     */
    private static boolean incrementLexicographically(
        MutableLongTuple current, LongTuple min, LongTuple max, int index)
    {
        if (index == -1)
        {
            return false;
        }
        long oldValue = current.get(index);
        long newValue = oldValue + 1;
        current.set(index, newValue);
        if (newValue >= max.get(index))
        {
            current.set(index, min.get(index));
            return incrementLexicographically(current, min, max, index-1);
        }
        return true;
    }


    /**
     * Colexicographically increment the given tuple in the given range, and
     * store the result in the given result tuple. It is assumed that the
     * elements of the given tuple are
     * {@link #areElementsGreaterThanOrEqual(LongTuple, LongTuple) greater than
     * or equal to} the values in the given minimum tuple.<br>
     * <br>
     * Note that in contrast to most other methods in this class, the
     * given result tuple may <b>not</b> be <code>null</code> (but it
     * may be identical to the input tuple).
     *
     * @param t The input tuple
     * @param min The minimum values
     * @param max The maximum values
     * @param result The result tuple
     * @return Whether the tuple could be incremented without causing an
     * overflow
     */
    public static boolean incrementColexicographically(
        LongTuple t, LongTuple min, LongTuple max, MutableLongTuple result)
    {
        Utils.checkForEqualSize(t, min);
        Utils.checkForEqualSize(t, max);
        Utils.checkForEqualSize(t, result);
        if (result != t)
        {
            result.set(t);
        }
        return incrementColexicographically(
            result, min, max, 0);
    }

    /**
     * Recursively increment the given tuple colexicographically, starting at
     * the given index.
     *
     * @param current The tuple to increment
     * @param min The minimum values
     * @param max The maximum values
     * @param index The index.
     * @return Whether the tuple could be incremented
     */
    static boolean incrementColexicographically(MutableLongTuple current,
        LongTuple min, LongTuple max, int index)
    {
        if (index == max.getSize())
        {
            return false;
        }
        long oldValue = current.get(index);
        long newValue = oldValue + 1;
        current.set(index, newValue);
        if (newValue >= max.get(index))
        {
            current.set(index, min.get(index));
            return incrementColexicographically(current, min, max, index+1);
        }
        return true;
    }

    // End   of custom methods
    //=========================================================================

    /**
     * Private constructor to prevent instantiation
     */
    private LongTuples()
    {
        // Private constructor to prevent instantiation
    }




}
