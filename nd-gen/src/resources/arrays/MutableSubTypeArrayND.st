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
package de.javagl.nd.arrays.$type.letter$;

import de.javagl.nd.arrays.Utils;
import de.javagl.nd.tuples.i.IntTuple;
import de.javagl.nd.tuples.i.IntTuples;

/**
 * This class represents a mutable view on a sub-array of an array
 * of <code>$type.name$</code> values.
 */
class MutableSub$type.uppercaseName$ArrayND
    extends AbstractMutable$type.uppercaseName$ArrayND
    implements Mutable$type.uppercaseName$ArrayND
{
    /**
     * The parent array
     */
    private final Mutable$type.uppercaseName$ArrayND parent;

    /**
     * The offsets inside the parent array
     */
    private final IntTuple offsets;

    /**
     * Creates a new sub-array
     *
     * @param parent The parent array
     * @param fromIndices The start indices in the parent, inclusive
     * @param toIndices The end indices in the parent, exclusive
     * @throws NullPointerException If the given parent 
     * or any of the given indices is <code>null</code>
     * @throws IllegalArgumentException If the indices are not valid. This
     * is the case when the {@link IntTuple#getSize() size} of the start- or
     * end indices is different than the parent size, or when
     * <code>fromIndex &lt; 0</code> or 
     * <code>toIndex &gt; parentSize(i)</code> 
     * or <code>fromIndex &gt; toIndex</code> for any dimension.
     */
    MutableSub$type.uppercaseName$ArrayND(
        Mutable$type.uppercaseName$ArrayND parent,
        IntTuple fromIndices, IntTuple toIndices)
    {
        super(IntTuples.subtract(toIndices, fromIndices, null), 
            parent.getPreferredIterationOrder());
        Utils.checkForValidSubArrayIndices(
            parent.getSize(), fromIndices, toIndices);
        this.parent = parent;
        this.offsets = IntTuples.copy(fromIndices);
    }

    @Override
    public $type.name$ get(IntTuple indices)
    {
        return parent.get(IntTuples.add(offsets, indices, null));
    }

    @Override
    public void set(IntTuple indices, $type.name$ value)
    {
        parent.set(IntTuples.add(offsets, indices, null), value);
    }

    @Override
    public Mutable$type.uppercaseName$ArrayND subArray(
        IntTuple fromIndices, IntTuple toIndices)
    {
        return $type.uppercaseName$ArraysND.createSubArray(parent, 
            IntTuples.add(offsets, fromIndices, null),
            IntTuples.add(offsets, toIndices, null));
    }
}
