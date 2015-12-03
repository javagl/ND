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
package de.javagl.nd.iteration.tuples.i;

import java.util.Iterator;
import java.util.NoSuchElementException;

import de.javagl.nd.tuples.Utils;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;

/**
 * An iterator over {@link MutableIntTuple}s
 */
final class IntTupleIterator implements Iterator<MutableIntTuple>
{
	/**
	 * The current value returned by this iterator
	 */
    private MutableIntTuple current;
    
    /**
     * The minimum values for this iterator, inclusive
     */
    private final IntTuple min;

    /**
     * The maximum values for this iterator, exclusive
     */
    private final IntTuple max;
    
    /**
     * The {@link IntTupleIncrementor} to use 
     */
    private final IntTupleIncrementor intTupleIncrementor;
    
    /**
     * Creates a new iterator, which iterates between the given limits.<br>
     * <br>
     * NOTE: This constructor will store REFERENCES to the given 
     * limits. They may NOT be modified while the iteration is
     * in progress.
     * 
     * @param min The minimum values
     * @param max The maximum values
     * @param intTupleIncrementor The {@link IntTupleIncrementor} to use
     */
    IntTupleIterator(IntTuple min, IntTuple max,
        IntTupleIncrementor intTupleIncrementor)
    {
        Utils.checkForEqualSize(min, max);
        this.min = min;
        this.max = max;
        this.intTupleIncrementor = intTupleIncrementor;
        if (IntTuples.areElementsGreaterThan(max, min))
        {
            current = IntTuples.copy(min);
        }
    }
    
    @Override
    public boolean hasNext()
    {
        return current != null;
    }
    
    @Override
    public MutableIntTuple next()
    {
        if (current == null)
        {
            throw new NoSuchElementException("No more elements");
        }
        MutableIntTuple result = IntTuples.copy(current);
        boolean hasNext = intTupleIncrementor.increment(current, min, max);
        if (!hasNext)
        {
            current = null;
        }
        return result;
    }
    
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException(
            "May not remove elements with this iterator");
    }
}