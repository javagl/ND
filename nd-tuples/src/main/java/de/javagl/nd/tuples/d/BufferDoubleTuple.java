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
package de.javagl.nd.tuples.d;

import java.nio.DoubleBuffer;
import java.util.Objects;

import de.javagl.nd.tuples.Utils;

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Implementation of a {@link MutableDoubleTuple} that is backed by 
 * a <code>DoubleBuffer</code>
 */
final class BufferDoubleTuple extends AbstractMutableDoubleTuple
{
    /**
     * The data of this tuple
     */
    private final DoubleBuffer buffer;

    /**
     * Creates a new tuple with the given buffer. The {@link #getSize()} of
     * the tuple will be the capacity of the given buffer. In order to 
     * create a view on a part of a buffer the {@link DoubleBuffer#slice()}
     * method may be used.<br>
     * <br> 
     * A <strong>reference</strong> to the given buffer will be stored!
     *
     * @param buffer The buffer for this tuple
     * @throws NullPointerException If the given array is <code>null</code>
     */
    BufferDoubleTuple(DoubleBuffer buffer)
    {
        Objects.requireNonNull(buffer, "The buffer is null");
        this.buffer = buffer;
    }

    @Override
    public int getSize()
    {
        return buffer.capacity();
    }

    @Override
    public double get(int index)
    {
        return buffer.get(index);
    }

    @Override
    public void set(int index, double value)
    {
        buffer.put(index, value);
    }

    @Override
    public MutableDoubleTuple subTuple(int fromIndex, int toIndex)
    {
        Utils.checkForValidSubTupleIndices(getSize(), fromIndex, toIndex);
        int oldPosition = buffer.position();
        int oldLimit = buffer.limit();
        buffer.position(fromIndex);
        buffer.limit(oldPosition + (toIndex - fromIndex));
        MutableDoubleTuple result = new BufferDoubleTuple(buffer.slice());
        buffer.position(oldPosition);
        buffer.limit(oldLimit);
        return result;
    }
}
