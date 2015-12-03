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

import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

/**
 * Methods to create streams from {@link LongTuple} instances.
 */
class LongTupleStreams
{
    /**
     * Returns a stream of the contents of the given {@link LongTuple}
     * 
     * @param tuple The tuple
     * @return The stream
     */
    static LongStream stream(LongTuple tuple)
    {
        return stream(tuple, 0, tuple.getSize());
    }

    /**
     * Returns a stream of the specified range of the given {@link LongTuple}
     * 
     * @param tuple The tuple
     * @param startInclusive The start index, inclusive
     * @param endExclusive  The end index, exclusive
     * @return The stream
     */
    static LongStream stream(
        LongTuple tuple, int startInclusive, int endExclusive) 
    {
        LongTupleSpliterator longTupleSpliterator = 
            new LongTupleSpliterator(
                tuple, startInclusive, endExclusive);
        return StreamSupport.longStream(longTupleSpliterator, false);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private LongTupleStreams()
    {
        // Private constructor to prevent instantiation
    }

}
