package org.michaelrol;

import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class Day9 implements Day {

  private final List<Pair<Integer, Integer>> input = new ArrayList<>();

  public Day9(String inputPath) {
    ClassLoader classLoader = Day9.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(",");
        input.add(Pair.of(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    return Lists.cartesianProduct(ImmutableList.of(input, input)).stream()
        .mapToLong(corners -> calcArea(corners.getFirst(), corners.getLast()))
        .max()
        .orElseThrow();
  }

  @Override
  public long Part2() {
    Set<Long> tileBorders = Sets.newHashSet();
    for (int i = 0; i < input.size(); i++) {
      Pair<Integer, Integer> point = input.get(i);
      Pair<Integer, Integer> next = input.get((i + 1) % input.size());
      if (point.getLeft().equals(next.getLeft())) {
        int startY = Math.min(point.getRight(), next.getRight());
        int endY = Math.max(point.getRight(), next.getRight());
        for (int y = startY; y <= endY; y++) {
          tileBorders.add(point.getLeft() * 100000L + y);
        }
      } else {
        int startX = Math.min(point.getLeft(), next.getLeft());
        int endX = Math.max(point.getLeft(), next.getLeft());
        for (int x = startX; x <= endX; x++) {
          tileBorders.add(x * 100000L + point.getRight());
        }
      }
    }
    List<List<Pair<Integer, Integer>>> allCorners = Lists.cartesianProduct(ImmutableList.of(input, input)).stream()
        .sorted(Comparator.comparingLong(corners -> calcArea(
            ((List<Pair<Integer, Integer>>) corners).getFirst(),
            ((List<Pair<Integer, Integer>>) corners).getLast())).reversed())
        .toList();
    int[] xs = input.stream().mapToInt(Pair::getLeft).toArray();
    int[] ys = input.stream().mapToInt(Pair::getRight).toArray();
    Polygon polygon = new Polygon(xs, ys, xs.length);
    BiFunction<Integer, Integer, Boolean> memoizedTileCheck =
        memoize((x, y) -> polygon.contains(x, y) || isOnPolygonBorder(x * 100000L + y, tileBorders));
    int count = 1;
    for (List<Pair<Integer, Integer>> corners : allCorners) {
      if (count % 10000 == 0) {
        System.out.println(count + "/" + allCorners.size());
      }
      count++;
      if (testBorders(corners.getFirst(), corners.getLast(), memoizedTileCheck)) {
        return calcArea(corners.getFirst(), corners.getLast());
      }
    }

    return 0;
  }

  public static BiFunction<Integer, Integer, Boolean> memoize(BiFunction<Integer, Integer, Boolean> function) {
    Map<Long, Boolean> cache = new HashMap<>();
    return (x, y) -> {
      Boolean oldValue = cache.get(x * 100000L + y);
      if (oldValue != null) {
        return oldValue;
      }
      boolean valid = function.apply(x, y);
      cache.put(x * 100000L + y, valid);
      return valid;
    };
  }

  private boolean testBorders(
      Pair<Integer, Integer> first,
      Pair<Integer, Integer> last,
      BiFunction<Integer, Integer, Boolean> test) {

    int startX = Math.min(first.getLeft(), last.getLeft());
    int endX = Math.max(first.getLeft(), last.getLeft());
    int startY = Math.min(first.getRight(), last.getRight());
    int endY = Math.max(first.getRight(), last.getRight());
    for (int x = startX; x <= endX; x++) {
      if (!test.apply(x, startY) || !test.apply(x, endY)) {
        return false;
      }
    }
    for (int y = startY; y <= endY; y++) {
      if (!test.apply(startX, y) || !test.apply(endX, y)) {
        return false;
      }
    }
    return true;
  }

  private boolean isOnPolygonBorder(Long point, Set<Long> tileBorders) {
    return tileBorders.contains(point);
  }

  private static long calcArea(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
    return (long) (Math.abs((a.getLeft() - b.getLeft())) + 1) * (Math.abs((a.getRight() - b.getRight())) + 1);
  }

}
