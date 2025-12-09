package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

// TODO: Try with graph
public class Day9 implements Day {

  private final List<Pair<Long, Long>> input = new ArrayList<>();

  public Day9(String inputPath) {
    ClassLoader classLoader = Day9.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(",");
        input.add(Pair.of(Long.parseLong(split[0]), Long.parseLong(split[1])));
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
    return 0;
  }

  private static long calcArea(Pair<Long, Long> a, Pair<Long, Long> b) {
    return (Math.abs((a.getLeft() - b.getLeft())) + 1) * (Math.abs((a.getRight() - b.getRight())) + 1);
  }

}
