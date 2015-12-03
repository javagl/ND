/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.iteration.tuples.i;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.javagl.nd.iteration.tuples.i.IntTupleIterators;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;

@SuppressWarnings("javadoc")
public class TestIntTupleIterators
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLexicographicalIterator()
    {
        Iterator<MutableIntTuple> iterator = 
            IntTupleIterators.lexicographicalIterator(
                IntTuples.of(1,1,1), IntTuples.of(2,3,4));
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
    public void testLexicographicalIteratorEmpty()
    {
        Iterator<MutableIntTuple> iterator = 
            IntTupleIterators.lexicographicalIterator(
                IntTuples.of(3,3,3), IntTuples.of(2,2,2));
        assertFalse(iterator.hasNext());
    }
    
    

    @Test
    public void testColexicographicalIterator()
    {
        Iterator<MutableIntTuple> iterator = 
            IntTupleIterators.colexicographicalIterator(
                IntTuples.of(1,1,1), IntTuples.of(2,3,4));
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
    public void testColexicographicalIteratorEmpty()
    {
        Iterator<MutableIntTuple> iterator = 
            IntTupleIterators.colexicographicalIterator(
                IntTuples.of(3,3,3), IntTuples.of(2,2,2));
        assertFalse(iterator.hasNext());
    }
    

    @Test
    public void testWrappingIterator()
    {
        Iterator<MutableIntTuple> delegate = 
            IntTupleIterators.lexicographicalIterator(
                IntTuples.of(-1,-1), IntTuples.of(4,4));
        Iterator<MutableIntTuple> iterator = 
            IntTupleIterators.wrappingIterator(
                IntTuples.of(2,2), delegate);
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
    public void testClampingIterator()
    {
        Iterator<MutableIntTuple> delegate = 
            IntTupleIterators.lexicographicalIterator(
                IntTuples.of(-1,-1), IntTuples.of(4,4));
        Iterator<MutableIntTuple> iterator = 
            IntTupleIterators.clampingIterator(
                IntTuples.of(0,0), IntTuples.of(2,2), delegate);
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
