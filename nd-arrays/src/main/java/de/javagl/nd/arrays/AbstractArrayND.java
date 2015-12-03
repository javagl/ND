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

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTupleFunctions;
import de.javagl.nd.tuples.i.IntTuples;

/**
 * Abstract base implementation of a multidimensional array.
 */
public abstract class AbstractArrayND implements ArrayND
{
	/**
	 * The size of this array.
	 */
    private final IntTuple size;
    
    /**
     * The total size of this array (that is, the product of all elements
     * of the {@link #size}
     */
    private final int totalSize;
    
    /**
     * The preferred iteration order of this array
     */
    private final Order preferredIterationOrder;
    
    /**
     * Creates a new multidimensional array with the given size.
     * 
     * @param size The size of the array.
     * @param preferredIterationOrder The preferred iteration {@link Order}
     * of this array. This may be <code>null</code> if the array does not
     * have a preferred iteration order.
     * @throws NullPointerException If the given size is <code>null</code>
     * @throws IllegalArgumentException If the given size is negative
     * along any dimension
     */
    protected AbstractArrayND(IntTuple size, Order preferredIterationOrder)
    {
        Utils.checkForNonNegativeElements(size);
        this.size = IntTuples.copy(size);
        this.totalSize = IntTupleFunctions.reduce(size, 1, (a, b) -> a * b);
        this.preferredIterationOrder = preferredIterationOrder; 
            
    }
    
    @Override
    public final int getTotalSize()
    {
        return totalSize;
    }
    
    @Override
    public final IntTuple getSize()
    {
        return size;
    }
    
    @Override
    public Order getPreferredIterationOrder()
    {
        return preferredIterationOrder;
    }
}