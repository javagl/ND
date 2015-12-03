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
package de.javagl.nd.distance.tuples.i;

import java.util.Comparator;
import java.util.function.ToDoubleBiFunction;

import de.javagl.nd.distance.DistanceFunction;
import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;

/**
 * Methods to create {@link DistanceFunction}s for {@link IntTuple}s
 */
public class IntTupleDistanceFunctions
{
    /**
     * Returns a new comparator that compares {@link IntTuple} instances
     * by their distance to the given reference, according to the given
     * distance function.
     * A copy of the given reference point will be stored, so that changes
     * in the given point will not affect the returned comparator.
     *
     * @param reference The reference point
     * @param distanceFunction The distance function
     * @return The comparator
     */
    public static Comparator<IntTuple> byDistanceComparator(
        IntTuple reference,
        final ToDoubleBiFunction<? super IntTuple, ? super IntTuple> 
            distanceFunction)
    {
        final IntTuple fReference = IntTuples.copy(reference);
        return new Comparator<IntTuple>()
        {
            @Override
            public int compare(IntTuple t0, IntTuple t1)
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
    public static DistanceFunction<IntTuple> euclidean()
    {
        return new IntTupleDistanceFunctionEuclidean();
    }

    /**
     * Returns a {@link DistanceFunction} that computes the 
     * squared Euclidean distance 
     * 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<IntTuple> euclideanSquared()
    {
        return new IntTupleDistanceFunctionEuclideanSquared();
    }
    
    /**
     * Returns a {@link DistanceFunction} that computes the 
     * Euclidean distance of {@link IntTuple}s when they are interpreted
     * as points on a sphere with the given size (that is, circumference).
     *  
     * @param size The size of the sphere 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<IntTuple> wrappedEuclidean(IntTuple size)
    {
        return new IntTupleDistanceFunctionEuclideanWrapped(size);
    }
    
    /**
     * Returns a {@link DistanceFunction} that computes the squared 
     * Euclidean distance of {@link IntTuple}s when they are interpreted
     * as points on a sphere with the given size (that is, circumference).
     *  
     * @param size The size of the sphere 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<IntTuple> wrappedEuclideanSquared(
        IntTuple size)
    {
        return new IntTupleDistanceFunctionEuclideanSquaredWrapped(size);
    }
    
    
    /**
     * Returns a {@link DistanceFunction} that computes the 
     * Manhattan distance 
     * 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<IntTuple> manhattan()
    {
        return new IntTupleDistanceFunctionManhattan();
    }
    
    /**
     * Returns a {@link DistanceFunction} that computes the 
     * Manhattan distance of {@link IntTuple}s when they are interpreted
     * as points on a sphere with the given size (that is, circumference).
     * 
     * @param size The size of the sphere 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<IntTuple> wrappedManhattan(IntTuple size)
    {
        return new IntTupleDistanceFunctionManhattanWrapped(size);
    }
    
    
    /**
     * Returns a {@link DistanceFunction} that computes the 
     * Chebyshev distance 
     * 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<IntTuple> chebyshev()
    {
        return new IntTupleDistanceFunctionChebyshev();
    }
    
    /**
     * Returns a {@link DistanceFunction} that computes the 
     * Chebyshev distance of {@link IntTuple}s when they are interpreted
     * as points on a sphere with the given size (that is, circumference).
     * 
     * @param size The size of the sphere 
     * @return The {@link DistanceFunction}
     */
    public static DistanceFunction<IntTuple> wrappedChebyshev(IntTuple size)
    {
        return new IntTupleDistanceFunctionChebyshevWrapped(size);
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
    static double computeEuclidean(IntTuple t0, IntTuple t1)
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
    static double computeEuclideanSquared(IntTuple t0, IntTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        long sum = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            int d = t0.get(i)-t1.get(i);
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
        IntTuple t0, IntTuple t1, IntTuple size)
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
        IntTuple t0, IntTuple t1, IntTuple size)
    {
        Utils.checkForEqualSize(t0, t1);
        Utils.checkForEqualSize(t0, size);
        long sum = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            int d = MathUtils.wrappedDistance(
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
    static int computeManhattan(IntTuple t0, IntTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        int sum = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            int d = t0.get(i)-t1.get(i);
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
    static int computeWrappedManhattan(
        IntTuple t0, IntTuple t1, IntTuple size)
    {
        Utils.checkForEqualSize(t0, t1);
        int sum = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            int d = MathUtils.wrappedDistance(
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
    static int computeChebyshev(IntTuple t0, IntTuple t1)
    {
        Utils.checkForEqualSize(t0, t1);
        int max = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            int d = t0.get(i)-t1.get(i);
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
    static int computeWrappedChebyshev(
        IntTuple t0, IntTuple t1, IntTuple size)
    {
        Utils.checkForEqualSize(t0, t1);
        int max = 0;
        for (int i=0; i<t0.getSize(); i++)
        {
            int d = MathUtils.wrappedDistance(
                t0.get(i), t1.get(i), size.get(i));
            max = Math.max(max, Math.abs(d));
        }
        return max;
    }
    
 
    /**
     * Private constructor to prevent instantiation
     */
    private IntTupleDistanceFunctions()
    {
        // Private constructor to prevent instantiation
    }
 
    
    
}


