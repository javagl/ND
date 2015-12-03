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
package de.javagl.nd.distance.tuples.j;

import java.util.Comparator;
import java.util.function.ToDoubleBiFunction;

import de.javagl.nd.distance.DistanceFunction;
import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;
import de.javagl.nd.tuples.j.LongTuple;
import de.javagl.nd.tuples.j.LongTuples;

/**
 * Methods to create {@link DistanceFunction}s for {@link LongTuple}s
 */
public class LongTupleDistanceFunctions
{
    /**
     * Returns a new comparator that compares {@link LongTuple} instances
     * by their distance to the given reference, according to the given
     * distance function.
     * A copy of the given reference point will be stored, so that changes
     * in the given point will not affect the returned comparator.
     *
     * @param reference The reference point
     * @param distanceFunction The distance function
     * @return The comparator
     */
    public static Comparator<LongTuple> byDistanceComparator(
        LongTuple reference,
        final ToDoubleBiFunction<? super LongTuple, ? super LongTuple> 
            distanceFunction)
    {
        final LongTuple fReference = LongTuples.copy(reference);
        return new Comparator<LongTuple>()
        {
            @Override
            public int compare(LongTuple t0, LongTuple t1)
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
    public static DistanceFunction<LongTuple> euclidean()
    {
        return new LongTupleDistanceFunctionEuclidean();
    }

    /**
     * Returns a {@link DistanceFunction} that computes the 
     * squared Euclidean distance 
     * 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<LongTuple> euclideanSquared()
    {
        return new LongTupleDistanceFunctionEuclideanSquared();
    }
    
    /**
     * Returns a {@link DistanceFunction} that computes the 
     * Euclidean distance of {@link LongTuple}s when they are interpreted
     * as points on a sphere with the given size (that is, circumference).
     *  
     * @param size The size of the sphere 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<LongTuple> wrappedEuclidean(LongTuple size)
    {
        return new LongTupleDistanceFunctionEuclideanWrapped(size);
    }
    
    /**
     * Returns a {@link DistanceFunction} that computes the squared 
     * Euclidean distance of {@link LongTuple}s when they are interpreted
     * as points on a sphere with the given size (that is, circumference).
     *  
     * @param size The size of the sphere 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<LongTuple> wrappedEuclideanSquared(
        LongTuple size)
    {
        return new LongTupleDistanceFunctionEuclideanSquaredWrapped(size);
    }
    
    
    /**
     * Returns a {@link DistanceFunction} that computes the 
     * Manhattan distance 
     * 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<LongTuple> manhattan()
    {
        return new LongTupleDistanceFunctionManhattan();
    }
    
    /**
     * Returns a {@link DistanceFunction} that computes the 
     * Manhattan distance of {@link LongTuple}s when they are interpreted
     * as points on a sphere with the given size (that is, circumference).
     * 
     * @param size The size of the sphere 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<LongTuple> wrappedManhattan(LongTuple size)
    {
        return new LongTupleDistanceFunctionManhattanWrapped(size);
    }
    
    
    /**
     * Returns a {@link DistanceFunction} that computes the 
     * Chebyshev distance 
     * 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<LongTuple> chebyshev()
    {
        return new LongTupleDistanceFunctionChebyshev();
    }
    
    /**
     * Returns a {@link DistanceFunction} that computes the 
     * Chebyshev distance of {@link LongTuple}s when they are interpreted
     * as points on a sphere with the given size (that is, circumference).
     * 
     * @param size The size of the sphere 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<LongTuple> wrappedChebyshev(LongTuple size)
    {
        return new LongTupleDistanceFunctionChebyshevWrapped(size);
    }
    
    
    
    
    /**
     * Computes the Euclidean distance between the given arrays
     * 
     * @param t0 The first array
     * @param t1 The second array
     * @return The distance
     * @throws IllegalArgumentException If the given array do not
     * have the same length 
     */
    static double computeEuclidean(LongTuple t0, LongTuple t1)
    {
        return Math.sqrt(computeEuclideanSquared(t0, t1));
    }
    
    /**
     * Computes the squared Euclidean distance between the given tuples
     * 
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return The distance
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    static double computeEuclideanSquared(LongTuple t0, LongTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        long sum = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            long d = t0.get(i)-t1.get(i);
            sum += d*d;
        }
        return sum;
    }
    
    /**
     * Computes the squared Euclidean distance between the given tuples
     * when they are interpreted as points of a sphere with the specified
     * size.
     * 
     * @param t0 The first array
     * @param t1 The second array
     * @param size The size of the sphere
     * @return The distance
     * @throws IllegalArgumentException If the given arrays do not
     * have the same length 
     */
    static double computeWrappedEuclidean(
        LongTuple t0, LongTuple t1, LongTuple size)
    {
        return Math.sqrt(computeWrappedEuclideanSquared(t0, t1, size));
    }
    
    /**
     * Computes the squared Euclidean distance between the given tuples
     * when they are interpreted as points of a sphere with the specified
     * size (that is, circumference).
     * 
     * @param t0 The first array
     * @param t1 The second array
     * @param size The size of the sphere
     * @return The distance
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    static long computeWrappedEuclideanSquared(
        LongTuple t0, LongTuple t1, LongTuple size)
    {
        Utils.checkForEqualSize(t0, t1);
        Utils.checkForEqualSize(t0, size);
        long sum = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            long d = MathUtils.wrappedDistance(
                t0.get(i), t1.get(i), size.get(i));
            sum += d*d;
        }
        return sum;
    }
    


    /**
     * Computes the Manhattan distance between the given tuples
     * 
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return The distance
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    static long computeManhattan(LongTuple t0, LongTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        long sum = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            long d = t0.get(i)-t1.get(i);
            sum += Math.abs(d);
        }
        return sum;
    }

    /**
     * Computes the Manhattan distance between the given tuples
     * when they are interpreted as points of a sphere with the specified
     * size (that is, circumference).
     * 
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @param size The size of the sphere
     * @return The distance
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    static long computeWrappedManhattan(
        LongTuple t0, LongTuple t1, LongTuple size)
    {
        Utils.checkForEqualSize(t0, t1);
        long sum = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            long d = MathUtils.wrappedDistance(
                t0.get(i), t1.get(i), size.get(i));
            sum += Math.abs(d);
        }
        return sum;
    }
    

    /**
     * Computes the Chebyshev distance between the given tuples
     * 
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @return The distance
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    static long computeChebyshev(LongTuple t0, LongTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        long max = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            long d = t0.get(i)-t1.get(i);
            max = Math.max(max, Math.abs(d));
        }
        return max;
    }

    /**
     * Computes the Chebyshev distance between the given tuples
     * when they are interpreted as points of a sphere with the specified
     * size (that is, circumference).
     * 
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @param size The size of the sphere
     * @return The distance
     * @throws IllegalArgumentException If the given tuples do not 
     * have the same {@link Tuple#getSize() size}
     */
    static long computeWrappedChebyshev(
        LongTuple t0, LongTuple t1, LongTuple size)
    {
        Utils.checkForEqualSize(t0, t1);
        long max = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            long d = MathUtils.wrappedDistance(
                t0.get(i), t1.get(i), size.get(i));
            max = Math.max(max, Math.abs(d));
        }
        return max;
    }
    
 
    /**
     * Private constructor to prevent instantiation
     */
    private LongTupleDistanceFunctions()
    {
        // Private constructor to prevent instantiation
    }
 
    
    
}


