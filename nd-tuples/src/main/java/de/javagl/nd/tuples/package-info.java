/**
 * Base classes for multidimensional tuples.<br>
 * <br>
 * <h3>Overview</h3>
 * <br>
 * The sub-packages are of this package contain classes that represent
 * tuples of primitive types. These packages are named according to
 * the field type descriptors in the Java Class File Format:
 * <ul>
 *   <li>
 *     <a href="d/package-summary.html">
 *         <code>de.javagl.nd.tuples.d</code></a>: Tuples consisting
 *         of <code>double</code> values
 *   </li>
 *   <li>
 *     <a href="i/package-summary.html">
 *         <code>de.javagl.nd.tuples.i</code></a>: Tuples consisting
 *         of <code>int</code> values
 *   </li>
 *   <li>
 *     <a href="j/package-summary.html">
 *         <code>de.javagl.nd.tuples.j</code></a>: Tuples consisting
 *         of <code>long</code> values
 *   </li>
 * </ul>
 * For each type, the packages contain the basic interfaces for representing
 * a tuple of this type, and classes containing utility methods that operate
 * on single tuples or collections of tuples, and to perform operations on
 * these tuples using functional interfaces.<br>
 * <br>
 * <h3>Tuple interfaces</h3>
 * <br>
 * The interfaces that represent tuples of the respective types are
 * separated into <i>mutable</i> and <i>immutable</i> instances. For 
 * example, the {@link de.javagl.nd.tuples.d.DoubleTuple DoubleTuple} 
 * interface represents a read-only tuple of <code>double</code> values.
 * The {@link de.javagl.nd.tuples.d.MutableDoubleTuple MutableDoubleTuple}
 * represents a tuple of <code>double</code> values that may be modified.
 * For both interfaces, abstract base implementations exist. For example,
 * {@link de.javagl.nd.tuples.d.AbstractDoubleTuple AbstractDoubleTuple} and
 * {@link de.javagl.nd.tuples.d.AbstractMutableDoubleTuple 
 * AbstractMutableDoubleTuple}. These classes offer canonical implementations 
 * of the <code>equals</code>, <code>hashCode</code> and <code>toString</code> 
 * methods, and thus allow implementing the corresponding interfaces with 
 * minimal effort:<br>
 * <br>
 * <pre><code>
 * MutableDoubleTuple t = new AbstractMutableDoubleTuple()
 * {
 *     {@literal @}Override
 *     public int getSize()
 *     {
 *         return myData.getNumberOfElements();
 *     }
 *     
 *     {@literal @}Override
 *     public double get(int index)
 *     {
 *         return myData.getElement(index);
 *     }
 *     
 *     {@literal @}Override
 *     public void set(int index, double value)
 *     {
 *         myData.setElement(index, value);
 *     }
 * });
 * </code></pre>
 * <br>
 * <h3>Tuple methods</h3>
 * <br>
 * Each tuple interface has few methods that refer to the data storage,
 * and whose implementation will thus likely depend on the underlying 
 * data structure. Namely the <code>stream()</code> method for stream handling, 
 * and the <code>subTuple(int,int)</code> method that creates a <i>view</i> on
 * parts of the tuple. Sensible <code>default</code> implementations
 * for these methods exist in the interface, but they may be overridden
 * by implementors of the interface. These methods allow to flexibly
 * operate on tuples (and sub-tuples) using stream operations. Examples
 * of using the {@link de.javagl.nd.tuples.d.DoubleTuple#subTuple(int, int) 
 * subTuple} or {@link de.javagl.nd.tuples.d.DoubleTuple#stream() 
 * stream} method of the {@link de.javagl.nd.tuples.d.DoubleTuple
 * DoubleTuple} class are shown here: 
 * <pre><code>
 * DoubleTuple tuple = 
 *     DoubleTuples.of(5.0, 4.0, 3.0, 2.0, 1.0, 0.0);
 * 
 * // Create a sub-tuple that is a view on a range of the tuple:
 * DoubleTuple subTuple = tuple.subTuple(1, 5);
 * 
 * System.out.println(subTuple);      // Prints (4.0, 3.0, 2.0, 1.0)
 * 
 * // Create a stream from the sub-tuple, derive a sorted stream, 
 * // and then convert the sorted stream into an array
 * double sortedArray[] = subTuple.stream().sorted().toArray(); 
 * System.out.println(
 *     Arrays.toString(sortedArray)); // Prints [1.0, 2.0, 3.0, 4.0]
 * </code></pre>
 * 
 * <br>
 * <h3>Tuple utility methods</h3>
 * <br>
 * Each package contains a class with utility methods for operating on 
 * the corresponding tuple type. These utility methods include the
 * creation and conversion of the respective tuple instances, as well
 * as arithmetic and comparison operations. For example, the 
 * {@link de.javagl.nd.tuples.d.DoubleTuples DoubleTuples} class contains
 * methods that allow the creation and manipulation of <code>DoubleTuple</code>
 * instances:
 * <pre><code>
 * // Create two tuples
 * MutableDoubleTuple t0 = DoubleTuples.of(0.0, 1.0, 2.0, 3.0);
 * MutableDoubleTuple t1 = DoubleTuples.of(1.0, 2.0, 3.0, 4.0);
 * 
 * // Compute the sum of the tuples, creating a new tuple:
 * MutableDoubleTuple sum = DoubleTuples.add(t0, t1, null);
 * 
 * System.out.println(sum); // Prints (1.0, 3.0, 5.0, 7.0)
 * 
 * // Multiply all elements if the sum with 2.0, storing the 
 * // result in a predefined result tuple 
 * MutableDoubleTuple result = DoubleTuples.create(sum.getSize());
 * DoubleTuples.multiply(sum, 2.0, result);
 *
 * System.out.println(result); // Prints (2.0, 6.0, 10.0, 14.0)
 * 
 * // Multiply the result with 2.0, in-place
 * DoubleTuples.multiply(result, 2.0, result);
 * 
 * System.out.println(result); // Prints (4.0, 12.0, 20.0, 28.0)
 * 
 * // Do some comparisons
 * System.out.println(
 *     DoubleTuples.areElementsLessThan(t1, 28.0));        // Prints false
 * System.out.println(
 *     DoubleTuples.areElementsLessThanOrEqual(t1, 28.0)); // Prints true
 * </code></pre>
 *  
 * <br>
 * <h3>Tuple collection utility methods</h3>
 * <br>
 * Each package contains a class with utility methods for operating on 
 * collections of the corresponding tuple type. These utility methods 
 * include bulk operations of the respective tuple utility methods.
 * For example, the 
 * {@link de.javagl.nd.tuples.d.DoubleTupleCollections DoubleTupleCollections} 
 * class contains methods that allow computing the sum of a collection of 
 * <code>DoubleTuple</code> instances:
 * <pre><code>
 * // Create a list of tuples
 * List&lt;DoubleTuple&gt; tuples = Arrays.asList (
 *     DoubleTuples.of(0.0, 1.0, 2.0),
 *     DoubleTuples.of(0.0, 1.0, 2.0),
 *     DoubleTuples.of(0.0, 1.0, 2.0)
 * );
 * 
 * // Compute the sum of all tuples
 * MutableDoubleTuple sum = 
 *     DoubleTupleCollections.add(tuples, null);
 * 
 * System.out.println(sum); // Prints (0.0, 3.0, 6.0)
 * </code></pre>
 *  
 * 
 * <br>
 * <h3>Tuple function utility methods</h3>
 * <br>
 * Each package contains a class with utility methods for performing
 * functional operations on the corresponding tuple type. These 
 * utility methods include general bulk operations on the elements
 * of the tuples, as well as reductions and inclusive and exclusive
 * scans. For example, the {@link de.javagl.nd.tuples.d.DoubleTupleFunctions
 * DoubleTupleFunctions} class contains these utility methods for operating
 * on <code>DoubleTuple</code> instances.
 * <pre><code>
 * MutableDoubleTuple tuple = 
 *     DoubleTuples.of(2.0, 3.0, 1.0, 4.0);
 * 
 * // Compute the sum of all elements of the tuple,
 * // using a (0,+) reduction
 * double sum = DoubleTupleFunctions.reduce(
 *     tuple, 0.0, (a,b) -&gt; a + b);
 * System.out.println(sum); // Prints 10.0
 *
 * // Compute the product of all elements of the tuple,
 * // using a (1,*) reduction
 * double product = DoubleTupleFunctions.reduce(
 *     tuple, 1.0, (a,b) &gt; a * b);
 * System.out.println(product); // Prints 24.0
 * 
 * // Compute the minimum of all elements of the tuple,
 * // using a (inf,min) reduction
 * double min = DoubleTupleFunctions.reduce(
 *     tuple, Double.POSITIVE_INFINITY, Math::min);
 * System.out.println(min); // Prints 1.0
 * </code></pre>
 * 
 * 
 */
package de.javagl.nd.tuples;

