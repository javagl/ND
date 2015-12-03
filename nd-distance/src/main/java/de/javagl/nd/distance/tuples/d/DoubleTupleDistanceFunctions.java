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
package de.javagl.nd.distance.tuples.d;

import java.util.Comparator;
import java.util.function.ToDoubleBiFunction;

import de.javagl.nd.distance.DistanceFunction;
import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;
import de.javagl.nd.tuples.d.DoubleTuple;
import de.javagl.nd.tuples.d.DoubleTuples;

/**
 * Methods to create {@link DistanceFunction} instances
 * for {@link DoubleTuple}s
 */
public class DoubleTupleDistanceFunctions
{
    /**
     * Returns a new comparator that compares {@link DoubleTuple} instances
     * by their distance to the given reference, according to the given
     * distance function.
     * A copy of the given reference point will be stored, so that changes
     * in the given point will not affect the returned comparator.
     *
     * @param reference The reference point
     * @param distanceFunction The distance function
     * @return The comparator
     */
    public static Comparator<DoubleTuple> byDistanceComparator(
        DoubleTuple reference,
        final ToDoubleBiFunction<? super DoubleTuple, ? super DoubleTuple> 
            distanceFunction)
    {
        final DoubleTuple fReference = DoubleTuples.copy(reference);
        return new Comparator<DoubleTuple>()
        {
            @Override
            public int compare(DoubleTuple t0, DoubleTuple t1)
            {
                double d0 = distanceFunction.applyAsDouble(fReference, t0);
                double d1 = distanceFunction.applyAsDouble(fReference, t1);
                return Double.compare(d0, d1);
            }
        };
    }

    /**
     * Returns a {@link DistanceFunction} that computes the
     * Euclidean distance
     *
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<DoubleTuple> euclidean()
    {
        return new DoubleTupleDistanceFunctionEuclidean();
    }

    /**
     * Returns a {@link DistanceFunction} that computes the
     * squared Euclidean distance
     *
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<DoubleTuple> euclideanSquared()
    {
        return new DoubleTupleDistanceFunctionEuclideanSquared();
    }

    /**
     * Computes the Euclidean distance between the given tuples
     *
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return The distance
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static double computeEuclidean(DoubleTuple t0, DoubleTuple t1)
    {
        return Math.sqrt(computeEuclideanSquared(t0, t1));
    }

    /**
     * Computes the squared Euclidean distance between the given tuples
     *
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return The squared distance
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static double computeEuclideanSquared(
        DoubleTuple t0, DoubleTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        double sum = 0;
        int n = t0.getSize();
        for (int i=0; i<n; i++)
        {
            double value0 = t0.get(i);
            double value1 = t1.get(i);
            double d = value1 - value0;
            double dd = d * d;
            sum += dd;
        }
        return sum;
    }

    
    
    /**
     * Returns a {@link DistanceFunction} that computes the
     * angular distance (that is, <code>1-c</code>, 
     * where <code>c</code> is the cosine similarity)
     *
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<DoubleTuple> angular()
    {
        return new DoubleTupleDistanceFunctionAngular();
    }
    
    
    /**
     * Computes the cosine similarity between the given tuples
     * 
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return The cosine similarity
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    private static double computeCosineSimilarity(
        DoubleTuple t0, DoubleTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        double dot = DoubleTuples.dot(t0, t1);
        final double epsilon = 1e-10;
        if (Math.abs(dot) < epsilon)
        {
            return 0;
        }
        double tt0 = DoubleTuples.computeL2(t0);
        double tt1 = DoubleTuples.computeL2(t1);
        double tt = tt0 * tt1;
        double result = clamp(dot / tt, -1.0, 1.0);
        return result;
    }
    
    /**
     * Clamp the given value to be in [min,max]
     * 
     * @param d The value
     * @param min The minimum value
     * @param max The maximum value
     * @return The clamped value
     */
    private static double clamp(double d, double min, double max)
    {
        if (d < min)
        {
            return min;
        }
        if (d > max)
        {
            return max;
        }
        return d;
    }
    
    /**
     * Computes the angular similarity between the given tuples
     * 
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return The angular similarity
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    static double computeAngularSimilarity(
        DoubleTuple t0, DoubleTuple t1)
    {
        return 1 - Math.acos(computeCosineSimilarity(t0, t1)) / Math.PI;
    }
    
    
    /**
     * Returns a {@link DistanceFunction} that computes the dynamic
     * time warping distance between two {@link DoubleTuple}s
     * 
     * @return The {@link DistanceFunction}
     */
	public static DistanceFunction<DoubleTuple> dynamicTimeWarping()
	{
		return new DoubleTupleDistanceFunctionDynamicTimeWarping();
	}
    


    /**
     * Private constructor to prevent instantiation
     */
    private DoubleTupleDistanceFunctions()
    {
        // Private constructor to prevent instantiation
    }
}