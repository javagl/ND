/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.tuples.d;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestDoubleTupleStream
{
    @Test
    public void testStream()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(3.0, 2.0, 1.0, 0.0);
        double expected[] = { 3.0, 2.0, 1.0, 0.0 };
        double actual[] = t0.stream().toArray();
        assertArrayEquals(expected, actual, 0.0);
    }

    @Test
    public void testSortedStream()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(3.0, 2.0, 1.0, 0.0);
        double expected[] = { 0.0, 1.0, 2.0, 3.0 };
        double actual[] = t0.stream().sorted().toArray();
        assertArrayEquals(expected, actual, 0.0);
    }
    
    @Test
    public void testSubSortedStream()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(3.0, 2.0, 1.0, 0.0);
        double expected[] = { 1.0, 2.0 };
        double actual[] = t0.subTuple(1,3).stream().sorted().toArray();
        assertArrayEquals(expected, actual, 0.0);
    }
    
}