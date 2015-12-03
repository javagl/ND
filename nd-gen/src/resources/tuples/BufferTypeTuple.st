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
package de.javagl.nd.tuples.$type.letter$;

import java.nio.$type.uppercaseName$Buffer;
import java.util.Objects;

import de.javagl.nd.tuples.Utils;

/**
 * Implementation of a {@link Mutable$type.uppercaseName$Tuple} that is backed by 
 * a <code>$type.uppercaseName$Buffer</code>
 */
final class Buffer$type.uppercaseName$Tuple extends AbstractMutable$type.uppercaseName$Tuple
{
    /**
     * The data of this tuple
     */
    private final $type.uppercaseName$Buffer buffer;

    /**
     * Creates a new tuple with the given buffer. The {@link #getSize()} of
     * the tuple will be the capacity of the given buffer. In order to 
     * create a view on a part of a buffer the {@link $type.uppercaseName$Buffer#slice()}
     * method may be used.<br>
     * <br> 
     * A <strong>reference</strong> to the given buffer will be stored!
     *
     * @param buffer The buffer for this tuple
     * @throws NullPointerException If the given array is <code>null</code>
     */
    Buffer$type.uppercaseName$Tuple($type.uppercaseName$Buffer buffer)
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
    public $type.name$ get(int index)
    {
        return buffer.get(index);
    }

    @Override
    public void set(int index, $type.name$ value)
    {
        buffer.put(index, value);
    }

    @Override
    public Mutable$type.uppercaseName$Tuple subTuple(int fromIndex, int toIndex)
    {
        Utils.checkForValidSubTupleIndices(getSize(), fromIndex, toIndex);
        int oldPosition = buffer.position();
        int oldLimit = buffer.limit();
        buffer.position(fromIndex);
        buffer.limit(oldPosition + (toIndex - fromIndex));
        Mutable$type.uppercaseName$Tuple result = new Buffer$type.uppercaseName$Tuple(buffer.slice());
        buffer.position(oldPosition);
        buffer.limit(oldLimit);
        return result;
    }
}
