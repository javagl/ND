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
package de.javagl.nd.distance.tuples.d;

import de.javagl.nd.tuples.d.DoubleTuple;


/**
 * Simple implementation of a dynamic time warping distance computation
 */
class DynamicTimeWarpingDistance 
{
	/**
	 * A thread local holder for the matrix that will be used for
	 * the computation in {@link #computeDynamicTimeWarpingDistance} 
	 */
	private static final ThreadLocal<double[][]> threadLocalMatrix = 
		new ThreadLocal<double[][]>() 
	{
		@Override
		protected double[][] initialValue() 
		{
			return new double[1][1];
		}
	};

	/**
	 * Performs the computation of the dynamic time warping distance
	 * between the given {@link DoubleTuple}s
	 * 
	 * @param u The first {@link DoubleTuple}
	 * @param v The second {@link DoubleTuple}
	 * @return The dynamic time warping distance
	 */
    static double computeDynamicTimeWarpingDistance(
    	DoubleTuple u, DoubleTuple v)
    {
        int m = u.getSize();
        int n = v.getSize();
        double matrix[][] = threadLocalMatrix.get();
        if (matrix.length < m || matrix[0].length < n)
        {
        	matrix = new double[m][n];
        	threadLocalMatrix.set(matrix);
        }
        double u0 = u.get(0);
        double v0 = v.get(0);
        matrix[0][0] = Math.abs(u0-v0);
        for (int j=1; j<n; j++)
        {
            matrix[0][j] = Math.abs(u0-v.get(j)) + matrix[0][j-1];
        }
        for (int i=1; i<m; i++)
        {
           matrix[i][0] = Math.abs(u.get(i)-v0) + matrix[i-1][0];
        }
        for (int i = 1; i < m; i++)
        {
            double ui = u.get(i);
            for (int j = 1; j < n; j++)
            {
                double vj = v.get(j);
                double min = matrix[i - 1][j - 1];
                min = min(min, matrix[i][j - 1]);
                min = min(min, matrix[i - 1][j]);
                double d = ui - vj;
                matrix[i][j] = min + (d < 0 ? -d : d);
            }
        }
        return matrix[m-1][n-1];
    }
    
    /**
     * Minimum function ignoring NaN
     * 
     * @param d0 The first value
     * @param d1 The second value
     * @return The minimum
     */
    private static double min(double d0, double d1)
    {
    	return d0 < d1 ? d0 : d1;
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private DynamicTimeWarpingDistance()
    {
        // Private constructor to prevent instantiation
    }
}
 

