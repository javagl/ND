/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.arrays;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;

import org.junit.Test;

import de.javagl.nd.iteration.tuples.i.IntTupleIterables;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;
import de.javagl.nd.tuples.i.MutableIntTuple;

@SuppressWarnings("javadoc")
public class IndexersTest
{
    @Test
    public void testLexicographicalIndexerLexicographicalIteration()
    {
        IntTuple size = IntTuples.of(2, 3);
        ToIntFunction<IntTuple> indexer =
            Indexers.lexicographicalIndexer(size);

        // 2 rows, 3 columns, incrementing the columns first
        // 0 1 2
        // 3 4 5
        List<Integer> expected = Arrays.asList(0,1,2,3,4,5);
        List<Integer> actual = new ArrayList<Integer>();
        Iterable<MutableIntTuple> iterable =
            IntTupleIterables.lexicographicalIterable(size);
        for (IntTuple t : iterable)
        {
            actual.add(indexer.applyAsInt(t));
        }
        assertEquals(expected, actual);
    }

    @Test
    public void testLexicographicalIndexerColexicographicalIteration()
    {
        IntTuple size = IntTuples.of(2, 3);
        ToIntFunction<IntTuple> indexer =
            Indexers.lexicographicalIndexer(size);

        // 2 rows, 3 columns, incrementing the rows first
        // 0 1 2
        // 3 4 5
        List<Integer> expected = Arrays.asList(0,3,1,4,2,5);
        List<Integer> actual = new ArrayList<Integer>();
        Iterable<MutableIntTuple> iterable =
            IntTupleIterables.colexicographicalIterable(size);
        for (IntTuple t : iterable)
        {
            actual.add(indexer.applyAsInt(t));
        }
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void testColexicographicalIndexerLexicographicalIteration()
    {
        IntTuple size = IntTuples.of(2, 3);
        ToIntFunction<IntTuple> indexer =
            Indexers.colexicographicalIndexer(size);

        // 2 rows, 3 columns, incrementing the columns first
        // 0 2 4
        // 1 3 5
        List<Integer> expected = Arrays.asList(0,2,4,1,3,5);
        List<Integer> actual = new ArrayList<Integer>();
        Iterable<MutableIntTuple> iterable =
            IntTupleIterables.lexicographicalIterable(size);
        for (IntTuple t : iterable)
        {
            actual.add(indexer.applyAsInt(t));
        }
        assertEquals(expected, actual);
    }

    @Test
    public void testColexicographicalIndexerColexicographicalIteration()
    {
        IntTuple size = IntTuples.of(2, 3);
        ToIntFunction<IntTuple> indexer =
            Indexers.colexicographicalIndexer(size);

        // 2 rows, 3 columns, incrementing the rows first
        // 0 2 4
        // 1 3 5
        List<Integer> expected = Arrays.asList(0,1,2,3,4,5);
        List<Integer> actual = new ArrayList<Integer>();
        Iterable<MutableIntTuple> iterable =
            IntTupleIterables.colexicographicalIterable(size);
        for (IntTuple t : iterable)
        {
            actual.add(indexer.applyAsInt(t));
        }
        assertEquals(expected, actual);
    }
    
}
