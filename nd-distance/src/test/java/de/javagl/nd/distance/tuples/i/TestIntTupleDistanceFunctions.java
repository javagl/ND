/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.distance.tuples.i;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.javagl.nd.distance.DistanceFunction;
import de.javagl.nd.distance.tuples.i.IntTupleDistanceFunctions;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;

@SuppressWarnings("javadoc")
public class TestIntTupleDistanceFunctions
{
    private static final double EPSILON = 1e-8;

    @Test
    public void testChebyshev()
    {
        DistanceFunction<IntTuple> distanceFunction =
            IntTupleDistanceFunctions.chebyshev();
        
        double actual = distanceFunction.distance(
            IntTuples.of(1,2,3), IntTuples.of(2,4,6));
        double expected = 3.0;
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testEuclidean()
    {
        DistanceFunction<IntTuple> distanceFunction =
            IntTupleDistanceFunctions.euclidean();
        
        double actual = distanceFunction.distance(
            IntTuples.of(1,2,3), IntTuples.of(2,4,6));
        double expected = Math.sqrt(14.0);
        assertEquals(expected, actual, EPSILON);
    }
    
    @Test
    public void testEuclideanSquared()
    {
        DistanceFunction<IntTuple> distanceFunction =
            IntTupleDistanceFunctions.euclideanSquared();
        
        double actual = distanceFunction.distance(
            IntTuples.of(1,2,3), IntTuples.of(2,4,6));
        double expected = 14.0;
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testManhattan()
    {
        DistanceFunction<IntTuple> distanceFunction =
            IntTupleDistanceFunctions.manhattan();
        
        double actual = distanceFunction.distance(
            IntTuples.of(1,2,3), IntTuples.of(2,4,6));
        double expected = 6.0;
        assertEquals(expected, actual, EPSILON);
    }


    @Test
    public void testWrappedChebyshev()
    {
        DistanceFunction<IntTuple> distanceFunction =
            IntTupleDistanceFunctions.wrappedChebyshev(
                IntTuples.of(2,2,2));
        
        double actual = distanceFunction.distance(
            IntTuples.of(1,2,3), IntTuples.of(2,4,6));
        double expected = 1.0;
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testWrappedEuclidean()
    {
        DistanceFunction<IntTuple> distanceFunction =
            IntTupleDistanceFunctions.wrappedEuclidean(
                IntTuples.of(2,2,2));
        
        double actual = distanceFunction.distance(
            IntTuples.of(1,2,3), IntTuples.of(2,4,6));
        double expected = Math.sqrt(2.0);
        assertEquals(expected, actual, EPSILON);
    }
    
    @Test
    public void testWrappedEuclideanSquared()
    {
        DistanceFunction<IntTuple> distanceFunction =
            IntTupleDistanceFunctions.wrappedEuclideanSquared(
                IntTuples.of(2,2,2));
        
        double actual = distanceFunction.distance(
            IntTuples.of(1,2,3), IntTuples.of(2,4,6));
        double expected = 2.0;
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testWrappedManhattan()
    {
        DistanceFunction<IntTuple> distanceFunction =
            IntTupleDistanceFunctions.wrappedManhattan(
                IntTuples.of(2,2,2));
        
        double actual = distanceFunction.distance(
            IntTuples.of(1,2,3), IntTuples.of(2,4,6));
        double expected = 2.0;
        assertEquals(expected, actual, EPSILON);
    }

    
    
}
