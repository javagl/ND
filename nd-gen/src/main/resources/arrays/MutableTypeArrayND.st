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

import de.javagl.nd.tuples.i.IntTuple;

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Interface describing a mutable multidimensional
 * array of <code>$type.name$</code> values.
 */
public interface Mutable$type.uppercaseName$ArrayND extends $type.uppercaseName$ArrayND
{
    /**
     * Set the value at the given position.
     *
     * @param indices The position
     * @param value The value to set.
     * @throws IllegalArgumentException If the given indices do not have 
     * the same {@link IntTuple#getSize() size} as the {@link #getSize()
     * size} of this array. 
     * @throws IndexOutOfBoundsException May be thrown if the indices are
     * not valid. That is, if <tt>fromIndex &lt; 0</tt> or
     * <tt>toIndex &gt; size</tt> or <tt>fromIndex &gt; toIndex</tt>
     * for any dimension. But explicit bounds checking is not guaranteed.
     */
    void set(IntTuple indices, $type.name$ value);

    /**
     * {@inheritDoc}
     * Changes in this array will be visible in the returned array.
     * Changes in the returned array will be visible in this array.
     */
    @Override
    Mutable$type.uppercaseName$ArrayND subArray(
        IntTuple fromIndices, IntTuple toIndices);

}
