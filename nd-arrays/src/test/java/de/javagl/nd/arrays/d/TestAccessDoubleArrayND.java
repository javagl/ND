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
public class TestAccessDoubleArrayND
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGet()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(2, 3, 4);
        assertEquals(0, a0.get(IntTuples.of(1, 1, 1)), 0);
    }
    
    @Test
    public void testGetInvalidDimensions()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(2, 3, 4);

        thrown.expect(IllegalArgumentException.class);
        a0.get(IntTuples.of(1, 1, 1, 1, 1, 1));
    }

    @Test
    public void testGetNegative()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(2, 3, 4);

        // Actually, this is not guaranteed to be thrown:
        thrown.expect(IndexOutOfBoundsException.class);
        a0.get(IntTuples.of(-1, 1, 1));
    }

    @Test
    public void testGetGreaterThanSize()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(2, 3, 4);

        // Actually, this is not guaranteed to be thrown:
        thrown.expect(IndexOutOfBoundsException.class);
        a0.get(IntTuples.of(100, 1, 1));
    }

    @Test
    public void testSet()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(2, 3, 4);
        a0.set(IntTuples.of(1, 1, 1), 1);
        assertEquals(1, a0.get(IntTuples.of(1, 1, 1)), 0);
    }

    @Test
    public void testSetInvalidDimensions()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(2, 3, 4);

        thrown.expect(IllegalArgumentException.class);
        a0.set(IntTuples.of(1, 1, 1, 1, 1, 1), 1);
    }
    
    @Test
    public void testSetNegative()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(2, 3, 4);

        thrown.expect(IndexOutOfBoundsException.class);
        a0.set(IntTuples.of(-1, 1, 1), 1);
    }

    @Test
    public void testSetGreaterThanSize()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(2, 3, 4);

        thrown.expect(IndexOutOfBoundsException.class);
        a0.set(IntTuples.of(100, 1, 1), 1);
    }
    
    

}