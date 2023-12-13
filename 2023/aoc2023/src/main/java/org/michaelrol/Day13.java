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
import java.util.Collections;
import java.util.List;

public class Day13 implements Day {

  private final List<List<List<Character>>> input = new ArrayList<>();

  public Day13(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      List<List<Character>> group = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        if (line.isBlank()) {
          input.add(new ArrayList<>(group));
          group.clear();
        } else {
          group.add(line.chars().mapToObj(chr -> (char) chr).collect(toList()));
        }
      }
      input.add(group);
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }


  @Override
  public long Part1() {
    int sum = 0;
    for (List<List<Character>> block : input) {
      int mirror = findMirror(block);
      sum += mirror;
    }
    return sum;
  }

  @Override
  public long Part2() {
    int sum = 0;
    for (List<List<Character>> block : input) {
      int mirror = findSmudge(block);
      sum += mirror;
    }
    return sum;
  }

  private static int findMirror(List<List<Character>> block) {
    for (int i = 1; i < block.size() / 2; i++) {
      List<List<Character>> topHalf = block.subList(0, i);
      List<List<Character>> bottomHalf = new ArrayList<>(block.subList(i, i + i));
      Collections.reverse(bottomHalf);
      if (topHalf.equals(bottomHalf)) {
        return topHalf.size() * 100;
      }
    }

    for (int i = 1; i < Math.ceil(block.size() / 2d); i++) {
      List<List<Character>> topHalf = block.subList(block.size() - 2 * i, block.size() - i);
      List<List<Character>> bottomHalf = new ArrayList<>(block.subList(block.size() - i, block.size()));
      Collections.reverse(bottomHalf);
      if (topHalf.equals(bottomHalf)) {
        return (block.size() - i) * 100;
      }
    }

    for (int i = 1; i < block.get(0).size() / 2; i++) {
      List<List<Character>> leftHalf = getLeftHalfFromLeft(block, i);
      List<List<Character>> rightHalf = getRightHalfFromLeft(block, i);
      if (leftHalf.equals(rightHalf)) {
        return i;
      }
    }

    for (int i = 1; i < Math.ceil(block.get(0).size()) / 2; i++) {
      List<List<Character>> rightHalf = getRightHalfFromRight(block, i);
      List<List<Character>> leftHalf = getLeftHalfFromRight(block, i);
      if (leftHalf.equals(rightHalf)) {
        return block.get(0).size() - rightHalf.size();
      }
    }
    return 0;
  }

  private static int findSmudge(List<List<Character>> block) {
    for (int i = 1; i < Math.ceil(block.size() / 2d); i++) {
      List<List<Character>> topHalf = block.subList(0, i);
      List<List<Character>> bottomHalf = new ArrayList<>(block.subList(i, i + i));
      Collections.reverse(bottomHalf);
      if (findDifferences(topHalf, bottomHalf) == 1) {
        return topHalf.size() * 100;
      }
    }

    for (int i = 1; i < Math.ceil(block.size() / 2d); i++) {
      List<List<Character>> topHalf = block.subList(block.size() - 2 * i, block.size() - i);
      List<List<Character>> bottomHalf = new ArrayList<>(block.subList(block.size() - i, block.size()));
      Collections.reverse(bottomHalf);
      if (findDifferences(topHalf, bottomHalf) == 1) {
        return (block.size() - i) * 100;
      }
    }

    for (int i = 1; i < Math.ceil(block.get(0).size() / 2d); i++) {
      List<List<Character>> leftHalf = getLeftHalfFromLeft(block, i);
      List<List<Character>> rightHalf = getRightHalfFromLeft(block, i);
      if (findDifferences(leftHalf, rightHalf) == 1) {
        return i;
      }
    }

    for (int i = 1; i < Math.ceil(block.get(0).size()) / 2; i++) {
      List<List<Character>> rightHalf = getRightHalfFromRight(block, i);
      List<List<Character>> leftHalf = getLeftHalfFromRight(block, i);
      if (findDifferences(leftHalf, rightHalf) == 1) {
        return block.get(0).size() - rightHalf.size();
      }
    }
    return 0;
  }

  private static List<List<Character>> getLeftHalfFromLeft(List<List<Character>> block, int i) {
    List<List<Character>> leftHalf = new ArrayList<>();
    for (int j = 0; j < i; j++) {
      List<Character> col = new ArrayList<>();
      for (List<Character> characters : block) {
        col.add(characters.get(j));
      }
      leftHalf.add(col);
    }
    return leftHalf;
  }

  private static List<List<Character>> getRightHalfFromLeft(List<List<Character>> block, int i) {
    List<List<Character>> rightHalf = new ArrayList<>();
    for (int j = 0; j < i; j++) {
      List<Character> col = new ArrayList<>();
      for (List<Character> characters : block) {
        col.add(characters.get(i * 2 - 1 - j));
      }
      rightHalf.add(col);
    }
    return rightHalf;
  }

  private static List<List<Character>> getLeftHalfFromRight(List<List<Character>> block, int i) {
    List<List<Character>> leftHalf = new ArrayList<>();
    for (int j = block.get(0).size() - 2 * i; j < block.get(0).size() - i; j++) {
      List<Character> col = new ArrayList<>();
      for (List<Character> characters : block) {
        col.add(characters.get(j));
      }
      leftHalf.add(col);
    }
    return leftHalf;
  }

  private static List<List<Character>> getRightHalfFromRight(List<List<Character>> block, int i) {
    List<List<Character>> rightHalf = new ArrayList<>();
    for (int j = 0; j < i; j++) {
      List<Character> col = new ArrayList<>();
      for (List<Character> characters : block) {
        col.add(characters.get(block.get(0).size() - 1 - j));
      }
      rightHalf.add(col);
    }
    return rightHalf;
  }

  private static int findDifferences(List<List<Character>> half1, List<List<Character>> half2) {
    int diff = 0;
    for (int i = 0; i < half1.size(); i++) {
      for (int j = 0; j < half1.get(0).size(); j++) {
        if (half1.get(i).get(j) != half2.get(i).get(j)) {
          diff++;
        }
        if (diff > 1) {
          return 2;
        }
      }
    }
    return diff;
  }
}
