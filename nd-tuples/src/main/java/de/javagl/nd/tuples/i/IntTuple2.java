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
 * Implementation of a {@link MutableIntTuple} with a 
 * {@link Tuple#getSize() size} of 2.
 */
class IntTuple2 implements MutableIntTuple
{
    /**
     * The data of this tuple
     */
    private int x;

    /**
     * The data of this tuple
     */
    private int y;
    
    /**
     * The cached hash code
     */
    private int hashCode = -1;

    /**
     * Creates a new tuple with the given values
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    IntTuple2(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getSize()
    {
        return 2;
    }

    @Override
    public int get(int index)
    {
        if (index == 0)
        {
            return x;
        }
        else if (index == 1)
        {
            return y;
        }
        else 
        {
            throw new IndexOutOfBoundsException(
                "Index must be 0 or 1, but is "+index);
        }
    }

    @Override
    public void set(int index, int value)
    {
        if (index == 0)
        {
            x = value;
        }
        else if (index == 1)
        {
            y = value;
        }
        else
        {
            throw new IndexOutOfBoundsException(
                "Index must be 0 or 1, but is "+index);
        }
        hashCode = -1;
    }

    @Override
    public void set(IntTuple other)
    {
        Utils.checkForEqualSize(this, other);
        this.x = other.get(0);
        this.y = other.get(1);
        hashCode = -1;
    }

    @Override
    public String toString()
    {
        return IntTuples.toString(this);
    }

    @Override
    public int hashCode()
    {
        if (hashCode == -1)
        {
            hashCode = IntTuples.hashCode(this);
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object object)
    {
        return IntTuples.equals(this, object);
    }

    @Override
    public MutableIntTuple subTuple(int fromIndex, int toIndex)
    {
        if (fromIndex == 0 && toIndex == 2)
        {
            return this;
        }
        return IntTuples.createSubTuple(
            this, fromIndex, toIndex);
    }

}
