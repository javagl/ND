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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import de.javagl.nd.tuples.Tuple;

/**
 * Utility methods related to collections of {@link IntTuple} 
 * instances.<br>
 * <br>
 * For the methods that receive a collection of input tuples and a 
 * <code>result</code> tuple, unless otherwise noted, the behavior
 * is as follows:
 * <ul>
 *   <li>
 *     If the input collection is empty, then <code>null</code> will
 *     be returned
 *   </li>
 *   <li>
 *     If the input collection is not empty:
 *     <ul>
 *       <li>
 *         If the <code>result</code> tuple is <code>null</code>, then a new 
 *         tuple will be created and returned.
 *       </li>
 *       <li>
 *         If the <code>result</code> tuple is not <code>null</code>,
 *         then it will be verified that the result tuple has an appropriate
 *         {@link Tuple#getSize() size} for the respective operation, 
 *         and an <code>IllegalArgumentException</code> will be thrown if 
 *         this is not the case.
 *       </li>
 *     </ul>
 *   </li>
 * </ul>
 * <br>
 * All these methods assume that the collections do not contain 
 * <code>null</code> elements, and that the {@link Tuple#getSize() size} 
 * of all tuples is the same. If this is not the case, the behavior is 
 * unspecified (although in most cases, a <code>NullPointerException</code> 
 * or an <code>IllegalArgumentException</code> will be thrown, respectively) 
 */
public class IntTupleCollections
{
    /**
     * Create the specified number of tuples with the given dimensions
     * ({@link Tuple#getSize() size}), all initialized to zero, and place 
     * them into the given target collection
     * 
     * @param dimensions The dimensions ({@link Tuple#getSize() size})
     * of the tuples to create
     * @param numElements The number of tuples to create
     * @param target The target collection
     * @throws IllegalArgumentException If the given dimensions are negative
     */
    public static void create(
        int dimensions, int numElements, 
        Collection<? super MutableIntTuple> target)
    {
        for (int i=0; i<numElements; i++)
        {
            target.add(IntTuples.create(dimensions));
        }
    }

    /**
     * Create the specified number of tuples with the given dimensions
     * ({@link Tuple#getSize() size}), all initialized to zero, and
     * return them as a list 
     * 
     * @param dimensions The dimensions ({@link Tuple#getSize() size})
     * of the tuples to create
     * @param numElements The number of tuples to create
     * @return The list
     * @throws IllegalArgumentException If the given dimensions are negative
     */
    public static List<MutableIntTuple> create(
        int dimensions, int numElements)
    {
        List<MutableIntTuple> list = new ArrayList<MutableIntTuple>();
        create(dimensions, numElements, list);
        return list;
    }



    /**
     * Returns a deep copy of the given collection of tuples. If any element
     * of the given collection is <code>null</code>, then <code>null</code>
     * will also be added to the target collection.
     * 
     * @param tuples The input tuples
     * @return The deep copy
     */
    public static List<MutableIntTuple> deepCopy(
        Collection<? extends IntTuple> tuples)
    {
        List<MutableIntTuple> result = 
            new ArrayList<MutableIntTuple>(tuples.size());
        for (IntTuple t : tuples)
        {
            if (t == null)
            {
                result.add(null);
            }
            else
            {
                result.add(IntTuples.copy(t));
            }
        }
        return result;
    }



    /**
     * Computes the component-wise minimum of the given collection 
     * of tuples.<br>
     * <br>
     * If the given collection is not empty, and the given result is 
     * <code>null</code>, then a new tuple with the same 
     * {@link Tuple#getSize() size} as the first tuple of the collection
     * will be created internally. All elements of this tuple will
     * be initialized with <code>Integer.MAX_VALUE</code>. 
     * <br>
     *
     * @param tuples The input tuples
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not 
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableIntTuple min(
        Collection<? extends IntTuple> tuples,
        MutableIntTuple result)
    {
        if (tuples.isEmpty()) 
        {
            return null;
        }
        int size = getSize(result, tuples);
        IntTuple identity = 
            IntTuples.constant(size, Integer.MAX_VALUE);
        MutableIntTuple localResult = 
            tuples.parallelStream().collect(
                () -> IntTuples.copy(identity), 
                (r,t) -> IntTuples.min(r, t, r), 
                (r0,r1) -> IntTuples.min(r0, r1, r0));
        if (result == null)
        {
            return localResult;
        }
        result.set(localResult);
        return result;
    }




    /**
     * Computes the component-wise maximum of the given collection 
     * of tuples.<br>
     * <br>
     * If the given collection is not empty, and the given result is 
     * <code>null</code>, then a new tuple with the same 
     * {@link Tuple#getSize() size} as the first tuple of the collection
     * will be created internally. All elements of this tuple will
     * be initialized with <code>Integer.MIN_VALUE</code>. 
     * <br>
     *
     * @param tuples The input tuples
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not 
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableIntTuple max(
        Collection<? extends IntTuple> tuples,
        MutableIntTuple result)
    {
        if (tuples.isEmpty()) 
        {
            return null;
        }
        int size = getSize(result, tuples);
        IntTuple identity = 
            IntTuples.constant(size, Integer.MIN_VALUE);
        MutableIntTuple localResult = 
            tuples.parallelStream().collect(
                () -> IntTuples.copy(identity), 
                (r,t) -> IntTuples.max(r, t, r), 
                (r0,r1) -> IntTuples.max(r0, r1, r0));
        if (result == null)
        {
            return localResult;
        }
        result.set(localResult);
        return result;
    }

    /**
     * Computes the component-wise sum of the given collection 
     * of tuples.<br>
     * <br>
     * If the given collection is not empty, and the given result is 
     * <code>null</code>, then a new tuple with the same 
     * {@link Tuple#getSize() size} as the first tuple of the collection
     * will be created internally. 
     * <br>
     *
     * @param tuples The input tuples
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not 
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableIntTuple add(
        Collection<? extends IntTuple> tuples,
        MutableIntTuple result)
    {
        if (tuples.isEmpty()) 
        {
            return null;
        }
        int size = getSize(result, tuples);
        MutableIntTuple localResult = 
            tuples.parallelStream().collect(
                () -> IntTuples.create(size), 
                (r,t) -> IntTuples.add(r, t, r), 
                (r0,r1) -> IntTuples.add(r0, r1, r0));
        if (result == null)
        {
            return localResult;
        }
        result.set(localResult);
        return result;
    }



    /**
     * Returns the size of the given tuple. If the given tuple is 
     * <code>null</code>, then the size of the first tuple of the
     * given sequence is returned. If this first tuple is <code>null</code>,
     * or the given sequence is empty, then -1 is returned.
     * 
     * @param t The tuple
     * @param tuples The tuples
     * @return The size
     */
    private static int getSize(Tuple t, Iterable<? extends Tuple> tuples)
    {
        if (t != null)
        {
            return t.getSize();
        }
        Iterator<? extends Tuple> iterator = tuples.iterator();
        if (iterator.hasNext())
        {
            Tuple first = iterator.next();
            if (first != null)
            {
                return first.getSize();
            }
        }
        return -1;
    }


    //=========================================================================
    // Start of custom methods


    // End   of custom methods
    //=========================================================================


    /**
     * Private constructor to prevent instantiation
     */
    private IntTupleCollections()
    {
        // Private constructor to prevent instantiation
    }
}
