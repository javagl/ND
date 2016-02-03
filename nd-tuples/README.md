
# nd-tuples

Multidimensional tuples of primitive types  

### Overview

This library offers multidimensional tuples of primitive types. The packages 
are named according to the field type descriptors in the Java Class File Format:

*   `de.javagl.nd.tuples.d`: Tuples consisting of `double` values
*   `de.javagl.nd.tuples.i`: Tuples consisting of `int` values
*   `de.javagl.nd.tuples.j`: Tuples consisting of `long` values

For each type, the packages contain the basic interfaces for representing 
a tuple of this type, and classes containing utility methods that operate 
on single tuples or collections of tuples, and to perform operations on 
these tuples using functional interfaces.  

### Tuple interfaces

The interfaces that represent tuples of the respective types are separated 
into _mutable_ and _immutable_ instances. For example, the `DoubleTuple` 
interface represents a read-only tuple of `double` values. The 
`MutableDoubleTuple` represents a tuple of `double` values that may be 
modified. For both interfaces, abstract base implementations exist. 
For example, `AbstractDoubleTuple` and `AbstractMutableDoubleTuple`. 
These classes offer canonical implementations of the `equals`, `hashCode` 
and `toString` methods, and thus allow implementing the corresponding 
interfaces with minimal effort:  

     MutableDoubleTuple t = new AbstractMutableDoubleTuple()
     {
         @Override
         public int getSize()
         {
             return myData.getNumberOfElements();
         }

         @Override
         public double get(int index)
         {
             return myData.getElement(index);
         }

         @Override
         public void set(int index, double value)
         {
             myData.setElement(index, value);
         }
     });


### Tuple methods

Each tuple interface has few methods that refer to the data storage, and 
whose implementation will thus likely depend on the underlying data 
structure. Namely the `stream()` method for stream handling, and the 
`subTuple(int,int)` method that creates a _view_ on parts of the tuple. 
Sensible `default` implementations for these methods exist in the interface, 
but they may be overridden by implementors of the interface. These methods 
allow to flexibly operate on tuples (and sub-tuples) using stream operations. 
Examples of using the `subTuple` or `stream` method of the `DoubleTuple` 
class are shown here:

     DoubleTuple tuple = 
         DoubleTuples.of(5.0, 4.0, 3.0, 2.0, 1.0, 0.0);

     // Create a sub-tuple that is a view on a range of the tuple:
     DoubleTuple subTuple = tuple.subTuple(1, 5);

     System.out.println(subTuple);      // Prints (4.0, 3.0, 2.0, 1.0)

     // Create a stream from the sub-tuple, derive a sorted stream, 
     // and then convert the sorted stream into an array
     double sortedArray[] = subTuple.stream().sorted().toArray(); 
     System.out.println(
         Arrays.toString(sortedArray)); // Prints [1.0, 2.0, 3.0, 4.0]

### Tuple utility methods

Each package contains a class with utility methods for operating on the 
corresponding tuple type. These utility methods include the creation and 
conversion of the respective tuple instances, as well as arithmetic and 
comparison operations. For example, the `DoubleTuples` class contains 
methods that allow the creation and manipulation of `DoubleTuple` instances:

     // Create two tuples
     MutableDoubleTuple t0 = DoubleTuples.of(0.0, 1.0, 2.0, 3.0);
     MutableDoubleTuple t1 = DoubleTuples.of(1.0, 2.0, 3.0, 4.0);

     // Compute the sum of the tuples, creating a new tuple:
     MutableDoubleTuple sum = DoubleTuples.add(t0, t1, null);

     System.out.println(sum); // Prints (1.0, 3.0, 5.0, 7.0)

     // Multiply all elements if the sum with 2.0, storing the 
     // result in a predefined result tuple 
     MutableDoubleTuple result = DoubleTuples.create(sum.getSize());
     DoubleTuples.multiply(sum, 2.0, result);

     System.out.println(result); // Prints (2.0, 6.0, 10.0, 14.0)

     // Multiply the result with 2.0, in-place
     DoubleTuples.multiply(result, 2.0, result);

     System.out.println(result); // Prints (4.0, 12.0, 20.0, 28.0)

     // Do some comparisons
     System.out.println(
         DoubleTuples.areElementsLessThan(t1, 28.0));        // Prints false
     System.out.println(
         DoubleTuples.areElementsLessThanOrEqual(t1, 28.0)); // Prints true

### Tuple collection utility methods

Each package contains a class with utility methods for operating on collections 
of the corresponding tuple type. These utility methods include bulk operations 
of the respective tuple utility methods. For example, the 
`DoubleTupleCollections` class contains methods that allow computing 
the sum of a collection of `DoubleTuple` instances:

     // Create a list of tuples
     List<DoubleTuple> tuples = Arrays.asList (
         DoubleTuples.of(0.0, 1.0, 2.0),
         DoubleTuples.of(0.0, 1.0, 2.0),
         DoubleTuples.of(0.0, 1.0, 2.0)
     );

     // Compute the sum of all tuples
     MutableDoubleTuple sum = 
         DoubleTupleCollections.add(tuples, null);

     System.out.println(sum); // Prints (0.0, 3.0, 6.0)


### Tuple function utility methods

Each package contains a class with utility methods for performing functional 
operations on the corresponding tuple type. These utility methods include 
general bulk operations on the elements of the tuples, as well as 
reductions and inclusive and exclusive scans. For example, the 
`DoubleTupleFunctions` class contains these utility methods for 
operating on `DoubleTuple` instances.

     MutableDoubleTuple tuple = 
         DoubleTuples.of(2.0, 3.0, 1.0, 4.0);

     // Compute the sum of all elements of the tuple,
     // using a (0,+) reduction
     double sum = DoubleTupleFunctions.reduce(
         tuple, 0.0, (a,b) -> a + b);
     System.out.println(sum); // Prints 10.0

     // Compute the product of all elements of the tuple,
     // using a (1,*) reduction
     double product = DoubleTupleFunctions.reduce(
         tuple, 1.0, (a,b) > a * b);
     System.out.println(product); // Prints 24.0

     // Compute the minimum of all elements of the tuple,
     // using a (inf,min) reduction
     double min = DoubleTupleFunctions.reduce(
         tuple, Double.POSITIVE_INFINITY, Math::min);
     System.out.println(min); // Prints 1.0


---
     
# Change log:

Version 0.0.2-SNAPSHOT:

* Changed the `DoubleTuples#variance` methods to return 
the *bias-corrected sample variance*
* Added `DoubleTuples#geometricMean` and `DoubleTuples#harmonicMean`
* Added `insertElementAt` and `removeElementAt` to the tuples classes,
  to create new tuples with additional or removed elements
* Added `replaceNaN` in the `DoubleTuples` class
     

Version 0.0.1, 2015-12-08:

* Initial commit
     