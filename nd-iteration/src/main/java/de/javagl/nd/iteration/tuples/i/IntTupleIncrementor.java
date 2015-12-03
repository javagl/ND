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
package de.javagl.nd.iteration.tuples.i;

import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.MutableIntTuple;

/**
 * Interface for classes that may increment a {@link MutableIntTuple}
 * (lexicographically or colexicographically), within a certain range.
 */
interface IntTupleIncrementor
{
    /**
     * Increment the given {@link MutableIntTuple}, in the given range,
     * and return whether the tuple could be incremented without causing
     * an overflow. If <code>false</code> is returned, then the given
     * tuple wrapped around and is then equal to the minimum tuple.
     * 
     * @param t The tuple to increment
     * @param min The minimum tuple, inclusive
     * @param max The maximum tuple, exclusive
     * @return Whether the tuple could be incremented without causing
     * an overflow
     */
    boolean increment(MutableIntTuple t, IntTuple min, IntTuple max);
}