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

import de.javagl.nd.tuples.i.IntTuples;

@SuppressWarnings("javadoc")
public class TestSubDoubleArrayND
{
    /**
     * Create a test array 
     * 
     * @return The test array
     */
    private MutableDoubleArrayND create()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(4, 4);

        a0.set(IntTuples.of(0, 0), 0);
        a0.set(IntTuples.of(0, 1), 1);
        a0.set(IntTuples.of(0, 2), 2);
        a0.set(IntTuples.of(0, 3), 3);

        a0.set(IntTuples.of(1, 0), 10);
        a0.set(IntTuples.of(1, 1), 11);
        a0.set(IntTuples.of(1, 2), 12);
        a0.set(IntTuples.of(1, 3), 13);

        a0.set(IntTuples.of(2, 0), 20);
        a0.set(IntTuples.of(2, 1), 21);
        a0.set(IntTuples.of(2, 2), 22);
        a0.set(IntTuples.of(2, 3), 23);

        a0.set(IntTuples.of(3, 0), 30);
        a0.set(IntTuples.of(3, 1), 31);
        a0.set(IntTuples.of(3, 2), 32);
        a0.set(IntTuples.of(3, 3), 33);
        
        return a0;
    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreation()
    {
        MutableDoubleArrayND a0 = create();
        
        MutableDoubleArrayND s0 = 
            a0.subArray(IntTuples.of(1,1), IntTuples.of(3,3));
        
        assertEquals(s0.getSize(), IntTuples.of(2,2));
        assertEquals(2 * 2, s0.getTotalSize());
    }
    
    @Test
    public void testNegativeMin()
    {
        MutableDoubleArrayND a0 = create();
        
        thrown.expect(IllegalArgumentException.class);
        a0.subArray(IntTuples.of(-1,-1), IntTuples.of(3,3));
    }

    @Test
    public void testMaxTooLarge()
    {
        MutableDoubleArrayND a0 = create();
        
        thrown.expect(IllegalArgumentException.class);
        a0.subArray(IntTuples.of(1,1), IntTuples.of(111,111));
    }
    
    @Test
    public void testMinGreaterThanMax()
    {
        MutableDoubleArrayND a0 = create();
        
        thrown.expect(IllegalArgumentException.class);
        a0.subArray(IntTuples.of(2,2), IntTuples.of(1,1));
    }
    
    @Test
    public void testWrongDimensions()
    {
        MutableDoubleArrayND a0 = create();
        
        thrown.expect(IllegalArgumentException.class);
        a0.subArray(IntTuples.of(1,1), IntTuples.of(3,3,1,1,1,1));
    }
    
    @Test
    public void testWriteThrough()
    {
        MutableDoubleArrayND a0 = create();
        
        MutableDoubleArrayND s0 = 
            a0.subArray(IntTuples.of(1,1), IntTuples.of(3,3));
        
        s0.set(IntTuples.of(0, 0), 123);
        assertEquals(a0.get(IntTuples.of(1, 1)), 123, 0.0);
        
        a0.set(IntTuples.of(2, 2), 234);
        assertEquals(s0.get(IntTuples.of(1, 1)), 234, 0.0);
        
    }
    
    @Test
    public void testWriteThroughTwoLevels()
    {
        MutableDoubleArrayND a0 = create();
        
        MutableDoubleArrayND s0 = 
            a0.subArray(IntTuples.of(1,1), IntTuples.of(3,3));

        MutableDoubleArrayND s1 = 
            s0.subArray(IntTuples.of(1,1), IntTuples.of(2,2));
        
        s1.set(IntTuples.of(0, 0), 123);
        assertEquals(a0.get(IntTuples.of(2, 2)), 123, 0.0);
        
        a0.set(IntTuples.of(2, 2), 234);
        assertEquals(s1.get(IntTuples.of(0, 0)), 234, 0.0);
        
    }
    
    
    


}