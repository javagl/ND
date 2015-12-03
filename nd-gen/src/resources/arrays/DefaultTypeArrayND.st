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

import java.util.function.ToIntFunction;

import de.javagl.nd.arrays.Indexers;
import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.i.IntTuple;

/**
 * Default implementation of a {@link Mutable$type.uppercaseName$ArrayND}
 */
class Default$type.uppercaseName$ArrayND
    extends AbstractMutable$type.uppercaseName$ArrayND
    implements Mutable$type.uppercaseName$ArrayND
{
    /**
     * The underlying data
     */
    private final $type.name$ data[];

    /**
     * A function that converts multidimensional indices to 1D indices
     */
    private final ToIntFunction<IntTuple> indexer;

    /**
     * Create a new array with the given size.
     *
     * @param size The size
     * @throws NullPointerException If the given size is <code>null</code>
     * @throws IllegalArgumentException If the given size is negative
     * along any dimension
     */
    Default$type.uppercaseName$ArrayND(IntTuple size)
    {
        super(size, Order.LEXICOGRAPHICAL);
        this.data = new $type.name$[getTotalSize()];
        this.indexer = Indexers.lexicographicalIndexer(size);
    }

    @Override
    public void set(IntTuple indices, $type.name$ value)
    {
        data[indexer.applyAsInt(indices)] = value;
    }

    @Override
    public $type.name$ get(IntTuple indices)
    {
        return data[indexer.applyAsInt(indices)];
    }

}
