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

import java.util.Comparator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTupleFunctions;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;

/**
 * A Spliterator for a stream of {@link MutableIntTuple} instances that are
 * iterated through in lexicographical or colexicographical order.
 */
class IntTupleRangeSpliterator implements Spliterator<MutableIntTuple>
{
    /**
     * The minimum tuple provided by this spliterator, inclusive
     */
    private final IntTuple min;

    /**
     * The current tuple
     */
    private final MutableIntTuple current;

    /**
     * The maximum tuple provided by this spliterator, exclusive
     */
    private final MutableIntTuple max;

    /**
     * The Characteristics of this Spliterator
     */
    private final int characteristics;

    /**
     * The {@link IntTupleIncrementor} that will increment the current tuple
     */
    private IntTupleIncrementor incrementor;
    
    /**
     * A comparator for the {@link IntTuple}s
     */
    private Comparator<IntTuple> comparator;
    
    /**
     * Creates a spliterator covering the specified range. References to
     * the given tuples will be stored and used internally.
     * @param order The {@link Order} for the spliterator
     * @param min The minimum tuple, inclusive
     * @param max The maximum tuple, exclusive 
     * 
     * @throws IllegalArgumentException If the given minimum is greater
     * than the maximum
     */
    IntTupleRangeSpliterator( 
        Order order, IntTuple min, MutableIntTuple max)
    {
        this(min, max,
            IntTupleIncrementors.incrementor(order),
            IntTuples.comparator(order)); 
    }

    /**
     * Creates a spliterator covering the specified range. References to
     * the given tuples will be stored and used internally.
     * 
     * @param min The minimum tuple, inclusive
     * @param max The maximum tuple, exclusive 
     * @param incrementor The {@link IntTupleIncrementor}
     * @param comparator The comparator matching the {@link IntTupleIncrementor}
     * @throws IllegalArgumentException If the given minimum is greater
     * than the maximum
     */
    IntTupleRangeSpliterator( 
        IntTuple min, MutableIntTuple max, 
        IntTupleIncrementor incrementor, Comparator<IntTuple> comparator)
    {
        this.min = min;
        this.current = IntTuples.copy(min);
        this.max = max;
        this.incrementor = incrementor;
        this.comparator = comparator;
        if (comparator.compare(min, max) > 0)
        {
            throw new IllegalArgumentException(
                "Invalid range: min=" + min + ", max=" + max);
        }
        this.characteristics =
            Spliterator.SORTED | 
            Spliterator.IMMUTABLE |
            Spliterator.DISTINCT |
            Spliterator.SIZED | 
            Spliterator.SUBSIZED;
    }
    

    @Override
    public IntTupleRangeSpliterator trySplit()
    {
        int n = min.getSize();
        int largestDiff = -1;
        int largestDiffIndex = -1;
        for (int i = 0; i < n; i++)
        {
            int diff = max.get(i) - min.get(i);
            if (diff > largestDiff)
            {
                largestDiff = diff;
                largestDiffIndex = i;
            }
        }
        if (largestDiff <= 1)
        {
            return null;
        }
        int mid =  min.get(largestDiffIndex) + (largestDiff >>> 1);

        MutableIntTuple thatBegin = IntTuples.copy(min);
        thatBegin.set(largestDiffIndex, mid);
        MutableIntTuple thatEnd = IntTuples.copy(max);

        max.set(largestDiffIndex, mid);
        
        IntTupleRangeSpliterator result = 
            new IntTupleRangeSpliterator(
                thatBegin, thatEnd, incrementor, comparator);
        return result;
    }

    @Override
    public void forEachRemaining(Consumer<? super MutableIntTuple> action)
    {
        Objects.requireNonNull(action, "The action is null");
        if (comparator.compare(current, max) < 0)
        {
            while (true)
            {
                action.accept(IntTuples.copy(current));
                boolean couldIncrement = 
                    incrementor.increment(current, min, max);
                if (!couldIncrement)
                {
                    break;
                }
            }
        }
    }

    @Override
    public boolean tryAdvance(Consumer<? super MutableIntTuple> action)
    {
        Objects.requireNonNull(action, "The action is null");
        if (comparator.compare(current, max) < 0)
        {
            action.accept(IntTuples.copy(current));
            boolean couldIncrement = incrementor.increment(current, min, max);
            if (!couldIncrement)
            {
                current.set(max);
            }
            return true;
        }
        return false;
    }

    @Override
    public long estimateSize()
    {
        MutableIntTuple difference = IntTuples.subtract(max, min, null);
        int size = IntTupleFunctions.reduce(difference, 1, (a,b) -> (a*b));
        return size;
    }

    @Override
    public int characteristics()
    {
        return characteristics;
    }
    
    @Override
    public Comparator<? super IntTuple> getComparator()
    {
        return comparator;
    }
}