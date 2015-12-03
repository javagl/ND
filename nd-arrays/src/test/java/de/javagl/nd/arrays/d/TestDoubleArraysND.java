/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.arrays.d;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.javagl.nd.tuples.i.IntTuples;

@SuppressWarnings("javadoc")
public class TestDoubleArraysND
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
    
    @Test
    public void testMinMax()
    {
        MutableDoubleArrayND a0 = create();
        assertEquals(0, DoubleArraysND.min(a0), 0.0);
        assertEquals(33, DoubleArraysND.max(a0), 0.0);
    }
    
    @Test
    public void testMinMaxOfEmpty()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(0, 0);
        assertEquals(Double.POSITIVE_INFINITY, DoubleArraysND.min(a0), 0.0);
        assertEquals(Double.NEGATIVE_INFINITY, DoubleArraysND.max(a0), 0.0);
    }
    
    @Test
    public void testStream()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(100, 100);
        DoubleArrayFunctionsND.set(a0, () -> 1.0);
        
        double sumS = a0.stream().reduce(0.0, (x,y) -> x + y);
        assertEquals(sumS, 100.0 * 100.0, 0.0);
        
        double sumP = a0.stream().parallel().reduce(0.0, (x,y) -> x + y);
        assertEquals(sumP, 100.0 * 100.0, 0.0);
    }
    


}