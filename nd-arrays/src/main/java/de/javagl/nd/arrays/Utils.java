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

import de.javagl.nd.tuples.i.IntTuple;

/**
 * Internal utility methods for the arrays package. <br>
 * <br>
 * These methods should <b>not</b> be considered as being a part of the 
 * public API.
 */
public class Utils
{
    /**
     * Checks whether given given {@link ArrayND}s have equal dimensions, 
     * and throws an <code>IllegalArgumentException</code> if not.
     * 
     * @param a0 The first array
     * @param a1 The second array
     * @throws NullPointerException If any argument is <code>null</code>
     * @throws IllegalArgumentException If the given arrays
     * do not have equal dimensions
     */
    public static void checkForEqualDimensions(ArrayND a0, ArrayND a1)
    {
        if (a0.getSize().getSize() != a1.getSize().getSize())
        {
            throw new IllegalArgumentException(
                "Arrays have different dimensions: "+a0.getSize().getSize()+
                " and "+a1.getSize().getSize());
        }
    }
    
    /**
     * Checks whether given given {@link ArrayND}s have equal sizes, 
     * and throws an <code>IllegalArgumentException</code> if not.
     * 
     * @param a0 The first array
     * @param a1 The second array
     * @throws NullPointerException If any argument is <code>null</code>
     * @throws IllegalArgumentException If the given arrays
     * do not have equal sizes
     */
    public static void checkForEqualSizes(ArrayND a0, ArrayND a1)
    {
        if (!a0.getSize().equals(a1.getSize()))
        {
            throw new IllegalArgumentException(
                "Arrays have different sizes: "+a0.getSize() +
                " and "+a1.getSize());
        }
    }
    
    /**
     * Checks whether the elements of the given tuple are not negative,
     * and throws an <code>IllegalArgumentException</code> if any of
     * them is negative.
     * 
     * @param t The tuple
     * @throws NullPointerException If the given size is <code>null</code>
     * @throws IllegalArgumentException If any element of the given tuple
     * is negative
     */
    public static void checkForNonNegativeElements(IntTuple t)
    {
        for (int i=0; i<t.getSize(); i++)
        {
            if (t.get(i) < 0)
            {
                throw new IllegalArgumentException("Negative size: "+t);
            }
        }
    }
    
    /**
     * Checks whether the given tuples specify a valid range for a sub-array
     * of an array with the given parent size, and throws an
     * <code>IllegalArgumentException</code> if not.
     * 
     * @param parentSize The parent size
     * @param fromIndices The start indices, inclusive
     * @param toIndices The end indices, exclusive
     * @throws NullPointerException If any of the given tuples is 
     * <code>null</code>
     * @throws IllegalArgumentException If the indices are not valid. This
     * is the case when the {@link IntTuple#getSize() size} of the start- or
     * end indices is different than the parent size, or when
     * <code>fromIndex &lt; 0</code> or 
     * <code>toIndex &gt; parentSize.get(i)</code> 
     * or <code>fromIndex &gt; toIndex</code> for any dimension.
     */
    public static void checkForValidSubArrayIndices(
        IntTuple parentSize, IntTuple fromIndices, IntTuple toIndices)
    {
        if (fromIndices.getSize() != parentSize.getSize())
        {
            throw new IllegalArgumentException(
                "Parent is "+parentSize.getSize()+"-dimensional, " +
                "but fromIndices is "+fromIndices.getSize()+"-dimensional");
        }
        if (toIndices.getSize() != parentSize.getSize())
        {
            throw new IllegalArgumentException(
                "Parent is "+parentSize.getSize()+"-dimensional, " +
                "but toIndices is "+toIndices.getSize()+"-dimensional");
        }
        int n = parentSize.getSize();
        for (int i=0; i<n; i++)
        {
            int p = parentSize.get(i);
            int f = fromIndices.get(i);
            int t = toIndices.get(i);
            if (f < 0 || t > p || f > t)
            {
                throw new IllegalArgumentException(
                    "Invalid index range: "+fromIndices+
                    " to "+toIndices+" in "+parentSize);
            }
        }
    }
    
    
    
    /**
     * Returns the number of entries of the given tuples that are
     * different
     *  
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return The number of differences
     * @throws NullPointerException If any of the arguments is <code>null</code>
     * @throws IllegalArgumentException If the given tuples do not have
     * the same {@link IntTuple#getSize() size}
     */
    public static int countDifferences(IntTuple t0, IntTuple t1)
    {
        if (t0.getSize() != t1.getSize())
        {
            throw new IllegalArgumentException(
                "Sizes do not match: "+t0.getSize()+
                " and "+t1.getSize());
        }
        int n = t0.getSize();
        int differences = 0;
        for (int i=0; i<n; i++)
        {
            if (t0.get(i) != t1.get(i))
            {
                differences++;
            }
        }
        return differences;
    }

}
