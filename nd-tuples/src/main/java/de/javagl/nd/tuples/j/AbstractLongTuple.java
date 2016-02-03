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

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Abstract base implementation of a {@link LongTuple}.<br>
 * <br>
 * This class implements the {@link #toString()}, {@link #equals(Object)},
 * {@link #hashCode()} and {@link #subTuple(int, int)} methods 
 * canonically.<br>
 * <br> 
 * To create an implementation of a <code>LongTuple</code>, only the  
 * {@link LongTuple#getSize()} and {@link LongTuple#get(int)} methods
 * have to be implemented.<br>
 * <br>
 */
public abstract class AbstractLongTuple implements LongTuple
{
    @Override
    public String toString()
    {
        return LongTuples.toString(this);
    }

    @Override
    public int hashCode()
    {
        return LongTuples.hashCode(this);
    }

    @Override
    public boolean equals(Object object)
    {
        return LongTuples.equals(this, object);
    }

}
