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

import java.util.Objects;

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Default implementation of a {@link MutableDoubleTuple} 
 */
class DefaultDoubleTuple extends AbstractMutableDoubleTuple 
{
    /**
     * The data of this tuple
     */
    private final double data[];

    /**
     * Creates a new tuple with the given size.
     *
     * @param size The size
     * @throws IllegalArgumentException If the given size is negative
     */
    DefaultDoubleTuple(int size)
    {
        if (size < 0)
        {
            throw new IllegalArgumentException("The size is negative: "+size);
        }
        data = new double[size];
    }

    /**
     * Creates a new tuple with the given data.
     * A <strong>reference</strong> to the given
     * data will be stored!
     *
     * @param data The data for this tuple
     * @throws NullPointerException If the given array is <code>null</code>
     */
    DefaultDoubleTuple(double ... data)
    {
        Objects.requireNonNull(data, "The data is null");
        this.data = data;
    }

    /**
     * Copy constructor.
     *
     * @param other The other tuple.
     * @throws NullPointerException If the other tuple is <code>null</code>
     */
    DefaultDoubleTuple(DoubleTuple other)
    {
        this.data = new double[other.getSize()];
        for (int i=0; i<data.length; i++)
        {
            data[i] = other.get(i);
        }
    }

    @Override
    public int getSize()
    {
        return data.length;
    }

    @Override
    public double get(int index)
    {
        return data[index];
    }

    @Override
    public void set(int index, double value)
    {
        data[index] = value;
    }


}
