
# nd-iteration

Classes for the iteration over multidimensional ranges. 
classes.  

### Iteration Order

The order of the iteration for tupless can be classified based on the 
dimension of the tuple that varies fastest. Also see [Wikipedia: Lexicographical Order](https://en.wikipedia.org/wiki/Lexicographical_order).  

#### Lexicographical order

Given a tuple, the next tuple in **lexicographical** order is 
computed by incrementing the _rightmost_ element. When the
maximum value for a particular element is reached, then
the overflow is handled by resetting the element to is minimal
value, and increasing the next element by 1. Intuitively, this
is the same as *counting* with decimal numbers:

    000
    001
    002
    ...
    009 // The maximum value for the last digit is reached
    010 // The last digit is reset to 0, and the next digit is increased by 1
    011
    012
    ...
     

For example, the iteration over the range (0,0,0) to (2,2,3) in 
**lexicographical** order is

     (0, 0, 0)
     (0, 0, 1)
     (0, 0, 2)
     (0, 1, 0)
     (0, 1, 1)
     (0, 1, 2)
     (1, 0, 0)
     (1, 0, 1)
     (1, 0, 2)
     (1, 1, 0)
     (1, 1, 1)
     (1, 1, 2)

The iteration over the range (-1,2,-1) to (1,4,2) in 
**lexicographical** order is

     (-1, 2, -1)
     (-1, 2,  0)
     (-1, 2,  1)
     (-1, 3, -1)
     (-1, 3,  0)
     (-1, 3,  1)
     ( 0, 2, -1)
     ( 0, 2,  0)
     ( 0, 2,  1)
     ( 0, 3, -1)
     ( 0, 3,  0)
     ( 0, 3,  1)

#### Colexicographical order

Given an `IntTuple`, the next `IntTuple` in **colexicographical**` order 
is computed by incrementing the _leftmost_ element. When the maximum value 
for a particular element is reached, then the overflow is handled by 
resetting the element to is minimal value, and increasing the next element 
by 1. Intuitively, this is the same as *counting* with decimal numbers,
with the order of the digits being reversed:

    000
    100
    200
    ...
    900 // The maximum value for the last digit is reached
    010 // The last digit is reset to 0, and the next digit is increased by 1
    110
    210
    ...  

For example, the iteration over the range (0,0,0) to (2,2,3) in 
**colexicographical** order is

     (0, 0, 0)
     (1, 0, 0)
     (0, 1, 0)
     (1, 1, 0)
     (0, 0, 1)
     (1, 0, 1)
     (0, 1, 1)
     (1, 1, 1)
     (0, 0, 2)
     (1, 0, 2)
     (0, 1, 2)
     (1, 1, 2)

The iteration over the range (-1,2,-1) to (1,4,2) in 
**colexicographical** order is

     (-1, 2, -1)
     ( 0, 2, -1)
     (-1, 3, -1)
     ( 0, 3, -1)
     (-1, 2,  0)
     ( 0, 2,  0)
     (-1, 3,  0)
     ( 0, 3,  0)
     (-1, 2,  1)
     ( 0, 2,  1)
     (-1, 3,  1)
     ( 0, 3,  1)

### Neighborhoods

The `IntTupleNeighborhoodIterables` and
`LongTupleNeighborhoodIterables` class offer iterators over tuples
that describe the neighborhood of a given point. The neighborhoods 
supported here are the **Moore neighborhood** and the 
**Von Neumann neighborhood**.  

#### Moore neighborhood

The **Moore neighborhood** describes all neighbors of a point whose 
Chebyshev distance is not greater than a certain limit:

       2 2 2 2 2 
       2 1 1 1 2 
       2 1 * 1 2
       2 1 1 1 2
       2 2 2 2 2

Also see: [Wikipedia: Moore Neighborhood](https://en.wikipedia.org/wiki/Moore_neighborhood)  
and [Wikipedia: Chebyshev Distance](https://en.wikipedia.org/wiki/Chebyshev_distance).

#### Von Neumann neighborhood

The **Von Neumann neighborhood** describes all neighbors of a point whose 
Manhattan distance is not greater than a certain limit:

           2     
         2 1 2   
       2 1 * 1 2
         2 1 2  
           2    

Also see: [Wikipedia: Von Neumann Neighborhood](https://en.wikipedia.org/wiki/Von_Neumann_neighborhood) 
and [Wikipedia: Manhattan Distance](https://en.wikipedia.org/wiki/Manhattan_distance).

  
