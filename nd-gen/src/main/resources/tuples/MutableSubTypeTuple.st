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

import de.javagl.nd.tuples.Tuple;
import de.javagl.nd.tuples.Utils;

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Implementation of a {@link Mutable$type.uppercaseName$Tuple} that is a view on 
 * a part of another {@link Mutable$type.uppercaseName$Tuple}
 */
final class MutableSub$type.uppercaseName$Tuple extends AbstractMutable$type.uppercaseName$Tuple 
{
    /**
     * The parent of this tuple
     */
    private final Mutable$type.uppercaseName$Tuple parent;

    /**
     * The offset of this tuple in the parent
     */
    private final int offset;

    /**
     * The size of this tuple
     */
    private final int size;

    /**
     * Creates a new tuple with the given data.
     *
     * @param parent The parent of this tuple
     * @param fromIndex The start index in the parent, inclusive
     * @param toIndex The end index in the parent, exclusive
     * @throws NullPointerException If the given parent is <code>null</code>
     * @throws IllegalArgumentException If the given indices are invalid.
     * This is the case when <code>fromIndex &lt; 0</code>, 
     * <code>fromIndex &gt; toIndex</code>, or 
     * <code>toIndex &gt; {@link Tuple#getSize() getSize()}</code>,
     */
    MutableSub$type.uppercaseName$Tuple(Mutable$type.uppercaseName$Tuple parent, 
        int fromIndex, int toIndex)
    {
        Utils.checkForValidSubTupleIndices(
            parent.getSize(), fromIndex, toIndex);
        this.parent = parent;
        this.offset = fromIndex;
        this.size = toIndex - fromIndex;
    }


    @Override
    public int getSize()
    {
        return size;
    }

    @Override
    public $type.name$ get(int index)
    {
        Utils.checkForValidIndex(index, size);
        return parent.get(index+offset);
    }

    @Override
    public void set(int index, $type.name$ value)
    {
        Utils.checkForValidIndex(index, size);
        parent.set(index+offset, value);
    }

    @Override
    public Mutable$type.uppercaseName$Tuple subTuple(int fromIndex, int toIndex)
    {
        return $type.uppercaseName$Tuples.createSubTuple(
            parent, offset+fromIndex, offset+toIndex);
    }

}
