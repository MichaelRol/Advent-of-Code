package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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

  // Rather than checking each point using ray cast, can we just scan along the edges of the box
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
    for (List<Pair<Integer, Integer>> corners : allCorners) {
      if (isBoxInBorders(findBorders(corners.getFirst(), corners.getLast()), tileBorders)) {
        return calcArea(corners.getFirst(), corners.getLast());
      }
    }

    return 0;
  }

  private boolean isBoxInBorders(Set<Long> boxBorders, Set<Long> tileBorders) {
    int maxX = Integer.MIN_VALUE;
    int minX = Integer.MAX_VALUE;
    int maxY = Integer.MIN_VALUE;
    int minY = Integer.MAX_VALUE;

    for (Long point : boxBorders) {
      int x = Math.toIntExact(point / 100000L);
      int y = Math.toIntExact(point % 100000L);
      maxX = Math.max(maxX, x);
      minX = Math.min(minX, x);
      maxY = Math.max(maxY, y);
      minY = Math.min(minY, y);
    }

    for (int x = minX; x <= maxX; x++) {
      boolean inTiles = false;
      boolean inBox = false;
      boolean wasLastATileBorder = false;
      boolean wasLastABoxBorder = false;
      for (int y = minY; y <= maxY; y++) {
        long key = x * 100000L + y;
        boolean isTileBorder = tileBorders.contains(key);
        boolean isBoxBorder = boxBorders.contains(key);

        if (wasLastATileBorder && !isTileBorder) {
          inTiles = !inTiles;
        }

        if (wasLastABoxBorder && !isBoxBorder) {
          inBox = !inBox;
        }

        wasLastABoxBorder = isBoxBorder;
        wasLastATileBorder = isTileBorder;

        if (inBox && !inTiles) {
          return false;
        }
      }
    }
    return true;
  }

  private Set<Long> findBorders(
      Pair<Integer, Integer> first,
      Pair<Integer, Integer> last) {

    Set<Long> borders = Sets.newHashSet();
    int startX = Math.min(first.getLeft(), last.getLeft());
    int endX = Math.max(first.getLeft(), last.getLeft());
    int startY = Math.min(first.getRight(), last.getRight());
    int endY = Math.max(first.getRight(), last.getRight());
    for (int x = startX; x <= endX; x++) {
      borders.add(x * 100000L + startY);
      borders.add(x * 100000L + endY);
    }
    for (int y = startY; y <= endY; y++) {
      borders.add(startX * 100000L + y);
      borders.add(endX * 100000L + y);
    }
    return borders;
  }

  private static long calcArea(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
    return (long) (Math.abs((a.getLeft() - b.getLeft())) + 1) * (Math.abs((a.getRight() - b.getRight())) + 1);
  }

}
