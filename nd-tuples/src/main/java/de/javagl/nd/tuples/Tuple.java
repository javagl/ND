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
package de.javagl.nd.tuples;

/**
 * Interface describing an N-dimensional tuple
 */
public interface Tuple
{
	/**
	 * Returns the size (number of dimensions) of this tuple.
	 * 
	 * @return The size of this tuple. 
	 */
    int getSize();
    
    /**
     * Returns a <i>view</i> on a portion of this tuple. Changes in this 
     * tuple will be visible in the returned tuple.
     *
     * @param fromIndex The start index of the sub-tuple (inclusive)
     * @param toIndex The end index of the sub-tuple (exclusive)
     * @return The sub-tuple
     * @throws IllegalArgumentException If the indices are not valid. This
     * is the case when <tt>fromIndex &lt; 0</tt> or 
     * <tt>fromIndex &gt; toIndex</tt> or 
     * <tt>toIndex &gt;= {@link #getSize()}</tt>  
     */
    Tuple subTuple(int fromIndex, int toIndex);
}
