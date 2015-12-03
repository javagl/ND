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
package de.javagl.nd.arrays;

import java.util.stream.Stream;

import de.javagl.nd.iteration.tuples.i.IntTupleStreams;
import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.MutableIntTuple;

/**
 * Methods that provide streams over the coordinates of multidimensional 
 * arrays, based on a given array size.<br>
 * <br>
 * Also see <a href="package-summary.html#IndexingAndIterationOrder">
 * Indexing and Iteration Order</a>
 */
public class Coordinates
{
    /**
     * Returns a default stream over the coordinates of an array with
     * the given size. The iteration order of this streams is 
     * unspecified. No caller should make assumptions about the 
     * iteration order. This method is intended for situations where 
     * the same iteration order for multiple arrays is required.
     *  
     * @param arraySize The array {@link ArrayND#getSize() size}
     * @return The streams
     * @throws NullPointerException If the given size is <code>null</code>
     */
    public static Stream<MutableIntTuple> coordinates(IntTuple arraySize)
    {
        return IntTupleStreams.lexicographicalStream(arraySize);
    }

    /**
     * Returns a stream over the coordinates of an array with the given 
     * size, using the given iteration {@link Order}. If the given
     * {@link Order} is <code>null</code>, then an unspecified default
     * order will be used.
     * 
     * @param order The iteration {@link Order}
     * @param arraySize The array {@link ArrayND#getSize() size}
     * @return The streams
     * @throws NullPointerException If the given size is <code>null</code>
     */
    public static Stream<MutableIntTuple> coordinates(
        Order order, IntTuple arraySize)
    {
        if (order == Order.COLEXICOGRAPHICAL)
        {
            return colexicographicalCoordinates(arraySize);
        }
        return lexicographicalCoordinates(arraySize);
    }
    
    
    /**
     * Returns a stream over the coordinates of an array with
     * the given size, in lexicographical order.
     *  
     * @param arraySize The array {@link ArrayND#getSize() size}
     * @return The stream
     * @throws NullPointerException If the given size is <code>null</code>
     */
    public static Stream<MutableIntTuple> lexicographicalCoordinates(
        IntTuple arraySize)
    {
        return IntTupleStreams.lexicographicalStream(arraySize);
    }
    
    /**
     * Returns a stream over the coordinates of an array with
     * the given size, in colexicographical order.
     *  
     * @param arraySize The array {@link ArrayND#getSize() size}
     * @return The stream
     * @throws NullPointerException If the given size is <code>null</code>
     */
    public static Stream<MutableIntTuple> colexicographicalCoordinates(
        IntTuple arraySize)
    {
        return IntTupleStreams.colexicographicalStream(arraySize);
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private Coordinates()
    {
        // Private constructor to prevent instantiation
    }
}
