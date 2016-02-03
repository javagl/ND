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
package de.javagl.nd.tuples.i;

import java.util.stream.IntStream;

import de.javagl.nd.tuples.Tuple;

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Interface describing an N-dimensional tuple consisting
 * of <code>int</code> values.
 */
public interface IntTuple extends Tuple
{
    /**
     * Returns the value at the given index of this tuple.
     *
     * @param index The index.
     * @return The value.
     * @throws IndexOutOfBoundsException If the given index
     * is smaller than 0, or greater than or equal to the
     * {@link #getSize() size} of this tuple.
     */
    int get(int index);

    @Override
    default IntTuple subTuple(int fromIndex, int toIndex)
    {
        return IntTuples.createSubTuple(
            this, fromIndex, toIndex - fromIndex);
    }

    /**
     * Returns a sequential {@code IntStream} with this tuple as its source.
     * 
     * @return The stream
     */
    default IntStream stream()
    {
        return IntTupleStreams.stream(this, 0, getSize());
    }

    /**
     * Returns whether this tuple equals the given object. This will
     * return <code>true</code> if and only if the given object is also
     * a <code>IntTuple</code>, has the same {@link #getSize() size}
     * as this one, and is component-wise equal to this one.
     * 
     * @param object The object to compare with
     * @return Whether this tuple equals the given object
     */
    @Override
    public boolean equals(Object object);

    /**
     * {@inheritDoc}
     * 
     * This hash code is defined to be the sum of the hash codes of
     * all the elements of this tuple.
     * 
     * @return The hash code
     */
    @Override
    public int hashCode();

}
