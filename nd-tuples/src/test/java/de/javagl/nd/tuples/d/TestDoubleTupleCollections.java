/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.tuples.d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

@SuppressWarnings("javadoc")
public class TestDoubleTupleCollections
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<MutableDoubleTuple> create()
    {
        List<MutableDoubleTuple> ts = new ArrayList<MutableDoubleTuple>();
        ts.add(DoubleTuples.of(0.0, 1.0, 2.0));
        ts.add(DoubleTuples.of(0.1, 1.1, 2.1));
        ts.add(DoubleTuples.of(0.2, 1.2, 2.2));
        ts.add(DoubleTuples.of(0.3, 1.3, 2.3));
        return ts;
    }
    
    @Test
    public void testCreate()
    {
        List<MutableDoubleTuple> ts0 = DoubleTupleCollections.create(3, 10);
        assertEquals(10, ts0.size());
        assertEquals(3, ts0.get(0).getSize());
    }
    
    @Test
    public void testDeepCopy()
    {
        List<MutableDoubleTuple> ts0 = create();
        List<MutableDoubleTuple> ts1 = create();
        List<MutableDoubleTuple> copy = DoubleTupleCollections.deepCopy(ts0);
        copy.get(0).set(0,123);
        
        assertEquals(ts0, ts1);
        assertNotEquals(ts0, copy);
    }
    
    @Test
    public void testMin()
    {
        List<MutableDoubleTuple> ts0 = create();
        MutableDoubleTuple actual = DoubleTupleCollections.min(ts0, null);
        MutableDoubleTuple expected = DoubleTuples.of(0.0, 1.0, 2.0);
        assertEquals(expected, actual);
    }

    @Test
    public void testMinWrongDimension()
    {
        List<MutableDoubleTuple> ts0 = create();
        MutableDoubleTuple result = DoubleTuples.create(123);
        
        thrown.expect(IllegalArgumentException.class);
        DoubleTupleCollections.min(ts0, result);
    }
    
    @Test
    public void testMax()
    {
        List<MutableDoubleTuple> ts0 = create();
        MutableDoubleTuple actual = DoubleTupleCollections.max(ts0, null);
        MutableDoubleTuple expected = DoubleTuples.of(0.3, 1.3, 2.3);
        assertEquals(expected, actual);
    }

    @Test
    public void testMaxWrongDimension()
    {
        List<MutableDoubleTuple> ts0 = create();
        MutableDoubleTuple result = DoubleTuples.create(123);
        
        thrown.expect(IllegalArgumentException.class);
        DoubleTupleCollections.min(ts0, result);
    }
    
    @Test
    public void testAdd()
    {
        List<MutableDoubleTuple> ts0 = create();
        MutableDoubleTuple actual = DoubleTupleCollections.add(ts0, null);
        MutableDoubleTuple expected = DoubleTuples.of(0.6, 4.6, 8.6);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testStandardize()
    {
        List<MutableDoubleTuple> ts = new ArrayList<MutableDoubleTuple>();
        ts.add(DoubleTuples.of(8.0, 1.0, 1.0));
        ts.add(DoubleTuples.of(6.0, 1.1, 1.0));
        ts.add(DoubleTuples.of(4.0, 1.2, 2.0));
        ts.add(DoubleTuples.of(2.0, 1.3, 2.0));
        
        List<MutableDoubleTuple> actual = 
            DoubleTupleCollections.standardize(ts, null);
        List<MutableDoubleTuple> expected = Arrays.asList(
            DoubleTuples.of(1.3416407864998738, -1.341640786499873, -1.0), 
            DoubleTuples.of(0.4472135954999579, -0.4472135954999564, -1.0), 
            DoubleTuples.of(-0.4472135954999579, 0.44721359549995837, 1.0), 
            DoubleTuples.of(-1.3416407864998738, 1.341640786499875, 1.0)
        );
        assertEquals(expected.size(), actual.size());
        for (int i=0; i<expected.size(); i++)
        {
            DoubleTuple e = expected.get(i);
            DoubleTuple a = actual.get(i);
            assertTrue(DoubleTuples.epsilonEquals(e, a, 1e-8));
        }
        
        MutableDoubleTuple newMean = 
            DoubleTupleCollections.arithmeticMean(actual, null);
        MutableDoubleTuple newStandardDeviation =
            DoubleTupleCollections.standardDeviationFromMean(
                actual, newMean, null);
        assertTrue(DoubleTuples.epsilonEquals(newMean, 
            DoubleTuples.constant(3, 0.0), 1e-8));
        assertTrue(DoubleTuples.epsilonEquals(newStandardDeviation, 
            DoubleTuples.constant(3, 1.0), 1e-8));
    }
    
    
    @Test
    public void testNormalize()
    {
        List<MutableDoubleTuple> ts = create();
        List<MutableDoubleTuple> actual = 
            DoubleTupleCollections.normalize(ts, null);
        
        List<MutableDoubleTuple> expected = Arrays.asList(
            DoubleTuples.of(0.0,           0.44721359549, 0.89442719099),
            DoubleTuples.of(0.04214497519, 0.46359472715, 0.88504447911),
            DoubleTuples.of(0.07955572841, 0.47733437050, 0.87511301259),
            DoubleTuples.of(0.11282661216, 0.48891531938, 0.86500402661)
        );
        assertEquals(expected.size(), actual.size());
        for (int i=0; i<expected.size(); i++)
        {
            DoubleTuple e = expected.get(i);
            DoubleTuple a = actual.get(i);
            assertTrue(DoubleTuples.epsilonEquals(e, a, 1e-8));
            assertEquals(1.0, DoubleTuples.computeL2(a), 1e-8);
        }
    }
    
    /* These are actually covered with "standardize"...
    @Test
    public void testArithmeticMean()
    {
    }
    
    @Test
    public void testVariance()
    {
    }
    
    @Test
    public void testVariance2()
    {
    }
    
    @Test
    public void testStandardDeviation()
    {
    }
    
    @Test
    public void testStandardDeviationFromMean()
    {
    }
    */
    
}