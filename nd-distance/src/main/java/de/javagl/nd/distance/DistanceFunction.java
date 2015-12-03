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
package de.javagl.nd.distance;

import java.util.function.ToDoubleBiFunction;

/**
 * Interface representing a distance function. This interface extends
 * the <code>ToDoubleBiFunction</code> interface. Users of this interface
 * should generally prefer to treat it as a <code>ToDoubleBiFunction</code>,
 * unless specific knowledge about it being a distance function is desired. 
 * 
 * @param <T> The type of the elements for which the distance is computed
 */
public interface DistanceFunction<T> extends ToDoubleBiFunction<T, T>
{
    /**
     * Computes the distance between the given objects
     * 
     * @param t0 The first object
     * @param t1 The second object
     * @return The distance
     */
    double distance(T t0, T t1);
    
    @Override
    default double applyAsDouble(T t0, T t1)
    {
        return distance(t0, t1);
    }
}