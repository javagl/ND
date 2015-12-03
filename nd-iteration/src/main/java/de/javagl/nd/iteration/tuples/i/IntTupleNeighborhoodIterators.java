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

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;

/**
 * Methods to create iterators over neighborhoods of {@link IntTuple}s.<br>
 * <br> 
 * Used by the {@link IntTupleNeighborhoodIterables}.<br>
 * <br> 
 * Also see <a href="../../package-summary.html#Neighborhoods">
 * Neighborhoods</a>
 */
class IntTupleNeighborhoodIterators
{
    /**
     * Creates an iterator for iterating over the Moore neighborhood of 
     * the given center and the given radius.<br> 
     * <br>
     * If the given minimum- or maximum are non-<code>null</code>, 
     * they will be used for clamping the neighborhood.
     * 
     * @param center The center of the Moore neighborhood
     * @param radius The radius of the Moore neighborhood
     * @param min The (optional) minimum, inclusive
     * @param max The (optional) maximum, exclusive
     * @param order The {@link Order} for the iteration
     * @return The iterator
     * @throws IllegalArgumentException If the given (non-<code>null</code>)
     * tuples do not have the same {@link Tuple#getSize() size}
     */
    static Iterator<MutableIntTuple> mooreNeighborhoodIterator(
        IntTuple center, int radius, 
        IntTuple min, IntTuple max,
        Order order)
    {
        if (min != null)
        {
            Utils.checkForEqualSize(center, max);
        }
        if (max != null)
        {
            Utils.checkForEqualSize(center, max);
        }
        MutableIntTuple resultMin = IntTuples.subtract(center, radius, null);
        MutableIntTuple resultMax = IntTuples.add(center, radius+1, null);
        if (min != null)
        {
            IntTuples.max(min, resultMin, resultMin);
            IntTuples.max(min, resultMax, resultMax);
        }
        if (max != null)
        {
            IntTuples.min(max, resultMin, resultMin);
            IntTuples.min(max, resultMax, resultMax);
        }
        return new IntTupleIterator(resultMin, resultMax, 
            IntTupleIncrementors.incrementor(order));
    }

    /**
     * Private constructor to prevent instantiation
     */
    private IntTupleNeighborhoodIterators()
    {
        // Private constructor to prevent instantiation
    }

}
