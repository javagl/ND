/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.tuples.d;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestDoubleTupleFunctions
{
    @Test
    public void testInclusiveScan()
    {
        DoubleTuple t0 = DoubleTuples.of(1, 0, 2, 2, 1, 3);
        DoubleTuple expected = DoubleTuples.of(1, 1, 3, 5, 6, 9);
        DoubleTuple actual = 
            DoubleTupleFunctions.inclusiveScan(t0, (a, b) -> a + b, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testInclusiveScanInPlace()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1, 0, 2, 2, 1, 3);
        DoubleTuple expected = DoubleTuples.of(1, 1, 3, 5, 6, 9);
        DoubleTupleFunctions.inclusiveScan(t0, (a, b) -> a + b, t0);
        assertEquals(expected, t0);
    }
    
    @Test
    public void testExclusiveScan()
    {
        DoubleTuple t0 = DoubleTuples.of(1, 0, 2, 2, 1, 3);
        DoubleTuple expected = DoubleTuples.of(0, 1, 1, 3, 5, 6);
        DoubleTuple actual = 
            DoubleTupleFunctions.exclusiveScan(t0, 0.0, (a, b) -> a + b, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testExclusiveScanInPlace()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1, 0, 2, 2, 1, 3);
        DoubleTuple expected = DoubleTuples.of(0, 1, 1, 3, 5, 6);
        DoubleTupleFunctions.exclusiveScan(t0, 0.0, (a, b) -> a + b, t0);
        assertEquals(expected, t0);
    }
    
    
}