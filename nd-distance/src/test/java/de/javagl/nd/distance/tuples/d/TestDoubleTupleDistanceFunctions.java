/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.distance.tuples.d;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.javagl.nd.distance.DistanceFunction;
import de.javagl.nd.distance.tuples.d.DoubleTupleDistanceFunctions;
import de.javagl.nd.tuples.d.DoubleTuple;
import de.javagl.nd.tuples.d.DoubleTuples;

@SuppressWarnings("javadoc")
public class TestDoubleTupleDistanceFunctions
{
    private static final double EPSILON = 1e-8;

    @Test
    public void testAngularEqualDirection()
    {
        DistanceFunction<DoubleTuple> distanceFunction =
            DoubleTupleDistanceFunctions.angular();
        
        double actual = distanceFunction.distance(
            DoubleTuples.of(1,2,3), DoubleTuples.of(2,4,6));
        double expected = 0.0;
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testAngularOrthogonal()
    {
        DistanceFunction<DoubleTuple> distanceFunction =
            DoubleTupleDistanceFunctions.angular();
        
        double actual = distanceFunction.distance(
            DoubleTuples.of(1,2), DoubleTuples.of(-2, 1));
        double expected = 0.5;
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testAngularOppositeDirection()
    {
        DistanceFunction<DoubleTuple> distanceFunction =
            DoubleTupleDistanceFunctions.angular();
        
        double actual = distanceFunction.distance(
            DoubleTuples.of(1,2), DoubleTuples.of(-1, -2));
        double expected = 1.0;
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testDynamicTimeWarping()
    {
        DistanceFunction<DoubleTuple> distanceFunction =
            DoubleTupleDistanceFunctions.dynamicTimeWarping();
        
        double actual = distanceFunction.distance(
            DoubleTuples.of(1,2,3), DoubleTuples.of(2,4,6));
        double expected = 5.0;
        assertEquals(expected, actual, EPSILON);
    }
    
    @Test
    public void testDynamicTimeWarpingDifferentOrder()
    {
        DistanceFunction<DoubleTuple> distanceFunction =
            DoubleTupleDistanceFunctions.dynamicTimeWarping();
        
        double actual0 = distanceFunction.distance(
            DoubleTuples.of(1,1,1), DoubleTuples.of(1,4,1));
        double expected0 = 3.0;
        assertEquals(expected0, actual0, EPSILON);
        
        double actual1 = distanceFunction.distance(
            DoubleTuples.of(4,1,1), DoubleTuples.of(1,1,1));
        double expected1 = 3.0;
        assertEquals(expected1, actual1, EPSILON);
        
    }
    
    @Test
    public void testEuclidean()
    {
        DistanceFunction<DoubleTuple> distanceFunction =
            DoubleTupleDistanceFunctions.euclidean();
        
        double actual = distanceFunction.distance(
            DoubleTuples.of(1,2,3), DoubleTuples.of(2,4,6));
        double expected = Math.sqrt(14.0);
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testEuclideanSquared()
    {
        DistanceFunction<DoubleTuple> distanceFunction =
            DoubleTupleDistanceFunctions.euclideanSquared();
        
        double actual = distanceFunction.distance(
            DoubleTuples.of(1,2,3), DoubleTuples.of(2,4,6));
        double expected = 14.0;
        assertEquals(expected, actual, EPSILON);
    }

    
    
}
