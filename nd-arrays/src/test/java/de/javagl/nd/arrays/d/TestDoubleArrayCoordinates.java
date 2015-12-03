/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.arrays.d;
import static org.junit.Assert.assertEquals;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;

@SuppressWarnings("javadoc")
public class TestDoubleArrayCoordinates
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCoordinates()
    {
        MutableDoubleArrayND a0 = DoubleArraysND.create(2, 3, 4);
        
        Set<IntTuple> expected = new LinkedHashSet<IntTuple>();
        expected.add(IntTuples.of(0, 0, 0));
        expected.add(IntTuples.of(0, 0, 1));
        expected.add(IntTuples.of(0, 0, 2));
        expected.add(IntTuples.of(0, 0, 3));
        expected.add(IntTuples.of(0, 1, 0));
        expected.add(IntTuples.of(0, 1, 1));
        expected.add(IntTuples.of(0, 1, 2));
        expected.add(IntTuples.of(0, 1, 3));
        expected.add(IntTuples.of(0, 2, 0));
        expected.add(IntTuples.of(0, 2, 1));
        expected.add(IntTuples.of(0, 2, 2));
        expected.add(IntTuples.of(0, 2, 3));
        expected.add(IntTuples.of(1, 0, 0));
        expected.add(IntTuples.of(1, 0, 1));
        expected.add(IntTuples.of(1, 0, 2));
        expected.add(IntTuples.of(1, 0, 3));
        expected.add(IntTuples.of(1, 1, 0));
        expected.add(IntTuples.of(1, 1, 1));
        expected.add(IntTuples.of(1, 1, 2));
        expected.add(IntTuples.of(1, 1, 3));
        expected.add(IntTuples.of(1, 2, 0));
        expected.add(IntTuples.of(1, 2, 1));
        expected.add(IntTuples.of(1, 2, 2));
        expected.add(IntTuples.of(1, 2, 3));
        
        List<IntTuple> actualList = 
            a0.coordinates().collect(Collectors.<IntTuple>toList());
        Set<IntTuple> actual = new LinkedHashSet<IntTuple>(actualList);
        
        assertEquals(expected.size(), actualList.size());
        assertEquals(expected, actual);
    }
    

}