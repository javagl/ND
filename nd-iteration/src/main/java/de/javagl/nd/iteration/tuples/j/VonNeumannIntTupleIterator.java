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
package de.javagl.nd.iteration.tuples.j;

import java.util.Iterator;
import java.util.NoSuchElementException;

import de.javagl.nd.tuples.j.LongTuple;
import de.javagl.nd.tuples.j.LongTuples;
import de.javagl.nd.tuples.j.MutableLongTuple;

/**
 * An iterator over the Von Neumannn neighborhood of a {@link LongTuple}
 */
class VonNeumannLongTupleIterator implements Iterator<MutableLongTuple>
{
    // TODO This implementation is rather confusing and not
    // very elegant (and not efficient). Improve this!
    
    /**
     * The center point of the iteration
     */
    private final LongTuple center;

    /**
     * The radius
     */
    private final long radius;

    /**
     * The current value returned by this iterator
     */
    private MutableLongTuple current;

    /**
     * The current minimum values
     */
    private final MutableLongTuple min;

    /**
     * The current maximum values
     */
    private final MutableLongTuple max;
    
    /**
     * Creates a new iterator around the given center, with the
     * given radius. A copy of the given tuple will be stored.
     * 
     * @param center The center
     * @param radius The radius
     */
    VonNeumannLongTupleIterator(LongTuple center, long radius)
    {
        this.min = LongTuples.create(center.getSize());
        this.min.set(0, -radius);
        this.max = LongTuples.create(center.getSize());
        this.max.set(0,  radius);
        this.current = LongTuples.create(center.getSize());
        this.current.set(0, -radius);
        
        this.center = LongTuples.copy(center);
        this.radius = radius;
    }
    
    @Override
    public boolean hasNext()
    {
        return current != null;
    }
    
    @Override
    public MutableLongTuple next()
    {
        if (current == null)
        {
            throw new NoSuchElementException("No more elements");
        }
        MutableLongTuple result = LongTuples.add(current, center, null);
        boolean hasNext = increment();
        if (!hasNext)
        {
            current = null;
        }
        return result;
    }
    
    /**
     * Tries to increment the current value and returns 
     * whether this was possible.
     * 
     * @return Whether the value could be incremented.
     */
    private boolean increment()
    {
        return increment(current.getSize()-1);
    }
    
    /**
     * Increment the current value starting at the given index,
     * and return whether this was possible
     * 
     * @param index The index
     * @return Whether the value could be incremented
     */
    private boolean increment(int index)
    {
        if (index == -1)
        {
            return false;
        }
        long value = current.get(index);
        if (value < max.get(index))
        {
            current.set(index, value+1);
            updateMinMax(index+1);
            return true;
        }
        boolean hasNext = increment(index-1);
        if (hasNext)
        {
            current.set(index, min.get(index));            
        }
        return hasNext;
    }

    /**
     * Update the minimum/maximum value based on the current 
     * tuple, for the given index
     * 
     * @param index The index
     */
    private void updateMinMax(int index)
    {
        if (index >= min.getSize())
        {
            return;
        }
        long sum = 0;
        for (int i=0; i<index; i++)
        {
            sum += Math.abs(current.get(i));
        }
        long m = radius - sum;
        min.set(index, -m);
        max.set(index, m);
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException(
            "May not remove elements with this iterator");
    }
}