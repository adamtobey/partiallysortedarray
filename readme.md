# Partially Sorted Array

## Problem

Given an array that is partially sorted with the property that every item in the given array is within 10 indices of its location in the final sorted array, compute the final sorted order. 

Design an O(n) algorithm to solve this problem.

## Solution

Break the array into chunks of length 10. Observe that the element at index *k* in the input list in chunk *A, A = floor(k/10)* must finally be sorted into either chunk *A - 1* or *A + 1* because *floor((k +- 10)/10) = floor(k/10) +- 1*. By this locality property, we can treat the chunks of length 10 independently, sort each one, and then make the final sort by insertion sort at chunk boundaries. At each boundary, until the last element of the lesser chunk is less than the first element of the greater chunk, exchange the two values and insert-sort them into their respective chunks. This must work because, as shown above, elements need cross only one boundary to be properly sorted, and by definition of the partial sorting, the chunks are already in order, so when each boundary shows an increase, then the whole list is sorted. Finally, exchanging elements across a boundary must maintain the invariant that the chunks are sorted, and insertion sort does just that.

This solution is O(n). The list will be broken into *ceil(n/10)* chunks, and constant work is applied to each chunk to sort a list of length 10, so the complexity of the divide step is linear. The chunks are then combined in the fashion described above. In the worst case, this is a *2n^2* operation, but here *n* is fixed as 10, so the operation is constant time on each chunk, meaning the overall complexity of the conquer step is also linear. Two linear steps together make an overall linear algorithm.