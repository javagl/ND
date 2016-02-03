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
package de.javagl.nd.arrays.i;

import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

import de.javagl.nd.arrays.Utils;

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Methods that operate on {@link IntArrayND} instances and perform
 * bulk operations with functional interfaces
 */
public class IntArrayFunctionsND
{
    /**
     * Assigns to each element of the given array the value that is provided
     * by the given supplier.
     * 
     * @param a0 The array
     * @param s The supplier
     */
    public static void set(
        MutableIntArrayND a0, IntSupplier s)
    {
        a0.coordinates().parallel().forEach(t ->
        {
            a0.set(t, s.getAsInt());
        });
    }

    /**
     * Applies the given binary operator to each pair of elements from the
     * given arrays, and stores the result in the given result array.<br>
     * <br>
     * If the given result array is <code>null</code>, then a new array
     * will be created and returned.<br>
     * <br>
     * The source arrays and the target array may be identical.
     * 
     * @param a0 The first array
     * @param a1 The second array
     * @param op The operator to apply
     * @param result The array that will store the result
     * @return The result
     * @throws IllegalArgumentException If the given arrays do not have
     * equal sizes.
     */
    public static MutableIntArrayND apply(
        IntArrayND a0, IntArrayND a1, 
        IntBinaryOperator op,
        MutableIntArrayND result)
    {
        Utils.checkForEqualSizes(a0, a1);
        MutableIntArrayND finalResult = validate(a0, result);
        finalResult.coordinates().parallel().forEach(t -> 
        {
            int operand0 = a0.get(t);
            int operand1 = a1.get(t);
            int r = op.applyAsInt(operand0, operand1);
            finalResult.set(t, r);
        });
        return finalResult;
    }

    /**
     * Applies the given unary operator to the elements from the given array, 
     * and stores the result in the given result array.<br>
     * <br>
     * If the given result array is <code>null</code>, then a new array
     * will be created and returned.<br>
     * <br>
     * The source array and the target array may be identical.
     * 
     * @param a0 The array
     * @param op The operator to apply
     * @param result The array that will store the result
     * @return The result
     * @throws IllegalArgumentException If the given arrays do not have
     * equal sizes.
     */
    public static MutableIntArrayND apply(
        IntArrayND a0, IntUnaryOperator op,
        MutableIntArrayND result)
    {
        MutableIntArrayND finalResult = validate(a0, result);
        finalResult.coordinates().parallel().forEach(t ->
        {
            int operand0 = a0.get(t);
            int r = op.applyAsInt(operand0);
            finalResult.set(t, r);
        });
        return finalResult;
    }


    /**
     * Validate the given result array against the given input array.
     * If the result array is not <code>null</code>, it must have 
     * the same size as the input array. If it is <code>null</code>,
     * then a new array with the same size as the input array will
     * be created and returned.
     * 
     * @param a The input array
     * @param result The result array
     * @return The result array
     * @throws IllegalArgumentException If the given result array is
     * not <code>null</code> and has a size that is different from
     * that of the input array.
     */
    private static MutableIntArrayND validate(
        IntArrayND a, MutableIntArrayND result)
    {
        if (result == null)
        {
            result = IntArraysND.create(a.getSize());
        }
        else
        {
            Utils.checkForEqualSizes(a, result);
        }
        return result;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private IntArrayFunctionsND()
    {
        // Private constructor to prevent instantiation
    }
}
