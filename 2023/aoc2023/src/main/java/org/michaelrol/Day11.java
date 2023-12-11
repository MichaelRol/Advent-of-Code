/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class Day11 implements Day {

  private final List<List<Character>> input = new ArrayList<>();

  public Day11(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        input.add(line.chars().mapToObj(chr -> (char) chr).collect(toList()));
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    return findManhattanDistanceSums(2);
  }

  @Override
  public long Part2() {
    return findManhattanDistanceSums(1000000);
  }

  private long findManhattanDistanceSums(int gravityLevel) {
    List<Integer> doubleRows = new ArrayList<>();
    List<Integer> doubleCols = new ArrayList<>();

    for (int i = 0; i < input.size(); i++) {
      List<Character> row = input.get(i);
      if (row.stream().allMatch(chr -> chr.equals(row.get(0)))) {
        doubleRows.add(i);
      }
    }

    for (int j = 0; j < input.get(0).size(); j++) {
      boolean allDot = true;
      for (List<Character> characters : input) {
        if (characters.get(j) != '.') {
          allDot = false;
          break;
        }
      }
      if (allDot) {
        doubleCols.add(j);
      }
    }

    List<Pair<Integer, Integer>> hashes = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) {
      for (int j = 0; j < input.get(0).size(); j++) {
        if (input.get(i).get(j) == '#') {
          hashes.add(Pair.of(i, j));
        }
      }
    }

    long sum = 0;
    for (int i = 0; i < hashes.size() - 1; i++) {
      for (int j = i + 1; j < hashes.size(); j++) {
        long iLeft = hashes.get(i).getLeft();
        long iRight = hashes.get(i).getRight();
        long jLeft = hashes.get(j).getLeft();
        long jRight = hashes.get(j).getRight();
        long highRow = Math.max(iLeft, jLeft);
        long highCol = Math.max(iRight, jRight);
        long lowRow = Math.min(iLeft, jLeft);
        long lowCol = Math.min(iRight, jRight);
        long doubleRowsBetween = 0;
        long doubleColsBetween = 0;
        for (int row : doubleRows) {
          if (row < highRow && row > lowRow) {
            doubleRowsBetween++;
          }
        }
        for (int col : doubleCols) {
          if (col < highCol && col > lowCol) {
            doubleColsBetween++;
          }
        }
        sum += ((highRow - lowRow) + (doubleRowsBetween * gravityLevel - doubleRowsBetween)) +
            ((highCol - lowCol) + (doubleColsBetween * gravityLevel - doubleColsBetween));
      }
    }
    return sum;
  }
}
