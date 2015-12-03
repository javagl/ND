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

import java.util.Arrays;
import java.util.stream.DoubleStream;

import de.javagl.nd.tuples.Utils;

/**
 * Implementation of a {@link MutableDoubleTuple} that is backed by 
 * a part of a <code>double[]</code> array.
 */
final class ArrayDoubleTuple extends AbstractMutableDoubleTuple
{
    /**
     * The data of this tuple
     */
    private final double data[];

    /**
     * The offset of this tuple in the given data array
     */
    private final int offset;

    /**
     * The size of this tuple
     */
    private final int size;

    /**
     * Creates a new tuple with the given data.
     * A <strong>reference</strong> to the given
     * data will be stored!
     *
     * @param data The data for this tuple
     * @param offset The offset in the given array
     * @param size The size
     * @throws NullPointerException If the given array is <code>null</code>
     * @throws IllegalArgumentException If the given offset or size is 
     * negative, or if <code>offset+size &gt;= data.length</code>
     */
    ArrayDoubleTuple(double data[], int offset, int size)
    {
        if (offset < 0)
        {
            throw new IllegalArgumentException(
                "The offset is negative: "+offset);
        }
        if (size < 0)
        {
            throw new IllegalArgumentException(
                "The size is negative: "+size);
        }
        if (offset + size > data.length)
        {
            throw new IllegalArgumentException(
                "The offset is " + offset + " and size is " + size + ". " +
                "The array length must be at least " + (offset + size) + 
                ", but only is " + data.length);
        }
        this.data = data;
        this.offset = offset;
        this.size = size;
    }


    @Override
    public int getSize()
    {
        return size;
    }

    @Override
    public double get(int index)
    {
        Utils.checkForValidIndex(index, size);
        return data[index+offset];
    }

    @Override
    public void set(int index, double value)
    {
        Utils.checkForValidIndex(index, size);
        data[index+offset] = value;
    }

    @Override
    public MutableDoubleTuple subTuple(int fromIndex, int toIndex)
    {
        return new ArrayDoubleTuple(data, offset+fromIndex, toIndex-fromIndex);
    }

    @Override
    public DoubleStream stream()
    {
        return Arrays.stream(data);
    }
}
