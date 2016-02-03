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
package de.javagl.nd.tuples.$type.letter$;

import java.nio.$type.uppercaseName$Buffer;
import java.util.AbstractList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;

$customImports$

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Utility methods related to {@link $type.uppercaseName$Tuple}s.<br>
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
public class $type.uppercaseName$Tuples
{
    /**
     * Size of the pool for frequently used zero-tuples
     */
    private static final int ZERO_POOL_SIZE = 5;

    /**
     * The pool of frequently used zero-tuples.
     */
    private static final $type.uppercaseName$Tuple ZEROS[] = new $type.uppercaseName$Tuple[ZERO_POOL_SIZE];
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
    private static $type.uppercaseName$Tuple createZero(final int size)
    {
        return constant(size, $type.zero$);
    }

    /**
     * Returns a tuple with the specified size, containing
     * all zeros.
     *
     * @param size The size
     * @return The zero tuple.
     * @throws IllegalArgumentException If the size is smaller than 0
     */
    public static $type.uppercaseName$Tuple zero(int size)
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
    public static $type.uppercaseName$Tuple constant(int size, $type.name$ value)
    {
        return new Constant$type.uppercaseName$Tuple(size, value);
    }

    /**
     * Creates a new tuple with the given size.
     *
     * @param size The size.
     * @return The new tuple.
     * @throws IllegalArgumentException If the size is smaller than 0
     */
    public static Mutable$type.uppercaseName$Tuple create(int size)
    {
        return new Default$type.uppercaseName$Tuple(size);
    }

    /**
     * Creates a new tuple which is a copy of 
     * the given one.
     *
     * @param other The other tuple.
     * @return The new tuple.
     * @throws NullPointerException If the other tuple is <code>null</code>
     */
    public static Mutable$type.uppercaseName$Tuple copy($type.uppercaseName$Tuple other)
    {
        return new Default$type.uppercaseName$Tuple(other);
    }

