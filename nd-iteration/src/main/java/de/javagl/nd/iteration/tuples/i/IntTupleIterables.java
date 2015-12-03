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

import java.util.Objects;

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;

/**
 * Methods to create iterables over ranges of {@link IntTuple}s.<br>
 * <br>
 * Unless otherwise noted, none of the arguments for these methods
 * may be <code>null</code>.
 */
public class IntTupleIterables 
{
    /**
     * Returns an iterable returning an iterator that returns 
     * {@link MutableIntTuple}s up to the given maximum values, in 
     * the specified {@link Order}. If the given {@link Order} is 
     * <code>null</code>, then <code>null</code> will be returned. <br>
     * <br>
     * Copies of the given tuples will be stored internally.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     * 
     * @param order The iteration {@link Order}
     * @param min The minimum values, inclusive
     * @param max The maximum values, exclusive
     * @return The iterable
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    public static Iterable<MutableIntTuple> iterable(
        Order order, IntTuple min, IntTuple max)
    {
        if (order == null)
        {
            return null;
        }
        Utils.checkForEqualSize(min, max);
        IntTuple localMin = IntTuples.copy(min);
        IntTuple localMax = IntTuples.copy(max);
        return () -> new IntTupleIterator(localMin, localMax, 
            IntTupleIncrementors.incrementor(order));
    }
    
    
    /**
     * Returns an iterable returning an iterator that returns 
     * {@link MutableIntTuple}s up to the given maximum values, in 
     * lexicographical order.<br>
     * <br>
     * A copy of the given tuple will be stored internally.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     *   
     * @param max The maximum values, exclusive
     * @return The iterable
     */
    public static Iterable<MutableIntTuple> lexicographicalIterable(
        IntTuple max)
    {
        return iterable(Order.LEXICOGRAPHICAL, 
            IntTuples.zero(max.getSize()), max);
    }

    /**
     * Returns an iterable returning an iterator that returns 
     * {@link MutableIntTuple}s in the specified range, in 
     * lexicographical order.<br> 
     * <br>
     * Copies of the given tuples will be stored internally.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     *   
     * @param min The minimum values, inclusive
     * @param max The maximum values, exclusive
     * @return The iterable
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    public static Iterable<MutableIntTuple> lexicographicalIterable(
        IntTuple min, IntTuple max)
    {
        return iterable(Order.LEXICOGRAPHICAL, 
            min, max);
    }

    /**
     * Returns an iterable returning an iterator that returns 
     * {@link MutableIntTuple}s up to the given maximum values, 
     * in colexicographical order.<br>
     * <br>
     * Copies of the given tuples will be stored internally.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     *   
     * @param max The maximum values, exclusive
     * @return The iterable
     */
    public static Iterable<MutableIntTuple> colexicographicalIterable(
        IntTuple max)
    {
        return iterable(Order.COLEXICOGRAPHICAL, 
            IntTuples.zero(max.getSize()), max);
    }

    /**
     * Returns an iterable returning an iterator that returns 
     * {@link MutableIntTuple}s in the specified range, in colexicographical 
     * order.<br>
     * <br>
     * Copies of the given tuples will be stored internally.<br>
     * <br>
     * Also see <a href="../../package-summary.html#IterationOrder">
     * Iteration Order</a>
     *   
     * @param min The minimum values, inclusive
     * @param max The maximum values, exclusive
     * @return The iterable
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    public static Iterable<MutableIntTuple> colexicographicalIterable(
        IntTuple min, IntTuple max)
    {
        return iterable(Order.COLEXICOGRAPHICAL, 
            min, max);
    }

    /**
     * Creates an iterable from the given delegate that creates iterators
     * that return the {@link IntTuple}s created by the iterators of the
     * given delegate, wrapping at the given bounds.<br>
     * <br>
     * <b>Note:</b> The caller is responsible for making sure that 
     * the tuples returned by the given delegate have the same 
     * {@link Tuple#getSize()} as the given bounds. This method can 
     * not check this internally. If the sizes are different, the
     * behavior of the returned iterable is unspecified.   
     * 
     * @param bounds The bounds. A copy of this tuple will be stored internally.
     * @param delegate The delegate
     * @return The iterable
     */
    public static Iterable<MutableIntTuple> wrappingIterable(
        IntTuple bounds, final Iterable<? extends MutableIntTuple> delegate)
    {
        Objects.requireNonNull(delegate, "The delegate is null");
        IntTuple localBounds = IntTuples.copy(bounds);
        return () -> IntTupleIterators.wrappingIteratorInternal(
            localBounds, delegate.iterator());
    }

    /**
     * Creates an iterable from the given delegate that creates iterators
     * that return the {@link MutableIntTuple}s created by the iterators of 
     * the given delegate that are contained in the given bounds.<br>
     * <br>
     * <b>Note:</b> The caller is responsible for making sure that 
     * the tuples returned by the given delegate have the same 
     * {@link Tuple#getSize()} as the given bounds. This method can 
     * not check this internally. If the sizes are different, the
     * behavior of the returned iterable is unspecified.
     * 
     * @param min The minimum, inclusive. A copy of this tuple will be 
     * stored internally.
     * @param max The maximum, exclusive. A copy of this tuple will be 
     * stored internally.
     * @param delegate The delegate
     * @return The iterable
     */
    public static Iterable<MutableIntTuple> clampingIterable(
        IntTuple min, IntTuple max, 
        final Iterable<? extends MutableIntTuple> delegate)
    {
        Objects.requireNonNull(delegate, "The delegate is null");
        IntTuple localMin = IntTuples.copy(min);
        IntTuple localMax = IntTuples.copy(max);
        return () -> IntTupleIterators.clampingIteratorInternal(
            localMin, localMax, delegate.iterator());
    }

    /**
     * Private constructor to prevent instantiation
     */
    private IntTupleIterables()
    {
        // Private constructor to prevent instantiation
    }
    
}
