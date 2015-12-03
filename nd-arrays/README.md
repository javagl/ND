
# nd-arrays

Multidimensional arrays of primitive types

### Overview

This library offers multidimensional arrays of primitive types. The packages 
are named according to the field type descriptors in the Java Class File Format:

* `de.javagl.nd.arrays.d`: Arrays consisting of `double` values
* `de.javagl.nd.arrays.i`: Arrays consisting of `int` values
* `de.javagl.nd.arrays.j`: Arrays consisting of `long` values

For each type, the packages contain the basic interfaces for representing 
an array of this type, and classes containing utility methods that 
operate on single these arrays, and to perform operations on these 
arrays using functional interfaces.  

### Indexing and Iteration Order

The iteration over a multidimensional array may take place in different 
ways. For 2D arrays, these are usually referred to as _row-major_ and 
_column-major_ iteration. As an example, consider an array with a size 
of (4,3). This array has a size of 4 in x-direction, and a size of 3 
in y-direction. So it has 4 columns and 3 rows. The row-major iteration 
order for this array is

      0  1  2  3
      4  5  6  7
      8  9 10 11

The column-major iteration order is

      0  3  6  9
      1  4  7 10
      2  5  8 11

Intuitively, the difference here is whether one starts walking over the 
first row or the first column: In the row-major order, the iteration 
first walks over the row (increasing the column index, or the x-coordinate
in the array). In the column-major order, the iteration first walks over
the column (increasing the row index, or the y-coordinate in the array).  

As a generalization of this concept for multidimensional arrays, one can 
consider the coordinates of the array elements, and look which of the 
coordinate elements varies fastest. For example, the row-major iteration 
order in the above example implies the following iteration order for 
the coordinates:

       0,  0
       0,  1
       0,  2
       0,  3
       1,  0
       1,  1
       1,  2
       1,  3
       2,  0
       2,  1
       2,  2
       2,  3

Here, the rightmost coordinate element varies fastest. The ordering of 
the coordinate tuples is referred to as the **lexicographical** ordering.  

The column-major ordering corresponds to this coordinate order:

       0,  0
       1,  0
       2,  0
       0,  1
       1,  1
       2,  1
       0,  2
       1,  2
       2,  2
       0,  3
       1,  3
       2,  3

Here, the leftmost coordinate element varies fastest. The ordering of the 
coordinate tuples is then referred to as the **colexicographical** ordering.  

Also see [Wikipedia: Lexicographical Order](https://en.wikipedia.org/wiki/Lexicographical_order).  

These different orderings are reflected in the ND arrays by 
different `Coordinates` and `Indexers`.  

In the lexicographical coordinates for an array, the rightmost 
coordinate element varies fastest. For an array with a size of (4,3,2), 
the coordinates will be ordered as follows:

       0,  0,  0
       0,  0,  1
       0,  1,  0
       0,  1,  1
       0,  2,  0
       0,  2,  1
       1,  0,  0
       1,  0,  1
       ...
       3,  2,  1

The lexicographical indexing will assign 1D indices for these 
coordinates accordingly:

       0,  0,  0  : Index 0 
       0,  0,  1  : Index 1
       0,  1,  0  : Index 2
       0,  1,  1  : Index 3
       0,  2,  0  : Index 4
       0,  2,  1  : Index 5
       1,  0,  0  : Index 6
       1,  0,  1  : Index 7
       ...
       3,  2,  1  : Index 23 
       
       