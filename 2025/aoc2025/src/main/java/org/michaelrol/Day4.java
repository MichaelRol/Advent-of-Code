package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.primitives.Chars;

public class Day4 implements Day {

  private final List<List<Character>> input = new ArrayList<>();

  public Day4(String inputPath) {
    ClassLoader classLoader = Day4.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        input.add(Chars.asList(line.toCharArray()));
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    int count = 0;
    for (int y = 0; y < input.size(); y++) {
      for (int x = 0; x < input.get(y).size(); x++) {
        if (findValue(x, y, input) == '@') {
          long adjacentRolls = getAdjacentCoords(Pair.of(x, y), input.size()).stream()
              .filter(coords -> findValue(coords.getLeft(), coords.getRight(), input) == '@')
              .count();
          if (adjacentRolls < 4) {
            count++;
          }
        }
      }
    }
    return count;
  }

  @Override
  public long Part2() {
    int count = 0;
    while (true) {
      List<Pair<Integer, Integer>> toRemove = new ArrayList<>();
      for (int y = 0; y < input.size(); y++) {
        for (int x = 0; x < input.get(y).size(); x++) {
          if (findValue(x, y, input) == '@') {
            long adjacentRolls = getAdjacentCoords(Pair.of(x, y), input.size()).stream()
                .filter(coords -> findValue(coords.getLeft(), coords.getRight(), input) == '@')
                .count();
            if (adjacentRolls < 4) {
              count++;
              toRemove.add(Pair.of(x, y));
            }
          }
        }
      }
      if (toRemove.isEmpty()) {
        return count;
      }
      toRemove.forEach(coords -> input.get(coords.getRight()).set(coords.getLeft(), '#'));
    }
  }

  private static List<Pair<Integer, Integer>> getAdjacentCoords(Pair<Integer, Integer> pos, int size) {
    List<Pair<Integer, Integer>> adjacent = new ArrayList<>();
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (i == 0 && j == 0) {
          continue;
        }
        if (pos.getLeft() + i >= 0 && pos.getLeft() + i < size && pos.getRight() + j >= 0 && pos.getRight() + j < size) {
          adjacent.add(Pair.of(pos.getLeft() + i, pos.getRight() + j));
        }
      }
    }
    return adjacent;
  }

  private static int findValue(int x, int y, List<List<Character>> map) {
    return map.get(y).get(x);
  }

}