    /**
     * Creates a new tuple with the given values.
     * The given array will be cloned, so that changes in the given array
     * will not be visible in the returned tuple. In order to create
     * a <i>view</i> on a given array, {@link #wrap($type.name$...)} may be
     * used. 
     *
     * @param values The values
     * @return The new tuple
     * @throws NullPointerException If the given array is <code>null</code>
     */
    public static Mutable$type.uppercaseName$Tuple of($type.name$ ... values)
    {
        return new Default$type.uppercaseName$Tuple(values.clone());
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
    public static Mutable$type.uppercaseName$Tuple wrap($type.name$ ... values)
    {
        return new Default$type.uppercaseName$Tuple(values);
    }
    
    /**
     * Returns a new tuple that is a <i>view</i> on the given buffer.
     * 
     * @param buffer The buffer for the tuple
     * @throws NullPointerException If the given buffer is <code>null</code>
     * @return The new tuple
     */
    public static Mutable$type.uppercaseName$Tuple wrap($type.uppercaseName$Buffer buffer)
    {
        return new Buffer$type.uppercaseName$Tuple(buffer);
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
    public static Mutable$type.uppercaseName$Tuple wrap(
        $type.name$[] data, int offset, int size)
    {
        return new Array$type.uppercaseName$Tuple(data, offset, size);
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
    static $type.uppercaseName$Tuple createSubTuple(
        $type.uppercaseName$Tuple parent, int fromIndex, int toIndex)
    {
        return new Sub$type.uppercaseName$Tuple(parent, fromIndex, toIndex);
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
    static Mutable$type.uppercaseName$Tuple createSubTuple(
        Mutable$type.uppercaseName$Tuple parent, int fromIndex, int toIndex)
    {
        return new MutableSub$type.uppercaseName$Tuple(parent, fromIndex, toIndex);
    }


    /**
     * Creates a new array from the contents of the given tuple
     * 
     * @param t The tuple
     * @return The array
     */
    public static $type.name$[] toArray($type.uppercaseName$Tuple t)
    {
        int d = t.getSize();
        $type.name$ result[] = new $type.name$[d];
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
    public static List<$type.boxedName$> asList(final $type.uppercaseName$Tuple t)
    {
        if (t == null)
        {
            throw new NullPointerException("The tuple may not be null");
        }
        return new AbstractList<$type.boxedName$>()
        {
            @Override
            public $type.boxedName$ get(int index)
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
    public static List<$type.boxedName$> asList(final Mutable$type.uppercaseName$Tuple t)
    {
        if (t == null)
        {
            throw new NullPointerException("The tuple may not be null");
        }
        return new AbstractList<$type.boxedName$>()
        {
            @Override
            public $type.boxedName$ get(int index)
            {
                return t.get(index);
            }

            @Override
            public int size()
            {
                return t.getSize();
            }

            @Override
            public $type.boxedName$ set(int index, $type.boxedName$ element)
            {
                $type.name$ oldValue = t.get(index);
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
    public static void set(Mutable$type.uppercaseName$Tuple t, $type.name$ v)
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
     * {@link #reversed($type.uppercaseName$Tuple)} method may be used.   
     * 
     * @param t The input tuple
     * @param result The result tuple
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static Mutable$type.uppercaseName$Tuple reverse(
        $type.uppercaseName$Tuple t, Mutable$type.uppercaseName$Tuple result)
    {
        result = validate(t, result);
        if (t == result)
        {
            int n = t.getSize();
            int nh = n / 2;
            for (int i = 0; i < nh; i++)
            {
                $type.name$ temp = result.get(i);
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
     * {@link #reverse($type.uppercaseName$Tuple, Mutable$type.uppercaseName$Tuple)} method may 
     * be used.
     * 
     * @param t The tuple
     * @return The reversed view on the tuple
     * @throws NullPointerException If the given tuple is <code>null</code>
     */
    public static $type.uppercaseName$Tuple reversed($type.uppercaseName$Tuple t)
    {
        Objects.requireNonNull(t, "The input tuple is null");
        return new Abstract$type.uppercaseName$Tuple()
        {
            @Override
            public int getSize()
            {
                return t.getSize();
            }
            
            @Override
            public $type.name$ get(int index)
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
     * {@link #reverse($type.uppercaseName$Tuple, Mutable$type.uppercaseName$Tuple)} method may 
     * be used.
     * 
     * @param t The tuple
     * @return The reversed view on the tuple
     * @throws NullPointerException If the given tuple is <code>null</code>
     */
    public static Mutable$type.uppercaseName$Tuple reversed(Mutable$type.uppercaseName$Tuple t)
    {
        Objects.requireNonNull(t, "The input tuple is null");
        return new AbstractMutable$type.uppercaseName$Tuple()
        {
            @Override
            public int getSize()
            {
                return t.getSize();
            }
            
            @Override
            public $type.name$ get(int index)
            {
                return t.get(t.getSize() - 1 - index);
            }
            
            @Override
            public void set(int index, $type.name$ value)
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
    public static Mutable$type.uppercaseName$Tuple insertElementAt(
        $type.uppercaseName$Tuple t, int index, $type.name$ value, 
        Mutable$type.uppercaseName$Tuple result)
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
            result = $type.uppercaseName$Tuples.create(t.getSize() + 1);
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
    public static Mutable$type.uppercaseName$Tuple removeElementAt(
        $type.uppercaseName$Tuple t, int index, Mutable$type.uppercaseName$Tuple result)
    {
        Utils.checkForValidIndex(index, t.getSize());
        if (result == null)
        {
            result = $type.uppercaseName$Tuples.create(t.getSize() - 1);
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
    public static Mutable$type.uppercaseName$Tuple clamp(
        $type.uppercaseName$Tuple t, $type.name$ min, $type.name$ max, Mutable$type.uppercaseName$Tuple result)
    {
        result = validate(t, result);
        for (int i=0; i<result.getSize(); i++)
        {
            $type.name$ v = t.get(i);
            $type.name$ r = Math.min(max, Math.max(min, v));
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
    public static Mutable$type.uppercaseName$Tuple clamp(
        $type.uppercaseName$Tuple t, $type.uppercaseName$Tuple min, $type.uppercaseName$Tuple max, 
        Mutable$type.uppercaseName$Tuple result)
    {
        Utils.checkForEqualSize(min, max);
        result = validate(t, result);
        for (int i=0; i<result.getSize(); i++)
        {
            $type.name$ v = t.get(i);
            $type.name$ minV = min.get(i);
            $type.name$ maxV = max.get(i);
            $type.name$ r = Math.min(maxV, Math.max(minV, v));
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
    public static Mutable$type.uppercaseName$Tuple negate(
        $type.uppercaseName$Tuple t0, Mutable$type.uppercaseName$Tuple result)
    {
        return $type.uppercaseName$TupleFunctions.apply(
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
    public static Mutable$type.uppercaseName$Tuple add(
        $type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1, Mutable$type.uppercaseName$Tuple result)
    {
        return $type.uppercaseName$TupleFunctions.apply(
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
    public static Mutable$type.uppercaseName$Tuple subtract(
        $type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1, Mutable$type.uppercaseName$Tuple result)
    {
        return $type.uppercaseName$TupleFunctions.apply(
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
    public static Mutable$type.uppercaseName$Tuple multiply(
        $type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1, Mutable$type.uppercaseName$Tuple result)
    {
        return $type.uppercaseName$TupleFunctions.apply(
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
    public static Mutable$type.uppercaseName$Tuple divide(
        $type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1, Mutable$type.uppercaseName$Tuple result)
    {
        return $type.uppercaseName$TupleFunctions.apply(
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
    public static Mutable$type.uppercaseName$Tuple add(
        $type.uppercaseName$Tuple t0, $type.name$ value, Mutable$type.uppercaseName$Tuple result)
    {
        return $type.uppercaseName$TupleFunctions.apply(
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
    public static Mutable$type.uppercaseName$Tuple subtract(
        $type.uppercaseName$Tuple t0, $type.name$ value, Mutable$type.uppercaseName$Tuple result)
    {
        return $type.uppercaseName$TupleFunctions.apply(
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
    public static Mutable$type.uppercaseName$Tuple multiply(
        $type.uppercaseName$Tuple t0, $type.name$ factor, Mutable$type.uppercaseName$Tuple result)
    {
        return $type.uppercaseName$TupleFunctions.apply(
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
    public static Mutable$type.uppercaseName$Tuple addScaled(
        $type.uppercaseName$Tuple t0, $type.name$ factor, $type.uppercaseName$Tuple t1, 
        Mutable$type.uppercaseName$Tuple result)
    {
        return $type.uppercaseName$TupleFunctions.apply(
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
    public static $type.name$ dot($type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        $type.name$ result = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            result += t0.get(i) * t1.get(i);
        }
        return result;
    }



    /**
     * Computes the minimum value that occurs in the given tuple,
     * or <code>$type.max$</code> if the given tuple has a
     * size of 0.
     *
     * @param t The input tuple
     * @return The minimum value
     */
    public static $type.name$ min($type.uppercaseName$Tuple t)
    {
        return $type.uppercaseName$TupleFunctions.reduce(
            t, $type.max$, Math::min);
    }

    /**
     * Computes the maximum value that occurs in the given tuple,
     * or <code>$type.min$</code> if the given tuple has a
     * size of 0.
     *
     * @param t The input tuple
     * @return The maximum value
     */
    public static $type.name$ max($type.uppercaseName$Tuple t)
    {
        return $type.uppercaseName$TupleFunctions.reduce(
            t, $type.min$, Math::max);
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
    public static Mutable$type.uppercaseName$Tuple min(
        $type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1, Mutable$type.uppercaseName$Tuple result)
    {
        return $type.uppercaseName$TupleFunctions.apply(
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
    public static Mutable$type.uppercaseName$Tuple max(
        $type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1, Mutable$type.uppercaseName$Tuple result)
    {
        return $type.uppercaseName$TupleFunctions.apply(
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
    public static int compareLexicographically($type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1)
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
    public static int compareColexicographically($type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1)
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
    public static Comparator<$type.uppercaseName$Tuple> comparator(Order order)
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
        $type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1)
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
        $type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1)
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
        $type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1)
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
        $type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1)
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
        $type.uppercaseName$Tuple t, $type.name$ value)
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
        $type.uppercaseName$Tuple t, $type.name$ value)
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
        $type.uppercaseName$Tuple t, $type.name$ value)
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
        $type.uppercaseName$Tuple t, $type.name$ value)
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
    static Mutable$type.uppercaseName$Tuple validate(
        $type.uppercaseName$Tuple t, Mutable$type.uppercaseName$Tuple result)
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
    static String toString($type.uppercaseName$Tuple tuple)
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
    static String toString($type.uppercaseName$Tuple tuple, Locale locale, String format)
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
     * {@link #toString($type.uppercaseName$Tuple, Locale, String, int, int)} with
     * the default locale and format.
     * 
     * @param tuple The tuple
     * @param maxNumLeadingElements The maximum number of leading elements 
     * @param maxNumTrailingElements The maximum number of trailing elements
     * @return The string
     */
    public static String toString($type.uppercaseName$Tuple tuple, 
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
    public static String toString($type.uppercaseName$Tuple tuple, 
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
        StringBuilder sb, $type.uppercaseName$Tuple tuple, 
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
    static int hashCode($type.uppercaseName$Tuple tuple)
    {
        if (tuple == null)
        {
            return 0;
        }

        int result = 0;
        for (int i = 0; i < tuple.getSize(); i++)
        {
            $type.name$ value = tuple.get(i);
            result += $type.boxedName$.hashCode(value);
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
    static boolean equals($type.uppercaseName$Tuple tuple, Object object)
    {
        if (tuple == object)
        {
            return true;
        }
        if (object == null)
        {
            return false;
        }
        if (!(object instanceof $type.uppercaseName$Tuple))
        {
            return false;
        }
        $type.uppercaseName$Tuple other = ($type.uppercaseName$Tuple)object;
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
        $type.uppercaseName$Tuple t0, $type.uppercaseName$Tuple t1, $type.name$ epsilon)
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
            $type.name$ v0 = t0.get(i);
            $type.name$ v1 = t1.get(i);
            if (Math.abs(v0-v1) > epsilon)
            {
                return false;
            }
        }
        return true;
    }






    //=========================================================================
    // Start of custom methods

$customMethods$

    // End   of custom methods
    //=========================================================================

    /**
     * Private constructor to prevent instantiation
     */
    private $type.uppercaseName$Tuples()
    {
        // Private constructor to prevent instantiation
    }




}
