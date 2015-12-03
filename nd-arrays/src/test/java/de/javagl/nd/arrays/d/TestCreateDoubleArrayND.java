/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.arrays.d;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.javagl.nd.tuples.d.DoubleTuples;
import de.javagl.nd.tuples.d.MutableDoubleTuple;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTupleFunctions;
import de.javagl.nd.tuples.i.IntTuples;

@SuppressWarnings("javadoc")
public class TestCreateDoubleArrayND
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreation()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(2, 3, 4);
        assertEquals(2 * 3 * 4, a0.getTotalSize());
        assertEquals(3, a0.getSize().getSize());

        MutableDoubleArrayND a1 = DoubleArraysND.create(2, 0, 4);
        assertEquals(2 * 0 * 4, a1.getTotalSize());
        assertEquals(3, a1.getSize().getSize());
    }

    @Test
    public void testCreationWithNegativeSize()
    {
        thrown.expect(IllegalArgumentException.class);
        DoubleArraysND.create(2, -3, 4);
    }
    
    @Test
    public void testWrap()
    {
        IntTuple sizeND = IntTuples.wrap(2,3,4);
        int size1D = IntTupleFunctions.reduce(sizeND, 1, (a, b) -> a * b);
        MutableDoubleTuple tuple = DoubleTuples.create(size1D);
        for (int i=0; i<size1D; i++)
        {
            tuple.set(i, i);
        }
        MutableDoubleArrayND array = DoubleArraysND.wrap(tuple, sizeND);
     
        assertEquals(0.0, array.get(IntTuples.of(0,0,0)), 0.0);
        assertEquals(1.0, array.get(IntTuples.of(0,0,1)), 0.0);
        assertEquals(2.0, array.get(IntTuples.of(0,0,2)), 0.0);
        assertEquals(3.0, array.get(IntTuples.of(0,0,3)), 0.0);
        
        assertEquals(4.0, array.get(IntTuples.of(0,1,0)), 0.0);
        assertEquals(5.0, array.get(IntTuples.of(0,1,1)), 0.0);
        assertEquals(6.0, array.get(IntTuples.of(0,1,2)), 0.0);
        assertEquals(7.0, array.get(IntTuples.of(0,1,3)), 0.0);
        
        assertEquals(8.0, array.get(IntTuples.of(0,2,0)), 0.0);
        assertEquals(9.0, array.get(IntTuples.of(0,2,1)), 0.0);
        assertEquals(10.0, array.get(IntTuples.of(0,2,2)), 0.0);
        assertEquals(11.0, array.get(IntTuples.of(0,2,3)), 0.0);
        
        assertEquals(12.0, array.get(IntTuples.of(1,0,0)), 0.0);
        assertEquals(13.0, array.get(IntTuples.of(1,0,1)), 0.0);
        assertEquals(14.0, array.get(IntTuples.of(1,0,2)), 0.0);
        assertEquals(15.0, array.get(IntTuples.of(1,0,3)), 0.0);
        
        assertEquals(16.0, array.get(IntTuples.of(1,1,0)), 0.0);
        assertEquals(17.0, array.get(IntTuples.of(1,1,1)), 0.0);
        assertEquals(18.0, array.get(IntTuples.of(1,1,2)), 0.0);
        assertEquals(19.0, array.get(IntTuples.of(1,1,3)), 0.0);
        
        assertEquals(20.0, array.get(IntTuples.of(1,2,0)), 0.0);
        assertEquals(21.0, array.get(IntTuples.of(1,2,1)), 0.0);
        assertEquals(22.0, array.get(IntTuples.of(1,2,2)), 0.0);
        assertEquals(23.0, array.get(IntTuples.of(1,2,3)), 0.0);
        
    }
    
    @Test
    public void testWrapInvalidSize()
    {
        IntTuple sizeND = IntTuples.wrap(2,3,4);
        int size1D = IntTupleFunctions.reduce(sizeND, 1, (a, b) -> a * b);
        MutableDoubleTuple tuple = DoubleTuples.create(size1D-1);

        thrown.expect(IllegalArgumentException.class);
        DoubleArraysND.wrap(tuple, sizeND);
    }

    
}