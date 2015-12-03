/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.distance.tuples.i;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.javagl.nd.distance.tuples.i.MathUtils;

@SuppressWarnings("javadoc")
public class TestMathUtils
{
    @Test
    public void testWrap()
    {
        assertEquals(MathUtils.wrap(-7, 3),  2);
        assertEquals(MathUtils.wrap(-6, 3),  0);
        assertEquals(MathUtils.wrap(-5, 3),  1);
        assertEquals(MathUtils.wrap(-4, 3),  2);
        assertEquals(MathUtils.wrap(-3, 3),  0);
        assertEquals(MathUtils.wrap(-2, 3),  1);
        assertEquals(MathUtils.wrap(-1, 3),  2);
        assertEquals(MathUtils.wrap( 0, 3),  0);
        assertEquals(MathUtils.wrap( 1, 3),  1);
        assertEquals(MathUtils.wrap( 2, 3),  2);
        assertEquals(MathUtils.wrap( 3, 3),  0);
        assertEquals(MathUtils.wrap( 4, 3),  1);
        assertEquals(MathUtils.wrap( 5, 3),  2);
        assertEquals(MathUtils.wrap( 6, 3),  0);
        assertEquals(MathUtils.wrap( 7, 3),  1);
        
        assertEquals(MathUtils.wrap(-7, -3),  2);
        assertEquals(MathUtils.wrap(-6, -3),  0);
        assertEquals(MathUtils.wrap(-5, -3),  1);
        assertEquals(MathUtils.wrap(-4, -3),  2);
        assertEquals(MathUtils.wrap(-3, -3),  0);
        assertEquals(MathUtils.wrap(-2, -3),  1);
        assertEquals(MathUtils.wrap(-1, -3),  2);
        assertEquals(MathUtils.wrap( 0, -3),  0);
        assertEquals(MathUtils.wrap( 1, -3),  1);
        assertEquals(MathUtils.wrap( 2, -3),  2);
        assertEquals(MathUtils.wrap( 3, -3),  0);
        assertEquals(MathUtils.wrap( 4, -3),  1);
        assertEquals(MathUtils.wrap( 5, -3),  2);
        assertEquals(MathUtils.wrap( 6, -3),  0);
        assertEquals(MathUtils.wrap( 7, -3),  1);
        
    }
    
    @Test
    public void testWrappedDistance()
    {
        assertEquals(MathUtils.wrappedDistance(0,  9, 10), 1);
        assertEquals(MathUtils.wrappedDistance(0, 10, 10), 0);
        assertEquals(MathUtils.wrappedDistance(0, 11, 10), 1);
        assertEquals(MathUtils.wrappedDistance(1, -4, 10), 5);
    }
                
}
