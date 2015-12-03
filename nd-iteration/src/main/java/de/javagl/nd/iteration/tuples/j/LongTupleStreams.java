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
package de.javagl.nd.iteration.tuples.j;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;
import de.javagl.nd.tuples.j.LongTuple;
import de.javagl.nd.tuples.j.LongTuples;
import de.javagl.nd.tuples.j.MutableLongTuple;

/**
 * Methods to create streams over ranges of {@link LongTuple}s
 */
public class LongTupleStreams 
{
    /**
     * Returns a stream that returns {@link MutableLongTuple}s in the given
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
    public static Stream<MutableLongTuple> stream(
        Order order, LongTuple min, LongTuple max)
    {
        if (order == null)
        {
            return null;
        }
        Utils.checkForEqualSize(min, max);
        MutableLongTuple localMin = LongTuples.copy(min);
        MutableLongTuple localMax = LongTuples.copy(max);
        LongTupleRangeSpliterator spliterator =
            new LongTupleRangeSpliterator(
                order, localMin, localMax);
        return StreamSupport.stream(spliterator, false);
    }
    
    
    /**
     * Returns a stream that returns {@link MutableLongTuple}s in the given
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
    public static Stream<MutableLongTuple> lexicographicalStream(
        LongTuple min, LongTuple max)
    {
        return stream(Order.LEXICOGRAPHICAL, 
            min, max);
    }
    
    /**
     * Returns a stream that returns {@link MutableLongTuple}s up to the given
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
    public static Stream<MutableLongTuple> lexicographicalStream(
        LongTuple max)
    {
        return stream(Order.LEXICOGRAPHICAL, 
            LongTuples.zero(max.getSize()), max);
    }
    
    
    
    /**
     * Returns a stream that returns {@link MutableLongTuple}s in the given
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
    public static Stream<MutableLongTuple> colexicographicalStream(
        LongTuple min, LongTuple max)
    {
        return stream(Order.COLEXICOGRAPHICAL, 
            min, max);
    }

    /**
     * Returns a stream that returns {@link MutableLongTuple}s up to the given
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
    public static Stream<MutableLongTuple> colexicographicalStream(
        LongTuple max)
    {
        return stream(Order.COLEXICOGRAPHICAL, 
            LongTuples.zero(max.getSize()), max);
    }
    
    
    /**
     * Returns a stream that returns the {@link MutableLongTuple}s from the
     * given delegate, wrapped at the given bounds.<br>   
     * <br>
     * A copy of the given tuple will be stored internally.<br>
     * <br>
     * 
     * @param bounds The bounds
     * @param delegate The delegate iterator
     * @return The stream
     */
    public static Stream<MutableLongTuple> wrappingStream(
        LongTuple bounds, Stream<? extends MutableLongTuple> delegate)
    {
        LongTuple localBounds = LongTuples.copy(bounds);
        return delegate.map(t -> LongTupleUtils.wrap(t, localBounds));
    }
    
    
    /**
     * Returns a stream that returns the {@link MutableLongTuple}s from the
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
    public static <T extends MutableLongTuple> Stream<T> clampingStream(
        LongTuple min, LongTuple max, Stream<T> delegate)
    {
        Utils.checkForEqualSize(min, max);
        LongTuple localMin = LongTuples.copy(min);
        LongTuple localMax = LongTuples.copy(max);
        return delegate.filter(t -> 
            LongTuples.areElementsGreaterThanOrEqual(t, localMin) &&
            LongTuples.areElementsLessThan(t, localMax));
    }
    
    
    
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private LongTupleStreams()
    {
        // Private constructor to prevent instantiation
    }


}

