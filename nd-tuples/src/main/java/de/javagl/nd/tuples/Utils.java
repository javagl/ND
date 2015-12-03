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
package de.javagl.nd.tuples;

/**
 * Internal utility methods for the tuples package. <br>
 * <br>
 * These methods should <b>not</b> be considered as being a part of the 
 * public API.
 */
public class Utils
{
    /**
     * Checks whether given given {@link Tuple}s have equal
     * {@link Tuple#getSize() size}, and throws an 
     * <code>IllegalArgumentException</code> if not.
     * 
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @throws IllegalArgumentException If the given tuples
     * do not have the same {@link Tuple#getSize() size}
     */
    public static void checkForEqualSize(Tuple t0, Tuple t1)
    {
        if (t0.getSize() != t1.getSize())
        {
            throw new IllegalArgumentException(
                "Sizes do not match: "+t0.getSize()+
                " and "+t1.getSize());
        }
    }
    
    /**
     * Checks whether the given indices are valid indices for a sub-tuple
     * of a parent tuple with the given size, and throws an 
     * <code>IllegalArgumentException</code> if not.
     *  
     * @param parentSize The size of the parent
     * @param fromIndex The start index of the sub-tuple, inclusive
     * @param toIndex The end index of the sub-tuple, exclusive
     * @throws IllegalArgumentException If the given indices are invalid.
     * This is the case when <code>fromIndex &lt; 0</code>, 
     * <code>fromIndex &gt; toIndex</code>, or 
     * <code>toIndex &gt; parentSize</code>,
     */
    public static void checkForValidSubTupleIndices(
        int parentSize, int fromIndex, int toIndex)
    {
        if (fromIndex < 0)
        {
            throw new IllegalArgumentException(
                "Start index is negative: "+fromIndex);
        }
        if (toIndex > parentSize)
        {
            throw new IllegalArgumentException(
                "End index is "+toIndex+", parent size is "+parentSize);
        }
        if (fromIndex > toIndex)
        {
            throw new IllegalArgumentException(
                "Start index is "+fromIndex+", end index is "+toIndex);
        }
    }
    
    /**
     * Checks whether the given index is valid for accessing a tuple with 
     * the given size, and throws an <code>IndexOutOfBoundsException</code> 
     * if not.
     * 
     * @param index The index
     * @param size The size
     * @throws IndexOutOfBoundsException If the given index is negative
     * or not smaller than then given size
     */
    public static void checkForValidIndex(int index, int size)
    {
        if (index < 0)
        {
            throw new IndexOutOfBoundsException(
                "Index "+index+" is negative");
        }
        if (index >= size)
        {
            throw new IndexOutOfBoundsException(
                "Index "+index+", size "+size);
        }
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Utils()
    {
        // Private constructor to prevent instantiation
    }
}
