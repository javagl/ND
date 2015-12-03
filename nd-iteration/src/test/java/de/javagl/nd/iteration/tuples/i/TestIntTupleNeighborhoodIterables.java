/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.iteration.tuples.i;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.javagl.nd.iteration.tuples.i.IntTupleNeighborhoodIterables;
import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;

@SuppressWarnings("javadoc")
public class TestIntTupleNeighborhoodIterables
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testMooreNeighborhoodIterable()
    {
        //   0 1 2 3 4 5 6 7 8 
        // 0
        // 1
        // 2
        // 3       2 2 2 2 2
        // 4       2 1 1 1 2
        // 5       2 1 0 1 2
        // 6       2 1 1 1 2
        // 7       2 2 2 2 2
        // 8
        
        IntTuple center = IntTuples.of(5,5);
        int radius = 2;
        Iterable<MutableIntTuple> iterable = 
            IntTupleNeighborhoodIterables.mooreNeighborhoodIterable(
                center, radius, null, null, Order.LEXICOGRAPHICAL);
        Iterator<MutableIntTuple> iterator = iterable.iterator();
        List<IntTuple> actual = new ArrayList<IntTuple>();
        while (iterator.hasNext())
        {
            actual.add(iterator.next());
        }
        List<IntTuple> expected = Arrays.asList(
            IntTuples.of(3, 3),
            IntTuples.of(3, 4),
            IntTuples.of(3, 5),
            IntTuples.of(3, 6),
            IntTuples.of(3, 7),
            IntTuples.of(4, 3),
            IntTuples.of(4, 4),
            IntTuples.of(4, 5),
            IntTuples.of(4, 6),
            IntTuples.of(4, 7),
            IntTuples.of(5, 3),
            IntTuples.of(5, 4),
            IntTuples.of(5, 5),
            IntTuples.of(5, 6),
            IntTuples.of(5, 7),
            IntTuples.of(6, 3),
            IntTuples.of(6, 4),
            IntTuples.of(6, 5),
            IntTuples.of(6, 6),
            IntTuples.of(6, 7),
            IntTuples.of(7, 3),
            IntTuples.of(7, 4),
            IntTuples.of(7, 5),
            IntTuples.of(7, 6),
            IntTuples.of(7, 7)
        );
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void testVonNeumannNeighborhoodIterable()
    {
        //   0 1 2 3 4 5 6 7 8 
        // 0
        // 1
        // 2
        // 3           2
        // 4         2 1 2
        // 5       2 1 0 1 2
        // 6         2 1 2
        // 7           2
        // 8
        
        IntTuple center = IntTuples.of(5,5);
        int radius = 2;
        Iterable<MutableIntTuple> iterable = 
            IntTupleNeighborhoodIterables.vonNeumannNeighborhoodIterable(
                center, radius);
        Iterator<MutableIntTuple> iterator = iterable.iterator();
        List<IntTuple> actual = new ArrayList<IntTuple>();
        while (iterator.hasNext())
        {
            actual.add(iterator.next());
        }
        List<IntTuple> expected = Arrays.asList(
            IntTuples.of(3, 5),
            IntTuples.of(4, 4),
            IntTuples.of(4, 5),
            IntTuples.of(4, 6),
            IntTuples.of(5, 3),
            IntTuples.of(5, 4),
            IntTuples.of(5, 5),
            IntTuples.of(5, 6),
            IntTuples.of(5, 7),
            IntTuples.of(6, 4),
            IntTuples.of(6, 5),
            IntTuples.of(6, 6),
            IntTuples.of(7, 5)
        );
        assertEquals(expected, actual);
    }
    

}
