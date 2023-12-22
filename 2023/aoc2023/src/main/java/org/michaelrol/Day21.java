package org.michaelrol;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

public class Day21 implements Day {

  private final List<List<Character>> input = new ArrayList<>();
  private final List<List<Character>> input2 = new ArrayList<>();

  public Day21(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        List<Character> charLine = line.chars().mapToObj(chr -> (char) chr).collect(toList());
        input.add(charLine);
        List<Character> newLine = new ArrayList<>();
        newLine.addAll(charLine);
        newLine.addAll(charLine);
        newLine.addAll(charLine);
        newLine.addAll(charLine);
        newLine.addAll(charLine);
        input2.add(newLine);
      }
      List<List<Character>> lists = new ArrayList<>(input2);
      input2.addAll(lists);
      input2.addAll(lists);
      input2.addAll(lists);
      input2.addAll(lists);
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    Set<Pair<Integer, Integer>> positions = new HashSet<>();
    Pair<Integer, Integer> start = findStart(input, 0);
    positions.add(start);
    input.get(start.getLeft()).set(start.getRight(), '.');
    for (int i = 0; i < 64; i++) {
      for (Pair<Integer, Integer> position : new HashSet<>(positions)) {
        Set<? extends Pair<Integer, Integer>> neighbors = findNeighbors(position, input);
        positions.remove(position);
        positions.addAll(neighbors);
      }
    }
    return positions.size();
  }

  @Override
  public long Part2() {
    Set<Pair<Integer, Integer>> positions = new HashSet<>();
    Pair<Integer, Integer> start = findStart(input2, 12);
    positions.add(start);
    for (List<Character> characters : input2) {
      for (int y = 0; y < input2.get(0).size(); y++) {
        if (characters.get(y) == 'S') {
          characters.set(y, '.');
        }
      }
    }
    input2.get(start.getLeft()).set(start.getRight(), '.');
    for (int i = 0; i < 327; i++) {
      for (Pair<Integer, Integer> position : new HashSet<>(positions)) {
        Set<? extends Pair<Integer, Integer>> neighbors = findNeighbors(position, input2);
        positions.remove(position);
        positions.addAll(neighbors);
      }
      if (i == 64 || i == 195 || i == 326) {
        System.out.println((i + 1) + ": " + positions.size());
      }
    }
    System.out.println("Plug these numbers into Wolfram Alpha's Quadratic Fit calculator. " +
        "(But with 0, 1, 2 instead of 65, 196 and 327)");
    System.out.println("Then plug x = 202300 (26501365 = 202300 * 131 + 65)");
    return 3744 + 14878 * 202300L + 14795 * (long) Math.pow(202300, 2);
  }

  private Set<Pair<Integer, Integer>> findNeighbors(
      Pair<Integer, Integer> position,
      List<List<Character>> input) {

    Set<Pair<Integer, Integer>> neighbors = new HashSet<>();
    if (position.getLeft() - 1 >= 0 && input.get(position.getLeft() - 1).get(position.getRight()) == '.') {
      neighbors.add(Pair.of(position.getLeft() - 1, position.getRight()));
    }
    if (position.getLeft() + 1 < input.size() && input.get(position.getLeft() + 1).get(position.getRight()) == '.') {
      neighbors.add(Pair.of(position.getLeft() + 1, position.getRight()));
    }
    if (position.getRight() - 1 >= 0 && input.get(position.getLeft()).get(position.getRight() - 1) == '.') {
      neighbors.add(Pair.of(position.getLeft(), position.getRight() - 1));
    }
    if (position.getRight() + 1 < input.get(0).size() && input.get(position.getLeft()).get(position.getRight() + 1) == '.') {
      neighbors.add(Pair.of(position.getLeft(), position.getRight() + 1));
    }
    return neighbors;
  }

  private static Pair<Integer, Integer> findStart(List<List<Character>> map, int select) {
    int count = 0;
    for (int i = 0; i < map.size(); i++) {
      for (int j = 0; j < map.get(0).size(); j++) {
        if (map.get(i).get(j) == 'S') {
          if (count == select) {
            return Pair.of(i, j);
          }
          count++;
        }
      }
    }
    throw new IllegalArgumentException("Could not find start position.");
  }
}
