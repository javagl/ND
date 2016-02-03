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

import java.util.Objects;
import java.util.function.ToIntFunction;

import de.javagl.nd.arrays.Indexers;
import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.$type.letter$.$type.uppercaseName$Tuple;
import de.javagl.nd.tuples.i.IntTuple;

/*
 * Note: This class is automatically generated. Do not modify this class
 * directly. See https://github.com/javagl/ND/tree/master/nd-gen/ for
 * further information.
 */

/**
 * Implementation of a {@link $type.uppercaseName$ArrayND} that is backed by a 
 * {@link $type.uppercaseName$Tuple}
 */
class Tuple$type.uppercaseName$ArrayND
    extends Abstract$type.uppercaseName$ArrayND
    implements $type.uppercaseName$ArrayND
{
    /**
     * The underlying data
     */
    private final $type.uppercaseName$Tuple data;

    /**
     * A function that converts multidimensional indices to 1D indices
     */
    private final ToIntFunction<IntTuple> indexer;

    /**
     * Create a new array with the given backing data and size.
     *
     * @param data The backing data 
     * @param size The size
     * @throws NullPointerException If the given data or size is 
     * <code>null</code>
     * @throws IllegalArgumentException If the given size is negative
     * along any dimension
     */
    Tuple$type.uppercaseName$ArrayND($type.uppercaseName$Tuple data, IntTuple size)
    {
        super(size, Order.LEXICOGRAPHICAL);
        Objects.requireNonNull(data, "The data is null");
        this.data = data;
        this.indexer = Indexers.lexicographicalIndexer(size);
    }

    @Override
    public $type.name$ get(IntTuple indices)
    {
        return data.get(indexer.applyAsInt(indices));
    }

}
