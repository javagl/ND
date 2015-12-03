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

import de.javagl.nd.iteration.tuples.i.IntTupleIterables;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;

@SuppressWarnings("javadoc")
public class TestIntTupleIterables
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLexicographicalIterable()
    {
        Iterable<MutableIntTuple> iterable = 
            IntTupleIterables.lexicographicalIterable(
                IntTuples.of(2,3,4));

        Iterator<MutableIntTuple> iterator = iterable.iterator();
        List<IntTuple> actual = new ArrayList<IntTuple>();
        while (iterator.hasNext())
        {
            actual.add(iterator.next());
        }
        List<IntTuple> expected = Arrays.asList(
            IntTuples.of(0,0,0),
            IntTuples.of(0,0,1),
            IntTuples.of(0,0,2),
            IntTuples.of(0,0,3),
            
            IntTuples.of(0,1,0),
            IntTuples.of(0,1,1),
            IntTuples.of(0,1,2),
            IntTuples.of(0,1,3),
            
            IntTuples.of(0,2,0),
            IntTuples.of(0,2,1),
            IntTuples.of(0,2,2),
            IntTuples.of(0,2,3),
            
            IntTuples.of(1,0,0),
            IntTuples.of(1,0,1),
            IntTuples.of(1,0,2),
            IntTuples.of(1,0,3),
            
            IntTuples.of(1,1,0),
            IntTuples.of(1,1,1),
            IntTuples.of(1,1,2),
            IntTuples.of(1,1,3),
            
            IntTuples.of(1,2,0),
            IntTuples.of(1,2,1),
            IntTuples.of(1,2,2),
            IntTuples.of(1,2,3)
        );
        
        assertEquals(expected, actual);
    }

    @Test
    public void testLexicographicalIterableMinMax()
    {
        Iterable<MutableIntTuple> iterable = 
            IntTupleIterables.lexicographicalIterable(
                IntTuples.of(1,1,1), IntTuples.of(2,3,4));

        Iterator<MutableIntTuple> iterator = iterable.iterator();
        List<IntTuple> actual = new ArrayList<IntTuple>();
        while (iterator.hasNext())
        {
            actual.add(iterator.next());
        }
        List<IntTuple> expected = Arrays.asList(
            IntTuples.of(1,1,1),
            IntTuples.of(1,1,2),
            IntTuples.of(1,1,3),
            
            IntTuples.of(1,2,1),
            IntTuples.of(1,2,2),
            IntTuples.of(1,2,3)
        );
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testLexicographicalIterableMinMaxDifferentSize()
    {
        thrown.expect(IllegalArgumentException.class);
        IntTupleIterables.lexicographicalIterable(
            IntTuples.of(1,1,1), IntTuples.of(2,3,4,9,9,9,9));
    }

    
    @Test
    public void testColexicographicalIterable()
    {
        Iterable<MutableIntTuple> iterable = 
            IntTupleIterables.colexicographicalIterable(
                IntTuples.of(2,3,4));

        Iterator<MutableIntTuple> iterator = iterable.iterator();
        List<IntTuple> actual = new ArrayList<IntTuple>();
        while (iterator.hasNext())
        {
            actual.add(iterator.next());
        }
        List<IntTuple> expected = Arrays.asList(
            IntTuples.of(0,0,0),
            IntTuples.of(1,0,0),
            IntTuples.of(0,1,0),
            IntTuples.of(1,1,0),
            IntTuples.of(0,2,0),
            IntTuples.of(1,2,0),

            IntTuples.of(0,0,1),
            IntTuples.of(1,0,1),
            IntTuples.of(0,1,1),
            IntTuples.of(1,1,1),
            IntTuples.of(0,2,1),
            IntTuples.of(1,2,1),

            IntTuples.of(0,0,2),
            IntTuples.of(1,0,2),
            IntTuples.of(0,1,2),
            IntTuples.of(1,1,2),
            IntTuples.of(0,2,2),
            IntTuples.of(1,2,2),

            IntTuples.of(0,0,3),
            IntTuples.of(1,0,3),
            IntTuples.of(0,1,3),
            IntTuples.of(1,1,3),
            IntTuples.of(0,2,3),
            IntTuples.of(1,2,3)
        );
        
        assertEquals(expected, actual);
    }

    @Test
    public void testColexicographicalIterableMinMax()
    {
        Iterable<MutableIntTuple> iterable = 
            IntTupleIterables.colexicographicalIterable(
                IntTuples.of(1,1,1), IntTuples.of(2,3,4));

        Iterator<MutableIntTuple> iterator = iterable.iterator();
        List<IntTuple> actual = new ArrayList<IntTuple>();
        while (iterator.hasNext())
        {
            actual.add(iterator.next());
        }
        List<IntTuple> expected = Arrays.asList(
            IntTuples.of(1,1,1),
            IntTuples.of(1,2,1),

            IntTuples.of(1,1,2),
            IntTuples.of(1,2,2),

            IntTuples.of(1,1,3),
            IntTuples.of(1,2,3)
        );
        assertEquals(expected, actual);
    }
    
    @Test
    public void testColexicographicalIterableMinMaxDifferentSize()
    {
        thrown.expect(IllegalArgumentException.class);
        IntTupleIterables.colexicographicalIterable(
            IntTuples.of(1,1,1), IntTuples.of(2,3,4,9,9,9,9));
    }
    
    @Test
    public void testWrappingIterable()
    {
        Iterable<MutableIntTuple> delegate = 
            IntTupleIterables.lexicographicalIterable(
                IntTuples.of(-1,-1), IntTuples.of(4,4));
        Iterable<MutableIntTuple> iterable = 
            IntTupleIterables.wrappingIterable(
                IntTuples.of(2,2), delegate);
        Iterator<MutableIntTuple> iterator = iterable.iterator();
        List<IntTuple> actual = new ArrayList<IntTuple>();
        while (iterator.hasNext())
        {
            actual.add(iterator.next());
        }
        List<IntTuple> expected = Arrays.asList(
            IntTuples.of(1, 1),
            IntTuples.of(1, 0),
            IntTuples.of(1, 1),
            IntTuples.of(1, 0),
            IntTuples.of(1, 1),
            IntTuples.of(0, 1),
            IntTuples.of(0, 0),
            IntTuples.of(0, 1),
            IntTuples.of(0, 0),
            IntTuples.of(0, 1),
            IntTuples.of(1, 1),
            IntTuples.of(1, 0),
            IntTuples.of(1, 1),
            IntTuples.of(1, 0),
            IntTuples.of(1, 1),
            IntTuples.of(0, 1),
            IntTuples.of(0, 0),
            IntTuples.of(0, 1),
            IntTuples.of(0, 0),
            IntTuples.of(0, 1),
            IntTuples.of(1, 1),
            IntTuples.of(1, 0),
            IntTuples.of(1, 1),
            IntTuples.of(1, 0),
            IntTuples.of(1, 1)
        );
        assertEquals(expected, actual);
    }
    
    @Test
    public void testClampingIterable()
    {
        Iterable<MutableIntTuple> delegate = 
            IntTupleIterables.lexicographicalIterable(
                IntTuples.of(-1,-1), IntTuples.of(4,4));
        Iterable<MutableIntTuple> iterable = 
            IntTupleIterables.clampingIterable(
                IntTuples.of(0,0), IntTuples.of(2,2), delegate);
        Iterator<MutableIntTuple> iterator = iterable.iterator();
        List<IntTuple> actual = new ArrayList<IntTuple>();
        while (iterator.hasNext())
        {
            actual.add(iterator.next());
        }
        List<IntTuple> expected = Arrays.asList(
            IntTuples.of(0, 0),
            IntTuples.of(0, 1),
            IntTuples.of(1, 0),
            IntTuples.of(1, 1)
        );
        assertEquals(expected, actual);
    }

}
