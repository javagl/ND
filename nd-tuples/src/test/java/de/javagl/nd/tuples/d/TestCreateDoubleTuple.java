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
public class TestCreateDoubleTuple
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreation()
    {
        MutableDoubleTuple t0 = DoubleTuples.create(6);
        assertEquals(6, t0.getSize());
    }

    
    @Test
    public void testCopy()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0.0, 1.0, 2.0);
        MutableDoubleTuple t1 = DoubleTuples.copy(t0);
        assertEquals(t0, t1);
    }
    
    @Test
    public void testCreationWithNegativeSize()
    {
        thrown.expect(IllegalArgumentException.class);
        DoubleTuples.create(-1);
    }

    @Test
    public void testAccessWithNegativeIndex()
    {
        MutableDoubleTuple t0 = DoubleTuples.create(6);
        thrown.expect(IndexOutOfBoundsException.class);
        t0.get(-1);
    }
    
    @Test
    public void testAccessWithTooLargeIndex()
    {
        MutableDoubleTuple t0 = DoubleTuples.create(6);
        thrown.expect(IndexOutOfBoundsException.class);
        t0.get(111);
    }
    
    @Test
    public void testWriteThroughToArray()
    {
        double array[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
        MutableDoubleTuple t0 = DoubleTuples.wrap(array);
        array[0] = 123;
        assertEquals(123, t0.get(0), 0.0);
        t0.set(1, 234);
        assertEquals(234, array[1], 0.0);
    }
    
}