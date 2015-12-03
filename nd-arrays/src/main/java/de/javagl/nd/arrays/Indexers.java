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

import java.util.Objects;
import java.util.function.ToIntFunction;

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTupleFunctions;
import de.javagl.nd.tuples.i.IntTuples;

/**
 * Methods to create functions that map {@link IntTuple} instances to
 * 1-dimensional (array) indices.<br>
 * <br>
 * Also see <a href="package-summary.html#IndexingAndIterationOrder">
 * Indexing and Iteration Order</a> 
 */
public class Indexers
{
    /**
     * Creates a function that maps multidimensional indices to 1D indices.
     * The function will create consecutive 1D indices for indices that 
     * are given in the given {@link Order}. If the given {@link Order} 
     * is <code>null</code>, then an unspecified default order will be
     * used.
     *  
     * @param order The order for the indexing
     * @param size The size of the array to index
     * @return The indexer function
     * @throws NullPointerException If the given size is <code>null</code>.
     */
    public static ToIntFunction<IntTuple> indexer(Order order, IntTuple size)
    {
        if (order == Order.COLEXICOGRAPHICAL)
        {
            return colexicographicalIndexer(size);
        }
        return lexicographicalIndexer(size);
    }
    
    /**
     * Creates a function that maps multidimensional indices to 1D indices.
     * The function will create consecutive 1D indices for indices that 
     * are given in lexicographical order. 
     * 
     * @param size The size of the array to index
     * @return The indexer function
     * @throws NullPointerException If the given size is <code>null</code>.
     */
    public static ToIntFunction<IntTuple> lexicographicalIndexer(
        IntTuple size)
    {
        Objects.requireNonNull(size, "The size is null");
        IntTuple reversedProducts =
            IntTupleFunctions.exclusiveScan(
                IntTuples.reversed(size), 1, (a, b) -> a * b, null);
        IntTuple sizeProducts = IntTuples.reverse(reversedProducts, null);
        return indices -> IntTuples.dot(indices, sizeProducts); 
    }

    /**
     * Creates a function that maps multidimensional indices to 1D indices.
     * The function will create consecutive 1D indices for indices that 
     * are given in colexicographical order. 
     * 
     * @param size The size of the array to index
     * @return The indexer function
     * @throws NullPointerException If the given size is <code>null</code>.
     */
    public static ToIntFunction<IntTuple> colexicographicalIndexer(
        IntTuple size)
    {
        Objects.requireNonNull(size, "The size is null");
        IntTuple sizeProducts =
            IntTupleFunctions.exclusiveScan(
                size, 1, (a, b) -> a * b, null);
        return indices -> IntTuples.dot(indices, sizeProducts); 
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Indexers()
    {
        // Private constructor to prevent instantiation
    }
}
