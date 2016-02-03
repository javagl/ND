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
package de.javagl.nd.tuples.$type.letter$;

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * A {@link $type.uppercaseName$Tuple} where all elements are the same, constant value 
 */
final class Constant$type.uppercaseName$Tuple extends Abstract$type.uppercaseName$Tuple 
    implements $type.uppercaseName$Tuple
{
    /**
     * The size of this tuple
     */
    private final int size;

    /**
     * The constant value of all elements
     */
    private final $type.name$ value;

    /**
     * Creates a new tuple with the given size and value for all elements
     * 
     * @param size The size
     * @param value The value
     * @throws IllegalArgumentException If the size is negative
     */
    Constant$type.uppercaseName$Tuple(int size, $type.name$ value)
    {
        if (size < 0)
        {
            throw new IllegalArgumentException("Size is negative: "+size);
        }
        this.size = size;
        this.value = value;
    }

    @Override
    public int getSize()
    {
        return size;
    }

    @Override
    public $type.name$ get(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException(
                "Index "+index+", size "+size);
        }
        return value;
    }

    @Override
    public $type.uppercaseName$Tuple subTuple(int fromIndex, int toIndex)
    {
        return $type.uppercaseName$Tuples.constant(toIndex - fromIndex, value);
    }


}
