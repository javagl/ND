/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.javagl.nd.tuples.j;

import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Methods that perform bulk operations on the elements of {@link LongTuple}s
 * using functional interfaces.<br>
 * <br>
 * Unless otherwise noted, none of the arguments passed to these methods
 * may be <code>null</code>.
 */
public class LongTupleFunctions
{

    /**
     * Applies the given binary operator to each pair of elements from the
     * given tuples, and stores the result in the given result tuple.
     * If the given result tuple is <code>null</code>, then a new tuple
     * will be created and returned.
     * 
     * @param t0 The first tuple
     * @param t1 The second tuple
     * @param op The operator to apply
     * @param result The tuple that will store the result
     * @return The result
     * @throws IllegalArgumentException If the given tuples do not have
     * the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple apply(
        LongTuple t0, LongTuple t1, 
        LongBinaryOperator op,
        MutableLongTuple result)
    {
        Utils.checkForEqualSize(t0, t1);
        result = LongTuples.validate(t0, result);
        int n = t0.getSize();
        for (int i=0; i<n; i++)
        {
            long operand0 = t0.get(i);
            long operand1 = t1.get(i);
            long r = op.applyAsLong(operand0, operand1);
            result.set(i, r);
        }
        return result;
    }

    /**
     * Applies the given unary operator to element from the given tuple, 
     * and stores the result in the given result tuple.
     * If the given result tuple is <code>null</code>, then a new tuple
     * will be created and returned.
     * 
     * @param t0 The tuple
     * @param op The operator to apply
     * @param result The tuple that will store the result
     * @return The result
     * @throws IllegalArgumentException If the given tuples do not have
     * the same {@link Tuple#getSize() size}
     */
    public static MutableLongTuple apply(
        LongTuple t0, LongUnaryOperator op,
        MutableLongTuple result)
    {
        result = LongTuples.validate(t0, result);
        int n = t0.getSize();
        for (int i=0; i<n; i++)
        {
            long operand0 = t0.get(i);
            long r = op.applyAsLong(operand0);
            result.set(i, r);
        }
        return result;
    }

    /**
     * Performs a reduction on the elements of the given tuple, using the 
     * provided identity value and an associative accumulation function, 
     * and returns the reduced value.
     * 
     * @param t0 The tuple
     * @param identity The identity value
     * @param op The accumulation function
     * @return The result
     */
    public static long reduce(
        LongTuple t0, long identity, LongBinaryOperator op)
    {
        long result = identity;
        int n = t0.getSize();
        for (int i=0; i<n; i++)
        {
            long operand0 = t0.get(i);
            result = op.applyAsLong(result, operand0);
        }
        return result;
    }


    /**
     * Performs an inclusive scan on the elements of the given tuple, using 
     * the provided associative accumulation function, and returns the 
     * result.<br>
     * <br>
     * If the given <code>result</code> tuple is <code>null</code>, then a 
     * new tuple will be created and returned.<br>
     * <br>
     * The <code>result</code> tuple may be identical to the input tuple.
     * 
     * @param t0 The tuple
     * @param op The accumulation function
     * @param result The result
     * @return The result
     * @throws IllegalArgumentException If the given result tuple is not
     * <code>null</code> and has a different {@link Tuple#getSize() size}
     * than the input tuple
     */
    public static MutableLongTuple inclusiveScan(
        LongTuple t0, LongBinaryOperator op, MutableLongTuple result)
    {
        result = LongTuples.validate(t0, result);
        int n = t0.getSize();
        if (n > 0)
        {
            result.set(0, t0.get(0));
            for (int i=1; i<n; i++)
            {
                long operand0 = result.get(i-1);
                long operand1 = t0.get(i);
                long r = op.applyAsLong(operand0, operand1);
                result.set(i, r);
            }
        }
        return result;
    }

    /**
     * Performs an exclusive scan on the elements of the given tuple, using 
     * the provided identity value and associative accumulation function, 
     * and returns the result.<br>
     * <br>
     * If the given <code>result</code> tuple is <code>null</code>, then a 
     * new tuple will be created and returned.<br>
     * <br>
     * The <code>result</code> tuple may be identical to the input tuple. 
     * 
     * @param t0 The tuple
     * @param identity The identity value
     * @param op The accumulation function
     * @param result The result
     * @return The result
     * @throws IllegalArgumentException If the given result tuple is not
     * <code>null</code> and has a different {@link Tuple#getSize() size}
     * than the input tuple
     */
    public static MutableLongTuple exclusiveScan(
        LongTuple t0, long identity, LongBinaryOperator op, 
        MutableLongTuple result)
    {
        result = LongTuples.validate(t0, result);
        int n = t0.getSize();
        if (n > 0)
        {
            long previousElement = t0.get(0);
            result.set(0, identity);
            for (int i=1; i<n; i++)
            {
                long operand0 = result.get(i-1);
                long operand1 = previousElement;
                long r = op.applyAsLong(operand0, operand1);
                previousElement = t0.get(i);
                result.set(i, r);
            }
        }
        return result;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private LongTupleFunctions()
    {
        // Private constructor to prevent instantiation
    }
}
