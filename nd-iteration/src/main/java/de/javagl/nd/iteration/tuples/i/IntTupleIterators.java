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

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;

/**
 * Methods to create iterators over ranges of {@link IntTuple}s
 */
public class IntTupleIterators 
{
    /**
     * Returns an iterator that returns {@link MutableIntTuple}s in the
     * given range, incremented in the given {@link Order}. If the given 
     * {@link Order} is <code>null</code>, then <code>null</code> will 
     * be returned.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     * 
     * @param order The {@link Order}
     * @param min The minimum values, inclusive
     * @param max The maximum values, exclusive
     * @return The iterator
     * 
     * @see #lexicographicalIterator(IntTuple, IntTuple)
     * @see #colexicographicalIterator(IntTuple, IntTuple)
     */
    public static Iterator<MutableIntTuple> iterator(
        Order order, IntTuple min, IntTuple max)
    {
        if (order == null)
        {
            return null;
        }
        IntTuple localMin = IntTuples.copy(min);
        IntTuple localMax = IntTuples.copy(max);
        return new IntTupleIterator(localMin, localMax, 
            IntTupleIncrementors.incrementor(order));
    }
    
    
    /**
     * Returns an iterator that returns {@link MutableIntTuple}s in the
     * given range.<br>
     * <br> 
     * Copies of the given tuples will be stored internally.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     *   
     * @param min The minimum values, inclusive
     * @param max The maximum values, exclusive
     * @return The iterator
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    public static Iterator<MutableIntTuple> lexicographicalIterator(
        IntTuple min, IntTuple max)
    {
        Utils.checkForEqualSize(min, max);
        IntTuple localMin = IntTuples.copy(min);
        IntTuple localMax = IntTuples.copy(max);
        return new IntTupleIterator(localMin, localMax, 
            IntTupleIncrementors.lexicographicalIncrementor());
    }
    
    

    
    
    
    /**
     * Returns an iterator that returns {@link MutableIntTuple}s in the
     * given range. <br>
     * <br>
     * Copies of the given tuples will be stored internally.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     *   
     * @param min The minimum values, inclusive
     * @param max The maximum values, exclusive
     * @return The iterator
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    public static Iterator<MutableIntTuple> colexicographicalIterator(
        IntTuple min, IntTuple max)
    {
        Utils.checkForEqualSize(min, max);
        IntTuple localMin = IntTuples.copy(min);
        IntTuple localMax = IntTuples.copy(max);
        return new IntTupleIterator(localMin, localMax, 
            IntTupleIncrementors.colexicographicalIncrementor());
    }

    
    
    
    /**
     * Returns an iterator that returns the {@link MutableIntTuple}s from the
     * given delegate, wrapped at the given bounds.<br>   
     * 
     * @param bounds The bounds. A copy of this tuple will be stored internally.
     * @param delegate The delegate iterator
     * @return The iterator
     */
    static Iterator<MutableIntTuple> wrappingIterator(
        IntTuple bounds, Iterator<? extends MutableIntTuple> delegate)
    {
        return wrappingIteratorInternal(IntTuples.copy(bounds), delegate);
    }
    
    
    /**
     * Returns an iterator that returns the {@link MutableIntTuple}s from the
     * given delegate, wrapped at the given bounds.<br>   
     * <br>
     * NOTE: The iterator will store REFERENCES to the given bounds. 
     * They may NOT be modified while the iteration is in progress.
     * 
     * @param bounds The bounds
     * @param delegate The delegate iterator
     * @return The iterator
     */
    static Iterator<MutableIntTuple> wrappingIteratorInternal(
        IntTuple bounds, Iterator<? extends MutableIntTuple> delegate)
    {
        return new Iterator<MutableIntTuple>()
        {
            @Override
            public boolean hasNext()
            {
                return delegate.hasNext();
            }

            @Override
            public MutableIntTuple next()
            {
                return IntTupleUtils.wrap(delegate.next(), bounds);
            }
            
            @Override
            public void remove()
            {
                delegate.remove();
            }
        };
    }
    
    
    /**
     * Returns an iterator that returns the {@link MutableIntTuple}s from the
     * given delegate that are contained in the given bounds.<br>   
     * 
     * @param min The minimum, inclusive. A copy of this tuple will be 
     * stored internally.
     * @param max The maximum, exclusive. A copy of this tuple will be 
     * stored internally.
     * @param delegate The delegate iterator
     * @return The iterator
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    public static Iterator<MutableIntTuple> clampingIterator(
        IntTuple min, IntTuple max, 
        Iterator<? extends MutableIntTuple> delegate)
    {
        Utils.checkForEqualSize(min, max);
        IntTuple localMin = IntTuples.copy(min);
        IntTuple localMax = IntTuples.copy(max);
        return clampingIteratorInternal(localMin, localMax, delegate);
    }
    
    
    /**
     * Returns an iterator that returns the {@link MutableIntTuple}s from the
     * given delegate that are contained in the given bounds.<br>   
     * <br>
     * NOTE: The iterator will store REFERENCES to the given bounds. 
     * They may NOT be modified while the iteration is in progress.
     * 
     * @param min The minimum, inclusive
     * @param max The maximum, exclusive
     * @param delegate The delegate iterator
     * @return The iterator
     */
    static Iterator<MutableIntTuple> clampingIteratorInternal(
        IntTuple min, IntTuple max, 
        Iterator<? extends MutableIntTuple> delegate)
    {
        return new FilteringIterator<MutableIntTuple>(delegate, 
            t -> IntTuples.areElementsGreaterThanOrEqual(t, min) &&
                 IntTuples.areElementsLessThan(t, max));
    }
    
    
    
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private IntTupleIterators()
    {
        // Private constructor to prevent instantiation
    }


}

