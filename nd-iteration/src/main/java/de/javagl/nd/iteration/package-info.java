/**
 * Classes related to the iteration over multidimensional ranges.<br>
 * <br>
 * <h3>Overview</h3>
 * <br>
 * The sub-packages are of this package contain classes for the iteration
 * over multidimensional ranges. These packages are named according to
 * the field type descriptors in the Java Class File Format:
 * <ul>
 *   <li>
 *     <a href="tuples/i/package-summary.html">
 *         <code>de.javagl.nd.tuples.i</code></a>: Iteration over 
 *         tuples of <code>int</code> values
 *   </li>
 *   <li>
 *     <a href="tuples/j/package-summary.html">
 *         <code>de.javagl.nd.tuples.j</code></a>: Iteration over
 *         tuples of <code>long</code> values
 *   </li>
 * </ul>
 * 
 * Different iterables may be created with the 
 * {@link de.javagl.nd.iteration.tuples.i.IntTupleIterables 
 * IntTupleIterables} and  
 * {@link de.javagl.nd.iteration.tuples.j.LongTupleIterables
 * LongTupleIterables}, and the  
 * and {@link de.javagl.nd.iteration.tuples.i.IntTupleNeighborhoodIterables
 * IntTupleNeighborhoodIterables} and
 * and {@link de.javagl.nd.iteration.tuples.j.LongTupleNeighborhoodIterables
 * LongTupleNeighborhoodIterables} 
 * classes, respectively.<br>
 * <br>
 * <h3><a name="IterationOrder">Iteration Order</a></h3>
 * <br>
 * The order of the iteration for tupless can be classified
 * based on the dimension of the tuple that varies fastest. Also see 
 * <a href="https://en.wikipedia.org/wiki/Lexicographical_order" 
 * target="_blank">Wikipedia: Lexicographical Order</a>.<br>
 * <br>
 * <h4>Lexicographical order</h4>
 * <br>
 * Given a tuple, the next tuple in 
 * {@link de.javagl.nd.tuples.Order#LEXICOGRAPHICAL lexicographical} order
 * is computed by incrementing the <i>rightmost</i> element. <br>
 * <br>
 * For example, the iteration over the range (0,0,0) to (2,2,3) in 
 * <b>lexicographical</b> order is
 * <pre><code>
 * (0, 0, 0)
 * (0, 0, 1)
 * (0, 0, 2)
 * (0, 1, 0)
 * (0, 1, 1)
 * (0, 1, 2)
 * (1, 0, 0)
 * (1, 0, 1)
 * (1, 0, 2)
 * (1, 1, 0)
 * (1, 1, 1)
 * (1, 1, 2)
 * </code></pre>
 * The iteration over the range (-1,2,-1) to (1,4,2) in 
 * <b>lexicographical</b> order is
 * <pre><code>
 * (-1, 2, -1)
 * (-1, 2,  0)
 * (-1, 2,  1)
 * (-1, 3, -1)
 * (-1, 3,  0)
 * (-1, 3,  1)
 * ( 0, 2, -1)
 * ( 0, 2,  0)
 * ( 0, 2,  1)
 * ( 0, 3, -1)
 * ( 0, 3,  0)
 * ( 0, 3,  1)
 * </code></pre>
 * <br>
 * <br> 
 * <h4>Colexicographical order</h4>
 * <br>
 * Given a tuple, the next tuple in 
 * {@link de.javagl.nd.tuples.Order#COLEXICOGRAPHICAL colexicographical} order
 * is computed by incrementing the <i>leftmost</i> element. <br>
 * <br>
 * For example, the iteration over the range (0,0,0) to (2,2,3) in 
 * <b>colexicographical</b> order is
 * <pre><code>
 * (0, 0, 0)
 * (1, 0, 0)
 * (0, 1, 0)
 * (1, 1, 0)
 * (0, 0, 1)
 * (1, 0, 1)
 * (0, 1, 1)
 * (1, 1, 1)
 * (0, 0, 2)
 * (1, 0, 2)
 * (0, 1, 2)
 * (1, 1, 2)
 * </code></pre>
 * The iteration over the range (-1,2,-1) to (1,4,2) in 
 * <b>colexicographical</b> order is
 * <pre><code>
 * (-1, 2, -1)
 * ( 0, 2, -1)
 * (-1, 3, -1)
 * ( 0, 3, -1)
 * (-1, 2,  0)
 * ( 0, 2,  0)
 * (-1, 3,  0)
 * ( 0, 3,  0)
 * (-1, 2,  1)
 * ( 0, 2,  1)
 * (-1, 3,  1)
 * ( 0, 3,  1)
 * </code></pre>
 * <br>
 * <br>
 * <br>
 * <h3><a name="Neighborhoods">Neighborhoods</a></h3>
 * <br>
 * The {@link de.javagl.nd.iteration.tuples.i.IntTupleNeighborhoodIterables
 * IntTupleNeighborhoodIterables} 
 * and {@link de.javagl.nd.iteration.tuples.j.LongTupleNeighborhoodIterables
 * LongTupleNeighborhoodIterables} 
 * classes offer iterators over tuples that describe the neighborhood of 
 * a given point. The neighborhoods supported here are the 
 * <b>Moore neighborhood</b> and the <b>Von Neumann neighborhood</b>.<br>
 * <br>
 * <h4>Moore neighborhood</h4>
 * <br>
 * The <b>Moore neighborhood</b> describes all neighbors of a point 
 * whose Chebyshev distance is not greater than a certain limit:
 * <pre><code>
 *   2 2 2 2 2 
 *   2 1 1 1 2 
 *   2 1 * 1 2
 *   2 1 1 1 2
 *   2 2 2 2 2
 * </code></pre>
 * Also see: <a href="https://en.wikipedia.org/wiki/Moore_neighborhood" 
 * target="_blank">Wikipedia: Moore Neighborhood</a>.<br>
 * <br>
 * <br>
 * <h4>Von Neumann neighborhood</h4>
 * <br>
 * The <b>Von Neumann neighborhood</b> describes all neighbors of a point 
 * whose Manhattan distance is not greater than a certain limit:
 * <pre><code>
 *       2     
 *     2 1 2   
 *   2 1 * 1 2
 *     2 1 2  
 *       2    
 * </code></pre>
 * Also see: <a href="https://en.wikipedia.org/wiki/Von_Neumann_neighborhood" 
 * target="_blank">Wikipedia: Von Neumann Neighborhood</a>.<br>
 *  
 * 
 */
package de.javagl.nd.iteration;

