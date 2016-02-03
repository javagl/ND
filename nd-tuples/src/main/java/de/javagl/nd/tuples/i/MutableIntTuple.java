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

import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;

/**
 * Interface describing an N-dimensional mutable tuple of
 * <code>int</code> values.
 */
public interface MutableIntTuple extends IntTuple
{
    /**
     * Set the value at the given index.
     *
     * @param index The index
     * @param value The value
     * @throws IndexOutOfBoundsException If the given index
     * is smaller than 0, or greater than or equal to the
     * {@link #getSize() size} of this tuple.
     */
    void set(int index, int value);

    /**
     * Set this tuple to be equal to the given one.
     *
     * @param other The other tuple
     * @throws IllegalArgumentException If the given tuple does not 
     * have the same {@link Tuple#getSize() size}
     */
    default void set(IntTuple other)
    {
        Utils.checkForEqualSize(this, other);
        for (int i=0; i<other.getSize(); i++)
        {
            set(i, other.get(i));
        }
    }

    /**
     * {@inheritDoc}
     * Changes in the returned tuple will be visible in this tuple.
     */
    @Override
    default MutableIntTuple subTuple(int fromIndex, int toIndex)
    {
        return IntTuples.createSubTuple(
            this, fromIndex, toIndex);
    }

}
