/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 */ 
package de.javagl.nd.tuples.d;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

@SuppressWarnings("javadoc")
public class TestDoubleTuples
{
    private static final double DOUBLE_EPSILON = 1e-8;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private MutableDoubleTuple create()
    {
        return DoubleTuples.of(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0);
    }
    
    @Test
    public void testToArray()
    {
        MutableDoubleTuple t0 = create();
        double expecteds[] = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0 };
        double actuals[] = DoubleTuples.toArray(t0);
        assertArrayEquals(expecteds, actuals, 0.0);
    }
    
    @Test
    public void testAsList()
    {
        MutableDoubleTuple t0 = create();
        List<Double> expected = 
            Arrays.asList(new Double[] { 
                0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0 });
        List<Double> actual = DoubleTuples.asList(t0);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testAsListWriteThrough()
    {
        MutableDoubleTuple t0 = create();
        List<Double> list = DoubleTuples.asList(t0);
        t0.set(0, 123);
        assertEquals(123, list.get(0), 0.0);
        list.set(1, 234.0);
        assertEquals(234, t0.get(1), 0.0);
    }
    
    @Test
    public void testSet()
    {
        MutableDoubleTuple t0 = create();
        DoubleTuples.set(t0, 123);
        MutableDoubleTuple expected = 
            DoubleTuples.of(123, 123, 123, 123, 123, 123, 123, 123);
        assertEquals(expected, t0);
    }

    @Test
    public void testReverseEven()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0,1,2,3);
        MutableDoubleTuple expected = DoubleTuples.of(3,2,1,0);
        MutableDoubleTuple actual = DoubleTuples.reverse(t0, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReverseOdd()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0,1,2,3,4);
        MutableDoubleTuple expected = DoubleTuples.of(4,3,2,1,0);
        MutableDoubleTuple actual = DoubleTuples.reverse(t0, null);
        assertEquals(expected, actual);
    }

    @Test
    public void testReverseEvenInPlace()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0,1,2,3);
        MutableDoubleTuple expected = DoubleTuples.of(3,2,1,0);
        DoubleTuples.reverse(t0, t0);
        assertEquals(expected, t0);
    }
    
    @Test
    public void testReverseOddInPlace()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0,1,2,3,4);
        MutableDoubleTuple expected = DoubleTuples.of(4,3,2,1,0);
        DoubleTuples.reverse(t0, t0);
        assertEquals(expected, t0);
    }

    @Test
    public void testReverseView()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0,1,2,3,4);
        MutableDoubleTuple expected = DoubleTuples.of(4,3,2,1,0);
        MutableDoubleTuple reversed = DoubleTuples.reversed(t0);
        assertEquals(expected, reversed);
        
        t0.set(0, 123.0);
        assertEquals(reversed.get(4), 123.0, 0.0);
        
        reversed.set(0, 234.0);
        assertEquals(t0.get(4), 234.0, 0.0);
    }

    @Test
    public void testInsertElementAt()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0,1,3,4);
        MutableDoubleTuple expected = DoubleTuples.of(0,1,2,3,4);
        MutableDoubleTuple actual = 
            DoubleTuples.insertElementAt(t0, 2, 2.0, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testInsertElementAtWithInvalidResult()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0,1,3,4);
        MutableDoubleTuple result = DoubleTuples.of(0,1,3,4);
        thrown.expect(IllegalArgumentException.class);
        DoubleTuples.insertElementAt(t0, 2, 2.0, result);
    }
    
    @Test
    public void testInsertElementAtWithInvalidIndex()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0,1,2,3);
        thrown.expect(IndexOutOfBoundsException.class);
        DoubleTuples.insertElementAt(t0, 123, 2.0, null);
    }

    @Test
    public void testInsertElementAtWithLastValidIndex()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0,1,2,3);
        MutableDoubleTuple expected = DoubleTuples.of(0,1,2,3,4);
        MutableDoubleTuple actual = 
            DoubleTuples.insertElementAt(t0, 4, 4.0, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testRemoveElementAt()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0,1,2,3);
        MutableDoubleTuple expected = DoubleTuples.of(0,1,3);
        MutableDoubleTuple actual = 
            DoubleTuples.removeElementAt(t0, 2, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testRemoveElementAtWithInvalidResult()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0,1,2,3);
        MutableDoubleTuple result = DoubleTuples.of(0,1,2,3);
        thrown.expect(IllegalArgumentException.class);
        DoubleTuples.removeElementAt(t0, 2, result);
    }
    
    @Test
    public void testRemoveElementWithInvalidIndex()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(0,1,2,3);
        thrown.expect(IndexOutOfBoundsException.class);
        DoubleTuples.removeElementAt(t0, 123, null);
    }

    @Test
    public void testClampElement()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple expected = 
            DoubleTuples.of(2.0, 2.0, 2.0, 3.0, 4.0, 5.0, 5.0, 5.0);
        MutableDoubleTuple actual = 
            DoubleTuples.clamp(t0, 2.0, 5.0, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testClamp()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple min = 
            DoubleTuples.of(123.0, 1.0, 2.0, -234.0, -234.0, 5.0, 5.0, 123.0);
        MutableDoubleTuple max = 
            DoubleTuples.of(234.0, 1.0, 2.0, -123.0, -123.0, 5.0, 5.0, 234.0);
        MutableDoubleTuple expected = 
            DoubleTuples.of(123.0, 1.0, 2.0, -123.0, -123.0, 5.0, 5.0, 123.0);
        MutableDoubleTuple actual = 
            DoubleTuples.clamp(t0, min, max, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testNegate()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple expected = 
            DoubleTuples.of(-0.0, -1.0, -2.0, -3.0, -4.0, -5.0, -6.0, -7.0);
        MutableDoubleTuple actual = 
            DoubleTuples.negate(t0, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testAddToElements()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple expected = 
            DoubleTuples.of(2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
        MutableDoubleTuple actual = 
            DoubleTuples.add(t0, 2.0, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSubtractFromElements()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple expected = 
            DoubleTuples.of(-1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
        MutableDoubleTuple actual = 
            DoubleTuples.subtract(t0, 1.0, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMultiplyElements()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple expected = 
            DoubleTuples.of(0.0, 3.0, 6.0, 9.0, 12.0, 15.0, 18.0, 21.0);
        MutableDoubleTuple actual = 
            DoubleTuples.multiply(t0, 3, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testDivide()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(4.0, 6.0, 8.0);
        MutableDoubleTuple t1 = DoubleTuples.of(2.0, 3.0, 4.0);
        DoubleTuple expected = DoubleTuples.of(2.0, 2.0, 2.0);
        MutableDoubleTuple actual = 
            DoubleTuples.divide(t0, t1, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testAdd()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple t1 = create();
        MutableDoubleTuple expected = 
            DoubleTuples.of(0.0, 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0);
        MutableDoubleTuple actual = 
            DoubleTuples.add(t0, t1, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSubtract()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple t1 = create();
        DoubleTuple expected = 
            DoubleTuples.constant(t0.getSize(), 0.0);
        MutableDoubleTuple actual = 
            DoubleTuples.subtract(t0, t1, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMultiply()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple t1 = create();
        DoubleTuple expected = 
            DoubleTuples.of(0.0, 1.0, 4.0, 9.0, 16.0, 25.0, 36.0, 49.0);
        MutableDoubleTuple actual = 
            DoubleTuples.multiply(t0, t1, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testAddScaled()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple t1 = create();
        DoubleTuple expected = 
            DoubleTuples.of(0.0, 3.0, 6.0, 9.0, 12.0, 15.0, 18.0, 21.0);
        MutableDoubleTuple actual = 
            DoubleTuples.addScaled(t0, 2, t1, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testDot()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple t1 = create();
        double actual = DoubleTuples.dot(t0, t1);
        assertEquals(140.0, actual, 0.0);
    }
    
    @Test
    public void testMinElement()
    {
        MutableDoubleTuple t0 = create();
        double actual = DoubleTuples.min(t0);
        assertEquals(0.0, actual, 0.0);
    }
    
    @Test
    public void testMaxElement()
    {
        MutableDoubleTuple t0 = create();
        double actual = DoubleTuples.max(t0);
        assertEquals(7.0, actual, 0.0);
    }
    
    @Test
    public void testMin()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple t1 = 
            DoubleTuples.of(3.0, -3.0, 3.0, -3.0, 3.0, -3.0, 3.0, -3.0);
        DoubleTuple expected = 
            DoubleTuples.of(0.0, -3.0, 2.0, -3.0, 3.0, -3.0, 3.0, -3.0);
        MutableDoubleTuple actual = 
            DoubleTuples.min(t0, t1, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMax()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple t1 = 
            DoubleTuples.of(3.0, -3.0, 3.0, -3.0, 3.0, -3.0, 3.0, -3.0);
        DoubleTuple expected = 
            DoubleTuples.of(3.0,  1.0, 3.0,  3.0, 4.0,  5.0, 6.0,  7.0);
        MutableDoubleTuple actual = 
            DoubleTuples.max(t0, t1, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testCompareLexicographically()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1, 1, 2);
        MutableDoubleTuple t1 = DoubleTuples.of(1, 2, 3);
        assertEquals(-1, DoubleTuples.compareLexicographically(t0, t1));
        assertEquals( 0, DoubleTuples.compareLexicographically(t0, t0));
        assertEquals( 1, DoubleTuples.compareLexicographically(t1, t0));
    }
    
    @Test
    public void testCompareColexicographically()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(2, 1, 3);
        MutableDoubleTuple t1 = DoubleTuples.of(1, 2, 3);
        assertEquals(-1, DoubleTuples.compareColexicographically(t0, t1));
        assertEquals( 0, DoubleTuples.compareColexicographically(t0, t0));
        assertEquals( 1, DoubleTuples.compareColexicographically(t1, t0));
    }
    
    @Test
    public void testAreElementsGreaterThan()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1, 2, 3);
        MutableDoubleTuple t1 = DoubleTuples.of(2, 3, 4);
        MutableDoubleTuple t2 = DoubleTuples.of(2, 2, 4);
        assertTrue (DoubleTuples.areElementsGreaterThan(t1, t0));
        assertFalse(DoubleTuples.areElementsGreaterThan(t2, t0));
    }
    
    @Test
    public void testAreElementsGreaterThanOrEqual()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1, 2, 3);
        MutableDoubleTuple t1 = DoubleTuples.of(2, 3, 4);
        MutableDoubleTuple t2 = DoubleTuples.of(2, 2, 4);
        MutableDoubleTuple t3 = DoubleTuples.of(1, 1, 3);
        assertTrue (DoubleTuples.areElementsGreaterThanOrEqual(t1, t0));
        assertTrue (DoubleTuples.areElementsGreaterThanOrEqual(t2, t0));
        assertFalse(DoubleTuples.areElementsGreaterThanOrEqual(t3, t0));
    }

    @Test
    public void testAreElementsLessThan()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1, 2, 3);
        MutableDoubleTuple t1 = DoubleTuples.of(0, 1, 2);
        MutableDoubleTuple t2 = DoubleTuples.of(0, 2, 2);
        assertTrue (DoubleTuples.areElementsLessThan(t1, t0));
        assertFalse(DoubleTuples.areElementsLessThan(t2, t0));
    }

    @Test
    public void testAreElementsLessThanOrEqual()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1, 2, 3);
        MutableDoubleTuple t1 = DoubleTuples.of(0, 1, 2);
        MutableDoubleTuple t2 = DoubleTuples.of(1, 1, 3);
        MutableDoubleTuple t3 = DoubleTuples.of(1, 3, 3);
        assertTrue (DoubleTuples.areElementsLessThanOrEqual(t1, t0));
        assertTrue (DoubleTuples.areElementsLessThanOrEqual(t2, t0));
        assertFalse(DoubleTuples.areElementsLessThanOrEqual(t3, t0));
    }
    

    
    @Test
    public void testAreElementsGreaterThanValue()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1, 2, 3);
        assertTrue (DoubleTuples.areElementsGreaterThan(t0, 0.0));
        assertFalse(DoubleTuples.areElementsGreaterThan(t0, 1.0));
    }
    
    @Test
    public void testAreElementsGreaterThanOrEqualValue()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1, 2, 3);
        assertTrue (DoubleTuples.areElementsGreaterThanOrEqual(t0, 0.0));
        assertTrue (DoubleTuples.areElementsGreaterThanOrEqual(t0, 1.0));
        assertFalse(DoubleTuples.areElementsGreaterThanOrEqual(t0, 2.0));
    }

    @Test
    public void testAreElementsLessThanValue()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1, 2, 3);
        assertTrue (DoubleTuples.areElementsLessThan(t0, 4.0));
        assertFalse(DoubleTuples.areElementsLessThan(t0, 3.0));
    }
    
    @Test
    public void testAreElementsLessThanOrEqualValue()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1, 2, 3);
        assertTrue (DoubleTuples.areElementsLessThanOrEqual(t0, 4.0));
        assertTrue (DoubleTuples.areElementsLessThanOrEqual(t0, 3.0));
        assertFalse(DoubleTuples.areElementsLessThanOrEqual(t0, 2.0));
    }
    
    
    
    @Test
    public void testNormalize()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple n = 
            DoubleTuples.normalize(t0, null);
        double length = DoubleTuples.computeL2(n);
        assertEquals(1.0, length, DOUBLE_EPSILON);
    }
    
    @Test
    public void testComputeL2()
    {
        MutableDoubleTuple t0 = create();
        double length = DoubleTuples.computeL2(t0);
        assertEquals(11.832159566199232, length, DOUBLE_EPSILON);
    }

    @Test
    public void testNormalizeElementsElement()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1.0, 2.0, 3.0);
        DoubleTuple expected = DoubleTuples.of(0.0, 50.0, 100.0);
        MutableDoubleTuple actual = 
            DoubleTuples.normalizeElements(t0, 0.0, 100.0, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testNormalizeElements()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1.0, 2.0, 3.0);
        DoubleTuple min = DoubleTuples.of(1.0, 1.0, 0.0);
        DoubleTuple max = DoubleTuples.of(2.0, 3.0, 3.0);
        DoubleTuple expected = DoubleTuples.of(0.0, 0.5, 1.0);
        MutableDoubleTuple actual = 
            DoubleTuples.normalizeElements(t0, min, max, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testRescaleElements()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1.0, 2.0, 3.0);
        DoubleTuple expected = DoubleTuples.of(0.0, 0.5, 1.0);
        MutableDoubleTuple actual = 
            DoubleTuples.rescaleElements(t0, 1.0, 3.0, 0.0, 1.0, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGeometricMean()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1.0, 2.0, 3.0, 4.0, 5.0);
        double expected = 2.605171084697352;
        double actual = DoubleTuples.geometricMean(t0);
        assertEquals(expected, actual, DOUBLE_EPSILON);
    }
    
    @Test
    public void testHarmonicMean()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1.0, 2.0, 3.0, 4.0, 5.0);
        double expected = 2.18978102189781;
        double actual = DoubleTuples.harmonicMean(t0);
        assertEquals(expected, actual, DOUBLE_EPSILON);
    }
    
    @Test
    public void testInterpolate()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1.0, 2.0, 3.0);
        MutableDoubleTuple t1 = DoubleTuples.of(2.0, 3.0, 4.0);
        DoubleTuple expected = DoubleTuples.of(1.5, 2.5, 3.5);
        MutableDoubleTuple actual = 
            DoubleTuples.interpolate(t0, t1, 0.5, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testStandardDeviationFromVariance()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1.0, 2.0, 3.0);
        DoubleTuple expected = DoubleTuples.of(
            Math.sqrt(1.0), Math.sqrt(2.0), Math.sqrt(3.0));
        MutableDoubleTuple actual = 
            DoubleTuples.standardDeviationFromVariance(t0, null);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testStandardize()
    {
        MutableDoubleTuple t0 = create();
        MutableDoubleTuple standardized = 
            DoubleTuples.standardize(t0, null);
        double mean = DoubleTuples.arithmeticMean(standardized);
        double variance = DoubleTuples.variance(standardized);
        assertEquals(0.0, mean, DOUBLE_EPSILON);
        assertEquals(1.0, variance, DOUBLE_EPSILON);
    }
    
    @Test
    public void testArithmeticMean()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1.0, 2.0, 3.0);
        double expected = 2.0;
        double actual = DoubleTuples.arithmeticMean(t0);
        assertEquals(expected, actual, DOUBLE_EPSILON);
    }
    
    @Test
    public void testVariance()
    {
        MutableDoubleTuple t0 = create();
        double variance = DoubleTuples.variance(t0);
        assertEquals(6.0, variance, DOUBLE_EPSILON);
    }
    
    @Test
    public void testStandardDeviation()
    {
        MutableDoubleTuple t0 = create();
        double standardDeviation = DoubleTuples.standardDeviation(t0);
        assertEquals(2.449489742783178, standardDeviation, DOUBLE_EPSILON);
    }
    
    @Test
    public void testStandardDeviationFromMean()
    {
        MutableDoubleTuple t0 = create();
        double mean = DoubleTuples.arithmeticMean(t0);
        double standardDeviation = 
            DoubleTuples.standardDeviationFromMean(t0, mean);
        assertEquals(2.449489742783178, standardDeviation, DOUBLE_EPSILON);
    }

    @Test
    public void testContainsNaN()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1.0, Double.NaN, 3.0);
        MutableDoubleTuple t1 = DoubleTuples.of(1.0, 2.0, 3.0);
        assertTrue (DoubleTuples.containsNaN(t0));
        assertFalse(DoubleTuples.containsNaN(t1));
    }
    
    @Test
    public void testReplaceNaN()
    {
        MutableDoubleTuple t0 = DoubleTuples.of(1.0, Double.NaN, 3.0);
        MutableDoubleTuple expected = DoubleTuples.of(1.0, 2.0, 3.0);
        MutableDoubleTuple actual = DoubleTuples.replaceNaN(t0, 2.0, null);
        assertEquals(expected, actual);
    }
    
    
}