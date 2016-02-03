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
package de.javagl.nd.arrays.$type.letter$;

import java.util.Objects;
import java.util.function.To$type.uppercaseName$Function;
import java.util.stream.Stream;

import de.javagl.nd.arrays.Coordinates;
import de.javagl.nd.arrays.Utils;
import de.javagl.nd.tuples.$type.letter$.$type.uppercaseName$Tuple;
import de.javagl.nd.tuples.$type.letter$.Mutable$type.uppercaseName$Tuple;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTupleFunctions;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;
import de.javagl.nd.iteration.tuples.i.IntTupleIterables;

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Methods related to {@link $type.uppercaseName$ArrayND} instances
 */
public class $type.uppercaseName$ArraysND
{
    /**
     * Creates a new {@link Mutable$type.uppercaseName$ArrayND} with the specified size
     * 
     * @param size The size
     * @return The new array
     * @throws NullPointerException If the given size is <code>null</code>
     * @throws IllegalArgumentException If the given size is negative
     * along any dimension
     */
    public static Mutable$type.uppercaseName$ArrayND create(IntTuple size)
    {
        return new Default$type.uppercaseName$ArrayND(size);
    }

    /**
     * Creates a new {@link Mutable$type.uppercaseName$ArrayND} with the specified size
     * 
     * @param size The size
     * @return The new array
     * @throws NullPointerException If the given size is <code>null</code>
     * @throws IllegalArgumentException If the given size is negative
     * along any dimension
     */
    public static Mutable$type.uppercaseName$ArrayND create(int ... size)
    {
        return create(IntTuples.wrap(size));
    }

    /**
     * Creates a <i>view</i> on the given array as a 
     * {@link Mutable$type.uppercaseName$ArrayND}. Changes in the given array
     * will be visible in the returned array, and vice versa.<br>
     * <br>
     * The given array is assumed to be rectangular (and to not contain
     * <code>null</code> elements). 
     * 
     * @param array The backing array
     * @return The view on the array
     * @throws NullPointerException If the given array is <code>null</code>
     */
    public static Mutable$type.uppercaseName$ArrayND wrap($type.name$ array[][])
    {
        return new Array$type.uppercaseName$Array2D(array);
    }

    /**
     * Creates a <i>view</i> on the given tuple as a {@link $type.uppercaseName$ArrayND}.
     * Changes in the given tuple will be visible in the returned array.
     * 
     * @param t The tuple
     * @param size The size of the array
     * @return The view on the tuple
     * @throws NullPointerException If any argument is <code>null</code>
     * @throws IllegalArgumentException If the 
     * {@link $type.uppercaseName$Tuple#getSize() size} of the tuple does not match the
     * given array size (that is, the product of all elements of the given
     * tuple). 
     */
    public static $type.uppercaseName$ArrayND wrap($type.uppercaseName$Tuple t, IntTuple size)
    {
        Objects.requireNonNull(t, "The tuple is null");
        Objects.requireNonNull(size, "The size is null");
        int totalSize = IntTupleFunctions.reduce(size, 1, (a, b) -> a * b);
        if (t.getSize() != totalSize)
        {
            throw new IllegalArgumentException(
                "The tuple has a size of " + t.getSize() + ", the expected " + 
                "array size is " + size + " (total: " + totalSize + ")");
        }
        return new Tuple$type.uppercaseName$ArrayND(t, size);
    }


