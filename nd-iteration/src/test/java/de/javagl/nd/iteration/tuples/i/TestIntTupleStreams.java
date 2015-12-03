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
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.javagl.nd.iteration.tuples.i.IntTupleStreams;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;

@SuppressWarnings("javadoc")
public class TestIntTupleStreams
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLexicographicalStream()
    {
        Stream<MutableIntTuple> stream = 
            IntTupleStreams.lexicographicalStream(
                IntTuples.of(2,3,4));

        Iterator<MutableIntTuple> iterator = stream.iterator();
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
    public void testLexicographicalStreamMinMax()
    {
        Stream<MutableIntTuple> stream = 
            IntTupleStreams.lexicographicalStream(
                IntTuples.of(1,1,1), IntTuples.of(2,3,4));

        Iterator<MutableIntTuple> iterator = stream.iterator();
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
    public void testLexicographicalStreamMinMaxDifferentSize()
    {
        thrown.expect(IllegalArgumentException.class);
        IntTupleStreams.lexicographicalStream(
            IntTuples.of(1,1,1), IntTuples.of(2,3,4,9,9,9,9));
    }

    
    @Test
    public void testColexicographicalStream()
    {
        Stream<MutableIntTuple> stream = 
            IntTupleStreams.colexicographicalStream(
                IntTuples.of(2,3,4));

        Iterator<MutableIntTuple> iterator = stream.iterator();
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
    public void testColexicographicalStreamMinMax()
    {
        Stream<MutableIntTuple> stream = 
            IntTupleStreams.colexicographicalStream(
                IntTuples.of(1,1,1), IntTuples.of(2,3,4));

        Iterator<MutableIntTuple> iterator = stream.iterator();
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
    public void testColexicographicalStreamMinMaxDifferentSize()
    {
        thrown.expect(IllegalArgumentException.class);
        IntTupleStreams.colexicographicalStream(
            IntTuples.of(1,1,1), IntTuples.of(2,3,4,9,9,9,9));
    }
    
    @Test
    public void testWrappingStream()
    {
        Stream<MutableIntTuple> delegate = 
            IntTupleStreams.lexicographicalStream(
                IntTuples.of(-1,-1), IntTuples.of(4,4));
        Stream<MutableIntTuple> stream = 
            IntTupleStreams.wrappingStream(
                IntTuples.of(2,2), delegate);
        Iterator<MutableIntTuple> iterator = stream.iterator();
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
    public void testClampingStream()
    {
        Stream<MutableIntTuple> delegate = 
            IntTupleStreams.lexicographicalStream(
                IntTuples.of(-1,-1), IntTuples.of(4,4));
        Stream<MutableIntTuple> stream = 
            IntTupleStreams.clampingStream(
                IntTuples.of(0,0), IntTuples.of(2,2), delegate);
        Iterator<MutableIntTuple> iterator = stream.iterator();
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
