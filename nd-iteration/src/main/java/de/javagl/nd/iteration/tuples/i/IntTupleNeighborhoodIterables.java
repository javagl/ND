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

import java.util.Iterator;
import java.util.Objects;

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;

/**
 * Methods to create iterables over neighborhoods of {@link IntTuple}s.<br>
 * <br> 
 * Also see <a href="package-summary.html#Neighborhoods">Neighborhoods</a>
 */
public class IntTupleNeighborhoodIterables
{
    /**
     * Creates an iterable that provides iterators for iterating over the
     * Moore neighborhood of the given center and the given radius.<br>
     * <br> 
     * Also see <a href="../../package-summary.html#Neighborhoods">
     * Neighborhoods</a>
     * 
     * @param center The center of the Moore neighborhood
     * A copy of this tuple will be stored internally.
     * @param radius The radius of the Moore neighborhood
     * @param order The iteration {@link Order}
     * @return The iterable
     */
    public static Iterable<MutableIntTuple> mooreNeighborhoodIterable(
        IntTuple center, int radius, Order order)
    {
        return mooreNeighborhoodIterable(center, radius, null, null, order);
    }
    
    /**
     * Creates an iterable that provides iterators for iterating over the
     * Moore neighborhood of the given center and the given radius. <br>
     * <br>
     * If the given minimum- or maximum are non-<code>null</code>, 
     * they will be used for clamping the neighborhood.<br>
     * <br>
     * Copies of the given tuples will be stored internally.<br>
     * <br>
     * Note: The result of this method will be equivalent to creating 
     * an (unbounded) iterable of the specified neighborhood, and then
     * clamping the resulting iterable to the given bounds:
     * <pre><code>
     * Iterable&lt;MutableIntTuple&gt; iterable = 
     *     IntTupleNeighborhoodIterators.mooreNeighborhoodIterable(
     *         center, radius);
     * Iterable&lt;MutableIntTuple&gt; result =
     *     IntTupleIterables.clampingIterable(min, max);
     * </code></pre>
     * But as the bounds may be adjusted here at creation time, this 
     * method may be achieve a higher performance.<br>
     * <br> 
     * Also see <a href="../../package-summary.html#Neighborhoods">
     * Neighborhoods</a>
     * 
     * @param center The center of the Moore neighborhood
     * @param radius The radius of the Moore neighborhood
     * @param min The (optional) minimum, inclusive
     * @param max The (optional) maximum, exclusive
     * @param order The iteration {@link Order}
     * @return The iterable
     * @throws IllegalArgumentException If the given (non-<code>null</code>)
     * tuples do not have the same {@link Tuple#getSize() size}
     */
    public static Iterable<MutableIntTuple> mooreNeighborhoodIterable(
        IntTuple center, final int radius, 
        IntTuple min, IntTuple max,
        Order order)
    {
        Objects.requireNonNull(order, "The order is null");
        if (min != null)
        {
            Utils.checkForEqualSize(center, min);
        }
        if (max != null)
        {
            Utils.checkForEqualSize(center, max);
        }
        final IntTuple localCenter = IntTuples.copy(center);
        final IntTuple localMin = min == null ? null : IntTuples.copy(min);
        final IntTuple localMax = max == null ? null : IntTuples.copy(max);
        return () -> IntTupleNeighborhoodIterators.mooreNeighborhoodIterator(
            localCenter, radius, localMin, localMax, order);
    }
    
    /**
     * Creates an iterable that provides iterators for iterating over the
     * Von Neumann neighborhood of the given center and the given radius.<br> 
     * <br>
     * Also see <a href="../../package-summary.html#Neighborhoods">
     * Neighborhoods</a>
     * 
     * @param center The center of the Von Neumann neighborhood. 
     * A copy of this tuple will be stored internally.
     * @param radius The radius of the Von Neumann neighborhood
     * @return The iterable
     */
    public static Iterable<MutableIntTuple> vonNeumannNeighborhoodIterable(
        IntTuple center, final int radius)
    {
        final IntTuple localCenter = IntTuples.copy(center);
        return new Iterable<MutableIntTuple>()
        {
            @Override
            public Iterator<MutableIntTuple> iterator()
            {
                return new VonNeumannIntTupleIterator(localCenter, radius);
            }
        };
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private IntTupleNeighborhoodIterables()
    {
        // Private constructor to prevent instantiation
    }

}