    /**
     * Creates a <i>view</i> on the given tuple as a {@link $type.uppercaseName$ArrayND}.
     * Changes in the given tuple will be visible in the returned array,
     * and vice versa.
     * 
     * @param t The tuple
     * @param size The size of the array
     * @return The view on the tuple
     * @throws NullPointerException If any argument is <code>null</code>
     * @throws IllegalArgumentException If the 
     * {@link $type.uppercaseName$Tuple#getSize() size} of the tuple does not match the
     * given array size (that is, the product of all elements of the given
     * tuple). 
     */
    public static Mutable$type.uppercaseName$ArrayND wrap(
        Mutable$type.uppercaseName$Tuple t, IntTuple size)
    {
        Objects.requireNonNull(t, "The tuple is null");
        Objects.requireNonNull(size, "The size is null");
        int totalSize = IntTupleFunctions.reduce(size, 1, (a, b) -> a * b);
        if (t.getSize() != totalSize)
        {
            throw new IllegalArgumentException(
                "The tuple has a size of " + t.getSize() + ", the expected " + 
                "array size is " + size + " (total: " + totalSize + ")");
        }
        return new MutableTuple$type.uppercaseName$ArrayND(t, size);
    }

    /**
     * Returns a view on the given {@link $type.uppercaseName$ArrayND} as a function 
     * that maps coordinates to array entry values. This function <b>may</b>
     * throw an IndexOutOfBoundsException when the given indices are not 
     * valid. That is, if fromIndex &lt; 0 or toIndex &gt; size or 
     * fromIndex &gt; toIndex for any dimension. But explicit bounds 
     * checking is not guaranteed.
     * 
     * @param array The array
     * @return The function
     * @throws NullPointerException If the given array is <code>null</code>
     */
    public static To$type.uppercaseName$Function<IntTuple> asFunction(
        final $type.uppercaseName$ArrayND array)
    {
        Objects.requireNonNull(array, "The array is null");
        return new To$type.uppercaseName$Function<IntTuple>()
        {
            @Override
            public $type.name$ applyAs$type.uppercaseName$(IntTuple s)
            {
                return array.get(s);
            }
        };
    }


    /**
     * Creates a new array that is a <i>view</i> on the specified portion 
     * of the given parent. Changes in the parent will be visible in the 
     * returned array.
     * 
     * @param parent The parent array
     * @param fromIndices The start indices in the parent, inclusive
     * @param toIndices The end indices in the parent, exclusive
     * @throws NullPointerException If any argument is <code>null</code>
     * @throws IllegalArgumentException If the indices are not valid. This
     * is the case when the {@link IntTuple#getSize() size} of the start- or
     * end indices is different than the parent size, or when
     * <code>fromIndex &lt; 0</code> or 
     * <code>toIndex &gt; parentSize(i)</code> 
     * or <code>fromIndex &gt; toIndex</code> for any dimension.
     * @return The new array
     */
    public static $type.uppercaseName$ArrayND createSubArray(
        $type.uppercaseName$ArrayND parent, IntTuple fromIndices, IntTuple toIndices)
    {
        return new Sub$type.uppercaseName$ArrayND(
            parent, fromIndices, toIndices);
    }

    /**
     * Creates a new array that is a <i>view</i> on the specified portion 
     * of the given parent. Changes in the parent will be visible in the 
     * returned array, and vice versa. The returned array will use 
     * lexicographical iteration order. 
     * 
     * @param parent The parent array
     * @param fromIndices The start indices in the parent, inclusive
     * @param toIndices The end indices in the parent, exclusive
     * @throws NullPointerException If the given parent or any of the
     * given tuples is <code>null</code>
     * @throws IllegalArgumentException If the indices are not valid. This
     * is the case when the {@link IntTuple#getSize() size} of the start- or
     * end indices is different than the parent size, or when
     * <code>fromIndex &lt; 0</code> or 
     * <code>toIndex &gt; parentSize(i)</code> 
     * or <code>fromIndex &gt; toIndex</code> for any dimension.
     * @return The new array
     */
    public static Mutable$type.uppercaseName$ArrayND createSubArray(
        Mutable$type.uppercaseName$ArrayND parent, IntTuple fromIndices, IntTuple toIndices)
    {
        return new MutableSub$type.uppercaseName$ArrayND(
            parent, fromIndices, toIndices);
    }

