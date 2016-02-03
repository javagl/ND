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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import de.javagl.nd.tuples.Tuple;

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Utility methods related to collections of {@link DoubleTuple} 
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
public class DoubleTupleCollections
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
        Collection<? super MutableDoubleTuple> target)
    {
        for (int i=0; i<numElements; i++)
        {
            target.add(DoubleTuples.create(dimensions));
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
    public static List<MutableDoubleTuple> create(
        int dimensions, int numElements)
    {
        List<MutableDoubleTuple> list = 
            new ArrayList<MutableDoubleTuple>(numElements);
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
    public static List<MutableDoubleTuple> deepCopy(
        Collection<? extends DoubleTuple> tuples)
    {
        List<MutableDoubleTuple> result = 
            new ArrayList<MutableDoubleTuple>(tuples.size());
        for (DoubleTuple t : tuples)
        {
            if (t == null)
            {
                result.add(null);
            }
            else
            {
                result.add(DoubleTuples.copy(t));
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
     * be initialized with <code>Double.POSITIVE_INFINITY</code>. 
     * <br>
     *
     * @param tuples The input tuples
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not 
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableDoubleTuple min(
        Collection<? extends DoubleTuple> tuples,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty()) 
        {
            return null;
        }
        int size = getSize(result, tuples);
        DoubleTuple identity = 
            DoubleTuples.constant(size, Double.POSITIVE_INFINITY);
        MutableDoubleTuple localResult = 
            tuples.parallelStream().collect(
                () -> DoubleTuples.copy(identity), 
                (r,t) -> DoubleTuples.min(r, t, r), 
                (r0,r1) -> DoubleTuples.min(r0, r1, r0));
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
     * be initialized with <code>Double.NEGATIVE_INFINITY</code>. 
     * <br>
     *
     * @param tuples The input tuples
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not 
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableDoubleTuple max(
        Collection<? extends DoubleTuple> tuples,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty()) 
        {
            return null;
        }
        int size = getSize(result, tuples);
        DoubleTuple identity = 
            DoubleTuples.constant(size, Double.NEGATIVE_INFINITY);
        MutableDoubleTuple localResult = 
            tuples.parallelStream().collect(
                () -> DoubleTuples.copy(identity), 
                (r,t) -> DoubleTuples.max(r, t, r), 
                (r0,r1) -> DoubleTuples.max(r0, r1, r0));
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
    public static MutableDoubleTuple add(
        Collection<? extends DoubleTuple> tuples,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty()) 
        {
            return null;
        }
        int size = getSize(result, tuples);
        MutableDoubleTuple localResult = 
            tuples.parallelStream().collect(
                () -> DoubleTuples.create(size), 
                (r,t) -> DoubleTuples.add(r, t, r), 
                (r0,r1) -> DoubleTuples.add(r0, r1, r0));
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


    /**
     * Standardize the given input tuples. This means that the mean of
     * the given tuples is subtracted from them, and they are divided
     * by the standard deviation. <br>
     * <br>
     * The results will be written into the given result tuples.
     * If the given results list is <code>null</code>, then a new
     * list will be created and returned. <br>
     * <br>
     * If the result list is not <code>null</code>, then it must have the
     * same size as the input collection, otherwise an
     * <code>IllegalArgumentException</code> will be thrown.<br>
     * <br>
     * If the input collection contains <code>null</code> elements, then a
     * <code>NullPointerException</code> will be thrown. <br>
     * <br>
     * If the result list contains <code>null</code> elements, then a
     * <code>NullPointerException</code> will be thrown. <br>
     * <br>
     * The input collection and the results list may contain
     * pairwise identical elements.<br>
     * <br>
     * The input collection and the results list may be identical.
     * <br>
     * @param inputs The input tuples
     * @param results The result tuples
     * @return The result tuples
     * @throws IllegalArgumentException If the result list is not
     * <code>null</code> and has a size that is different from the size
     * of the input collection
     * @throws NullPointerException If the inputs list is <code>null</code>
     * or contains <code>null</code> elements, or if the output list is
     * non-<code>null</code> and contains <code>null</code> elements
     */
    public static List<MutableDoubleTuple> standardize(
        Collection<? extends DoubleTuple> inputs,
        List<MutableDoubleTuple> results)
    {
        results = validate(inputs, results);
        MutableDoubleTuple mean = arithmeticMean(inputs, null);
        MutableDoubleTuple standardDeviation =
            standardDeviationFromMean(inputs, mean, null);
        int index = 0;

        for (DoubleTuple input : inputs)
        {
            MutableDoubleTuple result = results.get(index);
            DoubleTuples.subtract(input, mean, result);
            DoubleTuples.divide(result, standardDeviation, result);
            index++;
        }

        boolean printResults = false;
        //printResults = true;
        if (printResults)
        {
            MutableDoubleTuple newMean = arithmeticMean(results, null);
            MutableDoubleTuple newStandardDeviation =
                standardDeviationFromMean(results, newMean, null);
            DoubleTuple min = min(results, null);
            DoubleTuple max = max(results, null);
            System.out.println("After standardization:");
            System.out.println("mean              : "+newMean);
            System.out.println("standard deviation: "+newStandardDeviation);
            System.out.println("min               : "+min);
            System.out.println("max               : "+max);
        }

        return results;
    }


    /**
     * Validate the given results list. If the given results list
     * is <code>null</code>, then a new results list will be created
     * and returned, that contains one MutableDoubleTuple instance
     * for each tuple from the inputs list, with the same size
     * as the corresponding tuple from the input collection.<br>
     * <br>
     * If the result list is not <code>null</code>, then it must have the
     * same size as the input collection, otherwise an
     * <code>IllegalArgumentException</code> will be thrown.<br>
     * <br>
     *
     * @param inputs The inputs
     * @param results The results
     * @return The results
     * @throws IllegalArgumentException If the given results list is not
     * <code>null</code>, and has a different size than the given inputs
     * collection
     */
    private static List<MutableDoubleTuple> validate(
        Collection<? extends DoubleTuple> inputs,
        List<MutableDoubleTuple> results)
    {
        if (results == null)
        {
            results = new ArrayList<MutableDoubleTuple>(inputs.size());
            for (DoubleTuple input : inputs)
            {
                results.add(DoubleTuples.create(input.getSize()));
            }
        }
        else
        {
            if (inputs.size() != results.size())
            {
                throw new IllegalArgumentException(
                    "The number of inputs (" + inputs.size() + ") must " +
                    "be the same as the number of results " +
                    "(" + results.size() + ")");
            }
        }
        return results;
    }

    /**
     * Normalize the given collection of {@link MutableDoubleTuple}s.<br>
     * <br>
     * The results will be written into the given result tuples.
     * If the given results list is <code>null</code>, then a new
     * list will be created and returned. <br>
     * <br>
     * If the result list is not <code>null</code>, then it must have the
     * same size as the input collection, otherwise an
     * <code>IllegalArgumentException</code> will be thrown.<br>
     * <br>
     * If the input collection contains <code>null</code> elements, then a
     * <code>NullPointerException</code> will be thrown. <br>
     * <br>
     * If the result list contains <code>null</code> elements, then a
     * <code>NullPointerException</code> will be thrown. <br>
     * <br>
     * The input collection and the results list may contain
     * pairwise identical elements.<br>
     * <br>
     * The input collection and the results list may be identical.
     * <br>
     *
     * @param inputs The tuples to normalize
     * @param results The result tuples
     * @return The result tuples
     * @throws IllegalArgumentException If the result list is not
     * <code>null</code> and has a size that is different from the size
     * of the input collection
     * @throws NullPointerException If the inputs list is <code>null</code>
     * or contains <code>null</code> elements, or if the output list is
     * non-<code>null</code> and contains <code>null</code> elements
     * @see DoubleTuples#normalize(DoubleTuple, MutableDoubleTuple)
     */
    public static List<MutableDoubleTuple> normalize(
        Collection<? extends DoubleTuple> inputs,
        List<MutableDoubleTuple> results)
    {
        results = validate(inputs, results);
        int index = 0;
        for (DoubleTuple input : inputs)
        {
            MutableDoubleTuple result = results.get(index);
            DoubleTuples.normalize(input, result);
            index++;
        }
        return results;
    }


    /**
     * Computes the component-wise arithmetic mean of the given collection
     * of {@link DoubleTuple} objects.
     *
     * @param tuples The input tuples
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableDoubleTuple arithmeticMean(
        Collection<? extends DoubleTuple> tuples,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty())
        {
            return null;
        }
        result = add(tuples, result);
        return DoubleTuples.multiply(result, 1.0 / tuples.size(), result);
    }

    /**
     * Computes the component-wise variance of the given collection
     * of {@link DoubleTuple} objects. The method will compute
     * the arithmetic mean, and then compute the actual result
     * with the {@link #variance(Collection, DoubleTuple, MutableDoubleTuple)}
     * method.
     *
     * @param tuples The input tuples
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableDoubleTuple variance(
        Collection<? extends DoubleTuple> tuples,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty())
        {
            return null;
        }
        DoubleTuple mean = arithmeticMean(tuples, null);
        return variance(tuples, mean, result);
    }

    /**
     * Computes the component-wise variance of the given collection
     * of {@link DoubleTuple} objects.
     *
     * @param tuples The input tuples
     * @param mean The mean, which may have been computed before with
     * {@link #arithmeticMean}
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableDoubleTuple variance(
        Collection<? extends DoubleTuple> tuples, DoubleTuple mean,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty())
        {
            return null;
        }
        DoubleTuple first = tuples.iterator().next();
        result = DoubleTuples.validate(first, result);
        DoubleTuples.set(result, 0.0);
        int d = result.getSize();
        for (DoubleTuple tuple : tuples)
        {
            for (int i=0; i<d; i++)
            {
                double difference = tuple.get(i) - mean.get(i);
                double v = result.get(i);
                result.set(i, v + difference * difference);
            }
        }
        return DoubleTuples.multiply(result, 1.0 / tuples.size(), result);
    }

    /**
     * Returns the component-wise standard deviation of the given collection
     * of {@link DoubleTuple} objects. The method will compute
     * the arithmetic mean, and then compute the actual result
     * with the {@link #standardDeviationFromMean} method.
     *
     * @param tuples The input tuples
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableDoubleTuple standardDeviation(
        Collection<? extends DoubleTuple> tuples,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty())
        {
            return null;
        }
        DoubleTuple mean = arithmeticMean(tuples, null);
        return standardDeviationFromMean(tuples, mean, result);
    }

    /**
     * Returns the component-wise standard deviation of the given collection
     * of {@link DoubleTuple} objects.
     * The method will compute {@link #variance}, and then compute
     * the actual result with the
     * {@link DoubleTuples#standardDeviationFromVariance}
     * method.
     *
     * @param tuples The input tuples
     * @param mean The mean, which may have been computed before with
     * {@link #arithmeticMean}
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableDoubleTuple standardDeviationFromMean(
        Collection<? extends DoubleTuple> tuples, DoubleTuple mean,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty())
        {
            return null;
        }
        result = variance(tuples, mean, result);
        return DoubleTuples.standardDeviationFromVariance(result, result);
    }

    // End   of custom methods
    //=========================================================================


    /**
     * Private constructor to prevent instantiation
     */
    private DoubleTupleCollections()
    {
        // Private constructor to prevent instantiation
    }
}
