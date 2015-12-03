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

import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.MutableIntTuple;

/**
 * Internal utility methods
 */
class IntTupleUtils
{
    /**
     * Wrap the given tuple in-place, obeying the given bounds, yielding
     * a torus topology.
     * 
     * @param tuple The tuple
     * @param bounds The bounds
     * @return The given tuple
     */
    static MutableIntTuple wrap(MutableIntTuple tuple, IntTuple bounds)
    {
        if (tuple.getSize() != bounds.getSize())
        {
            throw new IllegalArgumentException(
                "A tuple with size " + tuple.getSize() + 
                " can not be wrapped at bounds of size " + bounds.getSize());
        }
        for (int i=0; i<tuple.getSize(); i++)
        {
            int coordinate = tuple.get(i);
            int wrappedCoordinate = wrap(coordinate, bounds.get(i));
            tuple.set(i, wrappedCoordinate);
        }
        return tuple;
    }
    
    /**
     * Wrap the given value to be in [0,|max|)
     * 
     * @param value The value
     * @param max The maximum value
     * @return The wrapped value
     * @throws ArithmeticException If the given maximum value is 0
     */
    private static int wrap(int value, int max)
    {
        int result = value % max;
        if (result < 0) 
        {
            return result + (max > 0 ? max : - max);        
        }
        return result;
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private IntTupleUtils()
    {
        // Private constructor to prevent instantiation
    }
}
