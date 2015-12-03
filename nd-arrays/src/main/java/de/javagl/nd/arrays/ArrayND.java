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

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.MutableIntTuple;

/**
 * Interface describing a multidimensional array
 */
public interface ArrayND
{
	/**
	 * Returns the size of the array.
	 * 
	 * @return The size of the array.
	 */
    IntTuple getSize();
    
    /**
     * Returns the total size of this array. This is the product of the
     * {@link #getSize() size} along all dimensions.
     * 
     * @return The total size
     */
    int getTotalSize();
    
    /**
     * Returns the preferred iteration {@link Order} of this array, or
     * <code>null</code> if this array does not have any preferred 
     * iteration order.
     * 
     * @return The preferred iteration {@link Order}
     */
    Order getPreferredIterationOrder();
    
    /**
     * Returns a stream of the coordinates of this array. This stream will 
     * return all valid coordinates inside this array, in its 
     * {@link #getPreferredIterationOrder() preferred iteration order}.
     * (For example, for a 2D array, it may return the coordinates that 
     * either represent a row-major, or a column-major iteration order).<br>
     * <br>
     * To obtain a stream over the coordinates of several arrays in a 
     * consistent order, {@link Coordinates#coordinates(IntTuple)} 
     * may be used, passing in the {@link #getSize() size} of the array.<br>
     * <br>
     * To obtain a stream over the coordinates in one particular  
     * order, {@link Coordinates#lexicographicalCoordinates(IntTuple)} 
     * or {@link Coordinates#colexicographicalCoordinates(IntTuple)} 
     * may be used.<br>
     * <br>
     * Also see <a href="package-summary.html#IndexingAndIterationOrder">
     * Indexing and Iteration Order</a>
     * 
     * @return The stream over the coordinates.
     */
    default Stream<? extends MutableIntTuple> coordinates()
    {
        return Coordinates.coordinates(
            getPreferredIterationOrder(), getSize());
    }
    
    /**
     * Returns a <i>view</i> on a portion of this array. Changes in this
     * array will be visible in the returned array. 
     *
     * @param fromIndices The start indices of the sub-array along
     * each dimension (inclusive)
     * @param toIndices The end indices of the sub-array along
     * each dimension (exclusive)
     * @return The sub-array
     * @throws NullPointerException if any of the given indices are 
     * <code>null</code>
     * @throws IllegalArgumentException If the indices are not valid. This
     * is the case when the {@link IntTuple#getSize() size} of the start- or
     * end indices is different than the size of the {@link #getSize()} of 
     * this array, or when
     * <code>fromIndex &lt; 0</code> or 
     * <code>toIndex &gt; size(i)</code> 
     * or <code>fromIndex &gt; toIndex</code> for any dimension.
     */
    ArrayND subArray(IntTuple fromIndices, IntTuple toIndices);
    
}
