package com.adamtobey.partialSort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a class that will sort a partially-sorted array of T's
 */
public class PartialSorter<T extends Comparable<T>> {

  /**
   * Sort an array that was already partially sorted with the property that
   *  each element in the array passed in is within 10 indices of its final
   *  sorted location. Does not mutate input list.
   * @param list the partially sorted input list
   * @return a fully sorted output list
   */
  public List<T> sort(List<T> list) {
    /*
       Check the list for nullity to improve algorithm encapsulation. Triggering a
        NullPointerException in this method may give a confusing stack trace and uninformative
        error message that leaks the abstraction of this algorithm by its function instead of
        implementation, including which methods it calls that may cause a NullPointerException
        with a null list.
     */
    if (list == null) throw new IllegalArgumentException("List must not be null");

    if (list.size() == 0) return list;

    ArrayList<ArrayList<T>> chunks = new ArrayList<>(list.size() / 10 + 10);

    // Partition into chunks of 10
    for (int i = 0; 10 * i < list.size(); i++) {
      chunks.add(i, new ArrayList<>(list.subList(10 * i, Math.min(list.size(), 10 * (i + 1)))));
    }

    // Sort each chunk
    for (ArrayList<T> chunk : chunks) {
      Collections.sort(chunk);
    }

    // PartialSorter wept for he had no more chunks to conquer
    if (chunks.size() == 1) return chunks.get(0);

    // Conquer by swapping at the boundary
    for (int i = 0; i < chunks.size() - 1; i++) {
      ArrayList<T> lesser = chunks.get(i);
      ArrayList<T> greater = chunks.get(i + 1);
      while (lesser.get(lesser.size() - 1).compareTo(greater.get(0)) > 0) {
        T a = lesser.remove(lesser.size() - 1);
        T b = greater.remove(0);
        insertChunk(b, lesser);
        insertChunk(a, greater);
      }
    }

    return chunks.stream().flatMap(Collection::stream).collect(Collectors.toList());
  }

  /**
   * Mutably inserts an element into a chunk in sorted order
   * @param element the element to insert
   * @param chunk the sorted chunk to insert into
   */
  private void insertChunk(T element, List<T> chunk) {
    // don't need anything fancy to achieve constant complexity on constant length chunk
    int insert = 0;
    while (insert < chunk.size() && chunk.get(insert).compareTo(element) < 0) {
      insert++;
    }
    chunk.add(insert, element);
  }

}