    /**
     * Returns the minimum value in the given array, or 
     * <code>$type.max$</code> if the given array
     * has a size of 0.
     * 
     * @param array The array
     * @return The minimum value
     */
    public static $type.name$ min($type.uppercaseName$ArrayND array)
    {
        return array.stream().parallel().reduce(
            $type.max$, Math::min);
    }

    /**
     * Returns the maximum value in the given array, or 
     * <code>$type.min$</code> if the given array
     * has a size of 0.
     * 
     * @param array The array
     * @return The maximum value
     */
    public static $type.name$ max($type.uppercaseName$ArrayND array)
    {
        return array.stream().parallel().reduce(
            $type.min$, Math::max);
    }


    /**
     * Returns a default string representation of the given array.
     * Since arrays tend to be large (and multidimensional), this 
     * string will <b>not</b> contain the <i>contents</i> of
     * the array, but only its size.
     * 
     * @param array The array
     * @return The string
     */
    static String toString($type.uppercaseName$ArrayND array)
    {
        if (array == null)
        {
            return "null";
        }
        return array.getClass().getSimpleName() + 
            "[size=" + array.getSize() + "]";
    }

    /**
     * Computes the hash code of the given array. This method will
     * return a hash code that solely depends on the contents of
     * the given array, regardless of its preferred iteration order.
     * 
     * @param array The array
     * @return The hash code
     */
    static int hashCode($type.uppercaseName$ArrayND array)
    {
        if (array == null)
        {
            return 0;
        }
        return array.stream().parallel().mapToInt($type.boxedName$::hashCode).sum();
    }

    /**
     * Returns whether the given {@link $type.uppercaseName$ArrayND} equals the
     * given object
     * 
     * @param array The array
     * @param object The object
     * @return Whether the array equals the object
     */
    static boolean equals($type.uppercaseName$ArrayND array, Object object)
    {
        if (array == object)
        {
            return true;
        }
        if (object == null)
        {
            return false;
        }
        if (!(object instanceof $type.uppercaseName$ArrayND))
        {
            return false;
        }
        $type.uppercaseName$ArrayND other = ($type.uppercaseName$ArrayND) object;
        if (!array.getSize().equals(other.getSize()))
        {
            return false;
        }
        Stream<MutableIntTuple> coordinates = 
            Coordinates.coordinates(
                array.getPreferredIterationOrder(), array.getSize());
        Iterable<MutableIntTuple> iterable = () -> coordinates.iterator();
        for (IntTuple coordinate : iterable)
        {
            $type.name$ arrayValue = array.get(coordinate);
            $type.name$ otherValue = other.get(coordinate);
            if (arrayValue != otherValue)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a formatted representation of the given array. After each
     * dimension, a newline character will be inserted. 
     * 
     * @param array The array
     * @return The hash code
     */
    public static String toFormattedString($type.uppercaseName$ArrayND array)
    {
        return toFormattedString(array, "%f");
    }

    /**
     * Creates a formatted representation of the given array. After each
     * dimension, a newline character will be inserted. 
     * 
     * @param array The array
     * @param format The format for the array entries
     * @return The hash code
     */
    public static String toFormattedString($type.uppercaseName$ArrayND array, String format)
    {
        if (array == null)
        {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        Iterable<MutableIntTuple> iterable = 
            IntTupleIterables.lexicographicalIterable(array.getSize());
        IntTuple previous = null;
        for (IntTuple coordinates : iterable)
        {
            if (previous != null)
            {
                int c = Utils.countDifferences(previous, coordinates);
                for (int i=0; i<c-1; i++)
                {
                    sb.append("\n");
                }
            }
            $type.name$ value = array.get(coordinates);
            sb.append(String.format(format+", ", value));
            previous = coordinates;
        }
        return sb.toString();
    }



    /**
     * Private constructor to prevent instantiation
     */
    private $type.uppercaseName$ArraysND()
    {
        // Private constructor to prevent instantiation
    }


}