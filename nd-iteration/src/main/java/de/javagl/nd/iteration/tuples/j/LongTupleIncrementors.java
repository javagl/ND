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
package de.javagl.nd.iteration.tuples.j;

import de.javagl.nd.tuples.Order;
import de.javagl.nd.tuples.j.LongTuples;

/**
 * Methods for obtaining {@link LongTupleIncrementor}s
 */
class LongTupleIncrementors
{
    /**
     * Returns an {@link LongTupleIncrementor} that increments tuples in the
     * given {@link Order}. If the given {@link Order} is <code>null</code>, 
     * then <code>null</code> will be returned.
     * 
     * @param order The {@link Order}
     * @return The {@link LongTupleIncrementor}
     * 
     * @see #lexicographicalIncrementor()
     * @see #colexicographicalIncrementor()
     */
    static LongTupleIncrementor incrementor(Order order)
    {
        if (order == null)
        {
            return null;
        }
        switch (order)
        {
            case LEXICOGRAPHICAL:
                return lexicographicalIncrementor();
            case COLEXICOGRAPHICAL:
                return colexicographicalIncrementor();
            default:
                break;
        }
        return null;
    }
    
    /**
     * Returns an {@link LongTupleIncrementor} that increments the tuples
     * in lexicographical order
     * 
     * @return The {@link LongTupleIncrementor}.
     */
    static LongTupleIncrementor lexicographicalIncrementor()
    {
        return (t, min, max) ->
            LongTuples.incrementLexicographically(t, min, max, t);
    }
    /**
     * Returns an {@link LongTupleIncrementor} that increments the tuples
     * in colexicographical order
     * 
     * @return The {@link LongTupleIncrementor}.
     */
    static LongTupleIncrementor colexicographicalIncrementor()
    {
        return (t, min, max) -> 
            LongTuples.incrementColexicographically(t, min, max, t);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private LongTupleIncrementors()
    {
        // Private constructor to prevent instantiation
    }
}
