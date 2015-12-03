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
package de.javagl.nd.distance.tuples.i;

/**
 * Mathematical utility methods.
 */
class MathUtils
{
    /**
     * Wrap the given value to be in [0,|max|)
     * 
     * @param value The value
     * @param max The maximum value
     * @return The wrapped value
     * @throws ArithmeticException If the given maximum value is 0
     */
    static int wrap(int value, int max)
    {
        int result = value % max;
        if (result < 0) 
        {
            return result + (max > 0 ? max : - max);        
        }
        return result;
    }
    
    
    /**
     * Computes the distance (absolute difference) between the given values
     * when they are interpreted as points on a circle with the given 
     * size (that is, circumference). <br> 
     * <br>
     * E.g. <br>
     * <pre><code>
     * wrappedDistance(0,  9, 10) = 1
     * wrappedDistance(0, 10, 10) = 0
     * wrappedDistance(0, 11, 10) = 1
     * wrappedDistance(1, -4, 10) = 5
     * </code></pre>
     *  
     * @param i0 The first value
     * @param i1 The second value
     * @param size The wrapping size
     * @return The wrapped distance
     */
    static int wrappedDistance(int i0, int i1, int size)
    {
        int w0 = wrap(i0, size);
        int w1 = wrap(i1, size);
        int d = Math.abs(w1-w0);
        return Math.min(d, size-d);
    }
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private MathUtils()
    {
        // Private constructor to prevent instantiation
    }
    
    

}
