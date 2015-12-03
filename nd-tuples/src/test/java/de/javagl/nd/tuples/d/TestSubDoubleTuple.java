/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.tuples.d;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

@SuppressWarnings("javadoc")
public class TestSubDoubleTuple
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreation()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0, 1, 2, 3, 4, 5, 6, 7);
        MutableDoubleTuple s0 = t0.subTuple(2, 6);
        assertEquals(4, s0.getSize());
    }

    @Test
    public void testCreationNegativeMin()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        thrown.expect(IllegalArgumentException.class);
        t0.subTuple(-2, 7);
    }
    
    @Test
    public void testCreationMaxTooLarge()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        thrown.expect(IllegalArgumentException.class);
        t0.subTuple(-2, 111);
    }
    
    @Test
    public void testCreationMinGreaterThanMax()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        thrown.expect(IllegalArgumentException.class);
        t0.subTuple(7, 2);
    }

    @Test
    public void testWriteThrough()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        MutableDoubleTuple s0 = t0.subTuple(2, 7);
        s0.set(0, 123);
        assertEquals(123, t0.get(2), 0.0);
        t0.set(3, 234);
        assertEquals(234, s0.get(1), 0.0);
    }
    
    
}