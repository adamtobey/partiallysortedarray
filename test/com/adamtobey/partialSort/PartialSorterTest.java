package com.adamtobey.partialSort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Tests for the algorithm implementation
 */
public class PartialSorterTest {

  /**
   * Partially sort an Integer List so that the indices in the resulting list are within 10 indices
   *  of where they would be finally sorted. Mutates the given list.
   * @param list the list to partially sort
   * @return the partially sorted List
   */
  private List<Integer> partialIntSort(List<Integer> list) {
    Collections.sort(list);
    List<Integer> place = new ArrayList<>(list.size());
    Random rand = new Random();
    // not the most efficient algorithm for this...
    for (int i = 0; i < place.size(); i++) {
      int d;
      do {
        d = Math.min(Math.max(rand.nextInt(20) - 10, 0), place.size());
      } while (place.get(i + d) != null);
      place.add(i + d, list.get(i));
    }
    return place;
  }

  /**
   * Test that the algorithm works on random Integer Lists of given length.
   * @param length the length of Lists to test
   * @param iterations the number of random Lists to test
   */
  private void testRandomIntArrays(int length, int iterations) {
    List<Integer> test;
    List<Integer> compare;
    PartialSorter<Integer> sorter = new PartialSorter<>();
    while (iterations-- > 0) {
      test = new Random().ints().distinct().limit(length).boxed().collect(Collectors.toList());
      partialIntSort(test);
      compare = new ArrayList<>(test);
      Collections.sort(compare);
      assertEquals(compare, sorter.sort(test));
    }
  }

  /**
   * Test the presumable best case scenarios for this algorithm's correctness, which would be
   *  products of 10 and powers of 2.
   */
  @Test
  public void testBestCase() {
    testRandomIntArrays(40, 10);
    testRandomIntArrays(10, 10);
    testRandomIntArrays(20, 10);
    testRandomIntArrays(80, 10);
  }

  /**
   * Test cases with multiples of ten but not powers of 2.
   */
  @Test
  public void testMultiplesOfTen() {
    testRandomIntArrays(10, 5);
    testRandomIntArrays(30, 20);
    testRandomIntArrays(50, 20);
    testRandomIntArrays(100, 10);
  }

  /**
   * Test that the algorithm works with less than one chunk of ten.
   */
  @Test
  public void testBelowTen() {
    testRandomIntArrays(1, 10);
    testRandomIntArrays(2, 10);
    testRandomIntArrays(3, 10);
    testRandomIntArrays(4, 10);
    testRandomIntArrays(5, 10);
    testRandomIntArrays(6, 10);
    testRandomIntArrays(7, 10);
    testRandomIntArrays(8, 10);
    testRandomIntArrays(9, 10);
  }

  /**
   * Test other non-multiples of 10 and powers of 2
   */
  @Test
  public void testNonMultiples() {
    testRandomIntArrays(13, 10);
    testRandomIntArrays(27, 10);
    testRandomIntArrays(32, 10);
    testRandomIntArrays(46, 10);
    testRandomIntArrays(59, 10);
  }

  /**
   * Test large lists
   */
  @Test
  public void testLarge() {
    testRandomIntArrays(13234, 3);
    testRandomIntArrays(2712, 3);
    testRandomIntArrays(322, 3);
  }

}