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
package de.javagl.nd.arrays.$type.letter$;

import de.javagl.nd.arrays.AbstractArrayND;
import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.i.IntTuple;

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Abstract base implementation of a {@link Mutable$type.uppercaseName$ArrayND}.<br>
 * <br>
 * This class implements the {@link #toString()}, {@link #equals(Object)},
 * {@link #hashCode()} and {@link #subArray(IntTuple, IntTuple)} methods 
 * canonically.<br>
 */
abstract class AbstractMutable$type.uppercaseName$ArrayND
    extends AbstractArrayND
    implements Mutable$type.uppercaseName$ArrayND
{
    /**
     * Create a new array with the given size.
     *
     * @param size The size
     * @param preferredIterationOrder The preferred iteration {@link Order}
     * of this array. This may be <code>null</code> if the array does not
     * have a preferred iteration order.
     * @throws NullPointerException If the given size is <code>null</code>
     * @throws IllegalArgumentException If the given size is negative
     * along any dimension
     */
    AbstractMutable$type.uppercaseName$ArrayND(IntTuple size, Order preferredIterationOrder)
    {
        super(size, preferredIterationOrder);
    }

    @Override
    public Mutable$type.uppercaseName$ArrayND subArray(
        IntTuple fromIndices, IntTuple toIndices)
    {
        return $type.uppercaseName$ArraysND.createSubArray(this, fromIndices, toIndices);
    }

    @Override
    public String toString()
    {
        return $type.uppercaseName$ArraysND.toString(this);
    }

    @Override
    public int hashCode()
    {
        return $type.uppercaseName$ArraysND.hashCode(this);
    }

    @Override
    public boolean equals(Object object)
    {
        return $type.uppercaseName$ArraysND.equals(this, object);
    }


}
