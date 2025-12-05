package org.michaelrol;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

public class Day5 implements Day {

  private final List<Pair<Long, Long>> ranges = new ArrayList<>();
  private final List<Long> ingredients = new ArrayList<>();

  public Day5(String inputPath) {
    ClassLoader classLoader = Day5.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      String fullInput = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
      String[] splitInput = fullInput.split("\n\n");
      ranges.addAll(Arrays.stream(splitInput[0].split("\n"))
          .map(range -> range.split("-"))
          .map(range -> Pair.of(Long.parseLong(range[0]), Long.parseLong(range[1])))
          .toList());
      ingredients.addAll(Arrays.stream(splitInput[1].split("\n")).map(Long::parseLong).toList());
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    List<Pair<Long, Long>> mergedRanges = mergeRanges(this.ranges);
    return ingredients.stream()
        .filter(ingredient -> isIngredientFresh(ingredient, mergedRanges))
        .count();
  }

  @Override
  public long Part2() {
    return mergeRanges(this.ranges).stream()
        .mapToLong(range -> (range.getRight() - range.getLeft()) + 1)
        .sum();
  }

  private static boolean isIngredientFresh(long ingredient, List<Pair<Long, Long>> ranges) {
    for (Pair<Long, Long> range : ranges) {
      if (ingredient >= range.getLeft() && ingredient <= range.getRight()) {
        return true;
      }
      if (ingredient < range.getLeft()) {
        return false;
      }
    }
    return false;
  }

  private static List<Pair<Long, Long>> mergeRanges(List<Pair<Long, Long>> ranges) {
    List<Pair<Long, Long>> sortedRanges = ranges.stream().sorted(Comparator.comparingLong(Pair::getLeft)).toList();
    List<Pair<Long, Long>> mergedRanges = new ArrayList<>();
    Pair<Long, Long> current = sortedRanges.getFirst();
    for (int i = 1; i < sortedRanges.size(); i++) {
      long leftLow = current.getLeft();
      long leftHigh = current.getRight();
      long rightLow = sortedRanges.get(i).getLeft();
      long rightHigh = sortedRanges.get(i).getRight();
      if (leftHigh > rightHigh) {
        current = Pair.of(leftLow, leftHigh);
      } else if (leftHigh < rightLow) {
        mergedRanges.add(current);
        current = sortedRanges.get(i);
      } else {
        current = Pair.of(leftLow, rightHigh);
      }
    }
    mergedRanges.add(current);
    return mergedRanges;
  }

}
