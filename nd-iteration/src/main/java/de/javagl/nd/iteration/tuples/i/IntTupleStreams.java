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
package de.javagl.nd.iteration.tuples.i;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;

/**
 * Methods to create streams over ranges of {@link IntTuple}s
 */
public class IntTupleStreams 
{
    /**
     * Returns a stream that returns {@link MutableIntTuple}s in the given
     * range, in the specified iteration {@link Order}. It the given 
     * {@link Order} is <code>null</code>, then <code>null</code> will
     * be returned.<br>
     * <br>
     * Copies of the given tuples will be stored internally.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     * 
     * @param order The iteration {@link Order}
     * @param min The minimum values, inclusive
     * @param max The maximum values, exclusive
     * @return The stream
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    public static Stream<MutableIntTuple> stream(
        Order order, IntTuple min, IntTuple max)
    {
        if (order == null)
        {
            return null;
        }
        Utils.checkForEqualSize(min, max);
        MutableIntTuple localMin = IntTuples.copy(min);
        MutableIntTuple localMax = IntTuples.copy(max);
        IntTupleRangeSpliterator spliterator =
            new IntTupleRangeSpliterator(
                order, localMin, localMax);
        return StreamSupport.stream(spliterator, false);
    }
    
    
    /**
     * Returns a stream that returns {@link MutableIntTuple}s in the given
     * range, in lexicographical iteration order. <br>
     * <br>
     * Copies of the given tuples will be stored internally.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     *   
     * @param min The minimum values, inclusive
     * @param max The maximum values, exclusive
     * @return The stream
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    public static Stream<MutableIntTuple> lexicographicalStream(
        IntTuple min, IntTuple max)
    {
        return stream(Order.LEXICOGRAPHICAL, 
            min, max);
    }
    
    /**
     * Returns a stream that returns {@link MutableIntTuple}s up to the given
     * maximum, in lexicographical iteration order.<br>
     * <br>
     * A copy of the given tuple will be stored internally.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     * 
     * 
     * @param max The maximum values, exclusive
     * @return The stream
     */
    public static Stream<MutableIntTuple> lexicographicalStream(
        IntTuple max)
    {
        return stream(Order.LEXICOGRAPHICAL, 
            IntTuples.zero(max.getSize()), max);
    }
    
    
    
    /**
     * Returns a stream that returns {@link MutableIntTuple}s in the given
     * range, in colexicographical iteration order. <br>
     * <br>
     * Copies of the given tuples will be stored internally.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     *   
     * @param min The minimum values, inclusive
     * @param max The maximum values, exclusive
     * @return The stream
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    public static Stream<MutableIntTuple> colexicographicalStream(
        IntTuple min, IntTuple max)
    {
        return stream(Order.COLEXICOGRAPHICAL, 
            min, max);
    }

    /**
     * Returns a stream that returns {@link MutableIntTuple}s up to the given
     * maximum, in colexicographical iteration order. <br>
     * <br>
     * A copy of the given tuple will be stored internally.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     * 
     * @param max The maximum values, exclusive
     * @return The stream
     */
    public static Stream<MutableIntTuple> colexicographicalStream(
        IntTuple max)
    {
        return stream(Order.COLEXICOGRAPHICAL, 
            IntTuples.zero(max.getSize()), max);
    }
    
    
    /**
     * Returns a stream that returns the {@link MutableIntTuple}s from the
     * given delegate, wrapped at the given bounds.<br>   
     * <br>
     * A copy of the given tuple will be stored internally.<br>
     * <br>
     * 
     * @param bounds The bounds
     * @param delegate The delegate iterator
     * @return The stream
     */
    public static Stream<MutableIntTuple> wrappingStream(
        IntTuple bounds, Stream<? extends MutableIntTuple> delegate)
    {
        IntTuple localBounds = IntTuples.copy(bounds);
        return delegate.map(t -> IntTupleUtils.wrap(t, localBounds));
    }
    
    
    /**
     * Returns a stream that returns the {@link MutableIntTuple}s from the
     * given delegate that are contained in the given bounds.<br>   
     * <br>
     * Copies of the given tuples will be stored internally.<br>
     * <br>
     * 
     * @param <T> The type of the stream elements
     * 
     * @param min The minimum, inclusive
     * @param max The maximum, exclusive
     * @param delegate The delegate iterator
     * @return The stream
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    public static <T extends MutableIntTuple> Stream<T> clampingStream(
        IntTuple min, IntTuple max, Stream<T> delegate)
    {
        Utils.checkForEqualSize(min, max);
        IntTuple localMin = IntTuples.copy(min);
        IntTuple localMax = IntTuples.copy(max);
        return delegate.filter(t -> 
            IntTuples.areElementsGreaterThanOrEqual(t, localMin) &&
            IntTuples.areElementsLessThan(t, localMax));
    }
    
    
    
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private IntTupleStreams()
    {
        // Private constructor to prevent instantiation
    }


}

