/**
 * Base classes for multidimensional arrays.
 * <br>
 * <h3>Overview</h3>
 * <br>
 * The sub-packages are of this package contain classes that represent
 * multidimensional arrays of primitive types. These packages are named 
 * according to the field type descriptors in the Java Class File Format:
 * <ul>
 *   <li>
 *     <a href="de/javagl/nd/arrays/d/package-summary.html">
 *         <code>de.javagl.nd.arrays.d</code></a>: Arrays consisting
 *         of <code>double</code> values
 *   </li>
 *   <li>
 *     <a href="de/javagl/nd/tuples/i/package-summary.html">
 *         <code>de.javagl.nd.arrays.i</code></a>: Arrays consisting
 *         of <code>int</code> values
 *   </li>
 *   <li>
 *     <a href="de/javagl/nd/tuples/j/package-summary.html">
 *         <code>de.javagl.nd.arrays.j</code></a>: Arrays consisting
 *         of <code>long</code> values
 *   </li>
 * </ul>
 * For each type, the packages contain the basic interfaces for representing
 * an array of this type, and classes containing utility methods that operate
 * on single these arrays, and to perform operations on these arrays using 
 * functional interfaces.<br>
 * <br>
 * <br>
 * <h3><a name="IndexingAndIterationOrder">Indexing and Iteration Order</a></h3>
 * <br>
 * The iteration over a multidimensional array may take place in different
 * ways. For 2D arrays, these are usually referred to as <i>row-major</i>
 * and <i>column-major</i> iteration. As an example, consider an array 
 * with a size of (4,3). This array has a size of 4 in x-direction, and
 * a size of 3 in y-direction. So it has 4 columns and 3 rows. The row-major 
 * iteration order for this array is
 * <pre><code>
 *  0  1  2  3
 *  4  5  6  7
 *  8  9 10 11
 * </code></pre>
 * The column-major iteration order is
 * <pre><code>
 *  0  3  6  9
 *  1  4  7 10
 *  2  5  8 11
 * </code></pre>
 * Intuitively, the difference here is whether one starts walking over
 * the first row or the first column: In the row-major order, the 
 * iteration first walks over the row (increasing the column index, or the
 * x-coordinate in the array). In the column-major order, the iteration first 
 * walks over the column (increasing the row index, or the y-coordinate
 * in the array).<br>   
 * <br>
 * As a generalization of this concept for multidimensional arrays, one
 * can consider the coordinates of the array elements, and look which
 * of the coordinate elements varies fastest. For example, the row-major 
 * iteration order in the above example implies the following iteration 
 * order for the coordinates:
 * <pre><code>
 *   0,  0
 *   0,  1
 *   0,  2
 *   0,  3
 *   1,  0
 *   1,  1
 *   1,  2
 *   1,  3
 *   2,  0
 *   2,  1
 *   2,  2
 *   2,  3
 * </code></pre>
 * Here, the rightmost coordinate element varies fastest. The ordering of
 * the coordinate tuples is referred to as the <b>lexicographical</b> 
 * ordering.<br>
 * <br> 
 * The column-major ordering corresponds to this coordinate order:
 * <pre><code>
 *   0,  0
 *   1,  0
 *   2,  0
 *   0,  1
 *   1,  1
 *   2,  1
 *   0,  2
 *   1,  2
 *   2,  2
 *   0,  3
 *   1,  3
 *   2,  3
 * </code></pre>
 * Here, the leftmost coordinate element varies fastest. The ordering of
 * the coordinate tuples is then referred to as the <b>colexicographical</b> 
 * ordering.<br>
 * <br> 
 * Also see 
 * <a href="https://en.wikipedia.org/wiki/Lexicographical_order" 
 * target="_blank">Wikipedia: Lexicographical Order</a>.<br>
 * <br> 
 * These different orderings are reflected in the ND arrays by different
 * {@link de.javagl.nd.arrays.Coordinates Coordinates} and
 * {@link de.javagl.nd.arrays.Indexers Indexers}.<br>
 * <br>
 * In the 
 * {@link de.javagl.nd.arrays.Coordinates#lexicographicalCoordinates(
 * de.javagl.nd.tuples.i.IntTuple) lexicographical coordinates} for an array, 
 * the rightmost coordinate element varies fastest. For an array with a 
 * size of (4,3,2), the coordinates will be ordered as follows:
 * <pre><code>
 *   0,  0,  0
 *   0,  0,  1
 *   0,  1,  0
 *   0,  1,  1
 *   0,  2,  0
 *   0,  2,  1
 *   1,  0,  0
 *   1,  0,  1
 *   ...
 *   3,  2,  1
 * </code></pre>
 * The {@link de.javagl.nd.arrays.Indexers#lexicographicalIndexer(
 * de.javagl.nd.tuples.i.IntTuple) lexicographical indexer} will compute 
 * 1D indices for these coordinates accordingly:  
 * <pre><code>
 *   0,  0,  0  : Index 0 
 *   0,  0,  1  : Index 1
 *   0,  1,  0  : Index 2
 *   0,  1,  1  : Index 3
 *   0,  2,  0  : Index 4
 *   0,  2,  1  : Index 5
 *   1,  0,  0  : Index 6
 *   1,  0,  1  : Index 7
 *   ...
 *   3,  2,  1  : Index 23
 * </code></pre>
 */
package de.javagl.nd.arrays;

