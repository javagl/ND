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
package de.javagl.nd.tuples.d;

import java.nio.DoubleBuffer;
import java.util.AbstractList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;

import java.util.Random;

/**
 * Utility methods related to {@link DoubleTuple}s.<br>
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
public class DoubleTuples
{
    /**
     * Size of the pool for frequently used zero-tuples
     */
    private static final int ZERO_POOL_SIZE = 5;

    /**
     * The pool of frequently used zero-tuples.
     */
    private static final DoubleTuple ZEROS[] = new DoubleTuple[ZERO_POOL_SIZE];
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
    private static DoubleTuple createZero(final int size)
    {
        return constant(size, 0.0);
    }

    /**
     * Returns a tuple with the specified size, containing
     * all zeros.
     *
     * @param size The size
     * @return The zero tuple.
     * @throws IllegalArgumentException If the size is smaller than 0
     */
    public static DoubleTuple zero(int size)
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
    public static DoubleTuple constant(int size, double value)
    {
        return new ConstantDoubleTuple(size, value);
    }

    /**
     * Creates a new tuple with the given size.
     *
     * @param size The size.
     * @return The new tuple.
     * @throws IllegalArgumentException If the size is smaller than 0
     */
    public static MutableDoubleTuple create(int size)
    {
        return new DefaultDoubleTuple(size);
    }

    /**
     * Creates a new tuple which is a copy of 
     * the given one.
     *
     * @param other The other tuple.
     * @return The new tuple.
     * @throws NullPointerException If the other tuple is <code>null</code>
     */
    public static MutableDoubleTuple copy(DoubleTuple other)
    {
        return new DefaultDoubleTuple(other);
    }

    /**
     * Creates a new tuple with the given values.
     * The given array will be cloned, so that changes in the given array
     * will not be visible in the returned tuple. In order to create
     * a <i>view</i> on a given array, {@link #wrap(double...)} may be
     * used. 
     *
     * @param values The values
     * @return The new tuple
     * @throws NullPointerException If the given array is <code>null</code>
     */
    public static MutableDoubleTuple of(double ... values)
    {
        return new DefaultDoubleTuple(values.clone());
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
    public static MutableDoubleTuple wrap(double ... values)
    {
        return new DefaultDoubleTuple(values);
    }

    /**
     * Returns a new tuple that is a <i>view</i> on the given buffer.
     * 
     * @param buffer The buffer for the tuple
     * @throws NullPointerException If the given buffer is <code>null</code>
     * @return The new tuple
     */
    public static MutableDoubleTuple wrap(DoubleBuffer buffer)
    {
        return new BufferDoubleTuple(buffer);
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
    public static MutableDoubleTuple wrap(
        double[] data, int offset, int size)
    {
        return new ArrayDoubleTuple(data, offset, size);
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
    static DoubleTuple createSubTuple(
        DoubleTuple parent, int fromIndex, int toIndex)
    {
        return new SubDoubleTuple(parent, fromIndex, toIndex);
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
    static MutableDoubleTuple createSubTuple(
        MutableDoubleTuple parent, int fromIndex, int toIndex)
    {
        return new MutableSubDoubleTuple(parent, fromIndex, toIndex);
    }


    /**
     * Creates a new array from the contents of the given tuple
     * 
     * @param t The tuple
     * @return The array
     */
    public static double[] toArray(DoubleTuple t)
    {
        int d = t.getSize();
        double result[] = new double[d];
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
    public static List<Double> asList(final DoubleTuple t)
    {
        if (t == null)
        {
            throw new NullPointerException("The tuple may not be null");
        }
        return new AbstractList<Double>()
        {
            @Override
            public Double get(int index)
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
    public static List<Double> asList(final MutableDoubleTuple t)
    {
        if (t == null)
        {
            throw new NullPointerException("The tuple may not be null");
        }
        return new AbstractList<Double>()
        {
            @Override
            public Double get(int index)
            {
                return t.get(index);
            }

            @Override
            public int size()
            {
                return t.getSize();
            }

            @Override
            public Double set(int index, Double element)
            {
                double oldValue = t.get(index);
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
    public static void set(MutableDoubleTuple t, double v)
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
     * {@link #reversed(DoubleTuple)} method may be used.   
     * 
     * @param t The input tuple
     * @param result The result tuple
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple reverse(
        DoubleTuple t, MutableDoubleTuple result)
    {
        result = validate(t, result);
        if (t == result)
        {
            int n = t.getSize();
            int nh = n / 2;
            for (int i = 0; i < nh; i++)
            {
                double temp = result.get(i);
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
     * {@link #reverse(DoubleTuple, MutableDoubleTuple)} method may 
     * be used.
     * 
     * @param t The tuple
     * @return The reversed view on the tuple
     * @throws NullPointerException If the given tuple is <code>null</code>
     */
    public static DoubleTuple reversed(DoubleTuple t)
    {
        Objects.requireNonNull(t, "The input tuple is null");
        return new AbstractDoubleTuple()
        {
            @Override
            public int getSize()
            {
                return t.getSize();
            }

            @Override
            public double get(int index)
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
     * {@link #reverse(DoubleTuple, MutableDoubleTuple)} method may 
     * be used.
     * 
     * @param t The tuple
     * @return The reversed view on the tuple
     * @throws NullPointerException If the given tuple is <code>null</code>
     */
    public static MutableDoubleTuple reversed(MutableDoubleTuple t)
    {
        Objects.requireNonNull(t, "The input tuple is null");
        return new AbstractMutableDoubleTuple()
        {
            @Override
            public int getSize()
            {
                return t.getSize();
            }

            @Override
            public double get(int index)
            {
                return t.get(t.getSize() - 1 - index);
            }

            @Override
            public void set(int index, double value)
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
    public static MutableDoubleTuple insertElementAt(
        DoubleTuple t, int index, double value, 
        MutableDoubleTuple result)
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
            result = DoubleTuples.create(t.getSize() + 1);
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
    public static MutableDoubleTuple removeElementAt(
        DoubleTuple t, int index, MutableDoubleTuple result)
    {
        Utils.checkForValidIndex(index, t.getSize());
        if (result == null)
        {
            result = DoubleTuples.create(t.getSize() - 1);
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
    public static MutableDoubleTuple clamp(
        DoubleTuple t, double min, double max, MutableDoubleTuple result)
    {
        result = validate(t, result);
        for (int i=0; i<result.getSize(); i++)
        {
            double v = t.get(i);
            double r = Math.min(max, Math.max(min, v));
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
    public static MutableDoubleTuple clamp(
        DoubleTuple t, DoubleTuple min, DoubleTuple max, 
        MutableDoubleTuple result)
    {
        Utils.checkForEqualSize(min, max);
        result = validate(t, result);
        for (int i=0; i<result.getSize(); i++)
        {
            double v = t.get(i);
            double minV = min.get(i);
            double maxV = max.get(i);
            double r = Math.min(maxV, Math.max(minV, v));
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
    public static MutableDoubleTuple negate(
        DoubleTuple t0, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
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
    public static MutableDoubleTuple add(
        DoubleTuple t0, DoubleTuple t1, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
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
    public static MutableDoubleTuple subtract(
        DoubleTuple t0, DoubleTuple t1, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
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
    public static MutableDoubleTuple multiply(
        DoubleTuple t0, DoubleTuple t1, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
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
    public static MutableDoubleTuple divide(
        DoubleTuple t0, DoubleTuple t1, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
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
    public static MutableDoubleTuple add(
        DoubleTuple t0, double value, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
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
    public static MutableDoubleTuple subtract(
        DoubleTuple t0, double value, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
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
    public static MutableDoubleTuple multiply(
        DoubleTuple t0, double factor, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
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
    public static MutableDoubleTuple addScaled(
        DoubleTuple t0, double factor, DoubleTuple t1, 
        MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
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
    public static double dot(DoubleTuple t0, DoubleTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        double result = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            result += t0.get(i) * t1.get(i);
        }
        return result;
    }



    /**
     * Computes the minimum value that occurs in the given tuple,
     * or <code>Double.POSITIVE_INFINITY</code> if the given tuple has a
     * size of 0.
     *
     * @param t The input tuple
     * @return The minimum value
     */
    public static double min(DoubleTuple t)
    {
        return DoubleTupleFunctions.reduce(
            t, Double.POSITIVE_INFINITY, Math::min);
    }

    /**
     * Computes the maximum value that occurs in the given tuple,
     * or <code>Double.NEGATIVE_INFINITY</code> if the given tuple has a
     * size of 0.
     *
     * @param t The input tuple
     * @return The maximum value
     */
    public static double max(DoubleTuple t)
    {
        return DoubleTupleFunctions.reduce(
            t, Double.NEGATIVE_INFINITY, Math::max);
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
    public static MutableDoubleTuple min(
        DoubleTuple t0, DoubleTuple t1, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
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
    public static MutableDoubleTuple max(
        DoubleTuple t0, DoubleTuple t1, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
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
    public static int compareLexicographically(DoubleTuple t0, DoubleTuple t1)
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
    public static int compareColexicographically(DoubleTuple t0, DoubleTuple t1)
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
    public static Comparator<DoubleTuple> comparator(Order order)
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
        DoubleTuple t0, DoubleTuple t1)
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
        DoubleTuple t0, DoubleTuple t1)
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
        DoubleTuple t0, DoubleTuple t1)
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
        DoubleTuple t0, DoubleTuple t1)
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
        DoubleTuple t, double value)
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
        DoubleTuple t, double value)
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
        DoubleTuple t, double value)
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
        DoubleTuple t, double value)
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
    static MutableDoubleTuple validate(
        DoubleTuple t, MutableDoubleTuple result)
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
    static String toString(DoubleTuple tuple)
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
    static String toString(DoubleTuple tuple, Locale locale, String format)
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
     * {@link #toString(DoubleTuple, Locale, String, int, int)} with
     * the default locale and format.
     * 
     * @param tuple The tuple
     * @param maxNumLeadingElements The maximum number of leading elements 
     * @param maxNumTrailingElements The maximum number of trailing elements
     * @return The string
     */
    public static String toString(DoubleTuple tuple, 
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
    public static String toString(DoubleTuple tuple, 
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
        StringBuilder sb, DoubleTuple tuple, 
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
    static int hashCode(DoubleTuple tuple)
    {
        if (tuple == null)
        {
            return 0;
        }

        int result = 0;
        for (int i = 0; i < tuple.getSize(); i++)
        {
            double value = tuple.get(i);
            result += Double.hashCode(value);
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
    static boolean equals(DoubleTuple tuple, Object object)
    {
        if (tuple == object)
        {
            return true;
        }
        if (object == null)
        {
            return false;
        }
        if (!(object instanceof DoubleTuple))
        {
            return false;
        }
        DoubleTuple other = (DoubleTuple)object;
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
        DoubleTuple t0, DoubleTuple t1, double epsilon)
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
            double v0 = t0.get(i);
            double v1 = t1.get(i);
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
     * Fill the given tuple with random values in
     * the specified range, using the given random number generator
     *
     * @param t The tuple to fill
     * @param min The minimum value, inclusive
     * @param max The maximum value, exclusive
     * @param random The random number generator
     */
    public static void randomize(
        MutableDoubleTuple t, double min, double max, Random random)
    {
        double delta = max - min;
        for (int i=0; i<t.getSize(); i++)
        {
            t.set(i, min + random.nextDouble() * delta);
        }
    }

    /**
     * Fill the given tuple with random values in
     * [0,1), using the given random number generator
     *
     * @param t The tuple to fill
     * @param random The random number generator
     */
    public static void randomize(MutableDoubleTuple t, Random random)
    {
        randomize(t, 0.0, 1.0, random);
    }

    /**
     * Creates a tuple with the given size that
     * is filled with random values in [0,1)
     *
     * @param size The size
     * @param random The random number generator
     * @return The new tuple
     */
    public static MutableDoubleTuple createRandom(int size, Random random)
    {
       MutableDoubleTuple t = create(size);
       randomize(t, random);
       return t;
    }

    /**
     * Creates a tuple with the given size that
     * is filled with random values in the given range.
     *
     * @param size The size
     * @param min The minimum value, inclusive
     * @param max The maximum value, exclusive
     * @param random The random number generator
     * @return The new tuple
     */
    public static MutableDoubleTuple createRandom(
        int size, double min, double max, Random random)
    {
       MutableDoubleTuple t = create(size);
       randomize(t, min, max, random);
       return t;
    }



    /**
     * Randomize the given tuple with a gaussian
     * distribution with a mean of 0.0 and standard deviation of 1.0
     *
     * @param t The tuple to fill
     * @param random The random number generator
     */
    public static void randomizeGaussian(MutableDoubleTuple t, Random random)
    {
        for (int i=0; i<t.getSize(); i++)
        {
            double value = random.nextGaussian();
            t.set(i, value);
        }
    }

    /**
     * Creates a tuple with the given size
     * that was filled with values from a gaussian
     * distribution with mean 0.0 and standard deviation 1.0
     *
     * @param size The size
     * @param random The random number generator
     * @return The new tuple
     */
    public static MutableDoubleTuple createRandomGaussian(
        int size, Random random)
    {
       MutableDoubleTuple t = create(size);
       randomizeGaussian(t, random);
       return t;
    }


    /**
     * Normalize the given tuple and write the result into
     * the given result tuple. That is, the given tuple
     * is divided by its {@link #computeL2(DoubleTuple) L2 norm}.
     *
     * @param t The input tuple
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple normalize(
        DoubleTuple t, MutableDoubleTuple result)
    {
        result = validate(t, result);
        double inv = 1.0 / computeL2(t);
        return DoubleTupleFunctions.apply(
            t, (a)->(a * inv), result);
    }

    /**
     * Computes the L2 norm (euclidean length) of the given tuple
     *
     * @param t The tuple
     * @return The L2 norm
     */
    public static double computeL2(DoubleTuple t)
    {
        double sum = 0;
        for (int i=0; i<t.getSize(); i++)
        {
            double ti = t.get(i);
            sum += ti * ti;
        }
        return Math.sqrt(sum);
    }

    /**
     * Normalize the elements of the given tuple, so that its minimum and
     * maximum elements match the given minimum and maximum values.
     *
     * @param t The input tuple
     * @param min The minimum value
     * @param max The maximum value
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple normalizeElements(
        DoubleTuple t, double min, double max, MutableDoubleTuple result)
    {
        return rescaleElements(t, min(t), max(t), min, max, result);
    }

    /**
     * Normalize the elements of the given tuple, so that each element
     * will be linearly rescaled to the interval defined by the corresponding
     * elements of the given minimum and maximum tuple.
     * Each element that is equal to the corresponding minimum element will
     * be 0.0 in the resulting tuple.
     * Each element that is equal to the corresponding maximum element will
     * be 1.0 in the resulting tuple.
     * Other values will be interpolated accordingly.
     *
     * @param t The input tuple
     * @param min The minimum value
     * @param max The maximum value
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple normalizeElements(
        DoubleTuple t, DoubleTuple min, DoubleTuple max,
        MutableDoubleTuple result)
    {
        result = validate(t, result);
        for (int i=0; i<result.getSize(); i++)
        {
            double value = t.get(i);
            double minValue = min.get(i);
            double maxValue = max.get(i);
            double alpha = (value - minValue) / (maxValue - minValue);
            double newValue = alpha;
            result.set(i, newValue);
        }
        return result;
    }


    /**
     * Rescale the elements of the given tuple, so that the specified
     * old range is mapped to the specified new range.
     *
     * @param t The input tuple
     * @param oldMin The old minimum value
     * @param oldMax The old maximum value
     * @param newMin The new minimum value
     * @param newMax The new maximum value
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple rescaleElements(
        DoubleTuple t, double oldMin, double oldMax,
        double newMin, double newMax, MutableDoubleTuple result)
    {
        double invDelta = 1.0 / (oldMax - oldMin);
        double newRange = newMax - newMin;
        double scaling = invDelta * newRange;
        return DoubleTupleFunctions.apply(
            t, (a)->(newMin + (a - oldMin) * scaling), result);
    }



    /**
     * Computes <code>t0+alpha*(t1-t0)</code>, and stores the result in
     * the given result tuple.
     *
     * @param t0 The first input tuple
     * @param t1 The second input tuple
     * @param alpha The interpolation value
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple interpolate(
        DoubleTuple t0, DoubleTuple t1, double alpha,
        MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
            t0, t1, (a,b)->(a+alpha*(b-a)), result);
    }

    /**
     * Returns the geometric mean of the given tuple
     *
     * @param t The input tuple
     * @return The mean
     */
    public static double geometricMean(DoubleTuple t)
    {
        double product = DoubleTupleFunctions.reduce(t, 1.0, (a,b) -> (a*b));
        return Math.pow(product, 1.0 / t.getSize());
    }

    /**
     * Returns the harmonic mean of the given tuple
     *
     * @param t The input tuple
     * @return The mean
     */
    public static double harmonicMean(DoubleTuple t)
    {
        double s =
            DoubleTupleFunctions.reduce(t, 0.0, (a, b) -> (a + (1.0 / b)));
        return t.getSize() / s;
    }

    /**
     * Returns the standard deviation of the given variances (that is,
     * a tuple containing the square roots of the values in the given
     * tuple).
     *
     * @param variance The input tuple
     * @param result The result tuple
     * @return The standard deviation
     * @throws IllegalArgumentException If the given tuples do not have the
     * same size
     */
    public static MutableDoubleTuple standardDeviationFromVariance(
        DoubleTuple variance, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
            variance, Math::sqrt, result);
    }


    /**
     * Standardize the given tuple. This means that the mean of the elements
     * is subtracted from them, and they are divided by the standard
     * deviation.
     *
     * @param t The tuple
     * @param result The result tuple
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple standardize(
        DoubleTuple t, MutableDoubleTuple result)
    {
        result = DoubleTuples.validate(t, result);
        double mean = arithmeticMean(t);
        double standardDeviation = standardDeviationFromMean(t, mean);
        double invStandardDeviation = 1.0 / standardDeviation;
        return DoubleTupleFunctions.apply(
            t, (a) -> ((a - mean) * invStandardDeviation), result);
    }

    /**
     * Returns the arithmetic mean of the given tuple
     *
     * @param t The input tuple
     * @return The mean
     */
    public static double arithmeticMean(DoubleTuple t)
    {
        double sum = DoubleTupleFunctions.reduce(t, 0.0, (a,b) -> (a+b));
        return sum / t.getSize();
    }

    /**
     * Returns the bias-corrected sample variance of the given tuple. The
     * method will compute the arithmetic mean, and then compute the actual
     * result with the {@link #variance(DoubleTuple, double)} method.
     *
     * @param t The input tuple
     * @return The variance
     *
     * @see #variance(DoubleTuple, double)
     */
    public static double variance(DoubleTuple t)
    {
        double mean = arithmeticMean(t);
        return variance(t, mean);
    }

    /**
     * Returns the bias-corrected sample variance of the given tuple.
     *
     * @param t The input tuple
     * @param mean The mean, which may have been computed before with
     * {@link #arithmeticMean(DoubleTuple)}
     * @return The variance
     */
    public static double variance(DoubleTuple t, double mean)
    {
        int d = t.getSize();
        double variance = 0;
        for (int i=0; i<d; i++)
        {
            double difference = t.get(i) - mean;
            variance += difference * difference;
        }
        return variance / (d - 1);
    }


    /**
     * Returns the standard deviation of the given tuple.
     * The method will compute the arithmetic mean, and then compute the
     * actual result with the
     * {@link #standardDeviationFromMean(DoubleTuple, double)}
     * method.
     *
     * @param t The input tuple
     * @return The standard deviation
     */
    public static double standardDeviation(DoubleTuple t)
    {
        double mean = arithmeticMean(t);
        return standardDeviationFromMean(t, mean);
    }

    /**
     * Returns the standard deviation of the given tuple.
     * The method will compute {@link #variance(DoubleTuple)}, return
     * the square root of this value.
     *
     * @param t The input tuple
     * @param mean The mean, which may have been computed before with
     * {@link #arithmeticMean(DoubleTuple)}
     * @return The standard deviation
     */
    public static double standardDeviationFromMean(
        DoubleTuple t, double mean)
    {
        double variance = variance(t, mean);
        return Math.sqrt(variance);
    }

    /**
     * Returns whether the given tuple contains an element that
     * is Not A Number
     *
     * @param tuple The tuple
     * @return Whether the tuple contains a NaN element
     */
    public static boolean containsNaN(DoubleTuple tuple)
    {
        for (int i=0; i<tuple.getSize(); i++)
        {
            if (Double.isNaN(tuple.get(i)))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Replace all occurrences of "Not A Number" in the given tuple
     * with the given value, and store the result in the given 
     * result tuple
     * 
     * @param t The tuple
     * @param newValue The value that should replace the NaN value
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple replaceNaN(
        DoubleTuple t, double newValue, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
            t, d -> Double.isNaN(d) ? newValue : d, result);
    }
    


    // End   of custom methods
    //=========================================================================

    /**
     * Private constructor to prevent instantiation
     */
    private DoubleTuples()
    {
        // Private constructor to prevent instantiation
    }




}
