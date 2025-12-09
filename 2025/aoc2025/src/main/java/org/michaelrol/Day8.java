package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

public class Day8 implements Day {

  private final List<int[]> input = new ArrayList<>();

  public Day8(String inputPath) {
    ClassLoader classLoader = Day8.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        input.add(Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray());
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    Set<Pair<int[], int[]>> allPairs = findAllPairs();
    List<Pair<int[], int[]>> closestPairs = allPairs.stream()
        .sorted(Comparator.comparingDouble(pair -> findDistance(pair.getLeft(), pair.getRight())))
        .limit(1000)
        .collect(Collectors.toList());

    Set<Set<int[]>> circuits = new HashSet<>();
    while (!closestPairs.isEmpty()) {
      Pair<int[], int[]> search = closestPairs.getFirst();
      closestPairs.remove(search);
      HashSet<int[]> starters = new HashSet<>();
      starters.add(search.getLeft());
      starters.add(search.getRight());
      Set<int[]> circuit = findCircuit(starters, closestPairs);
      circuits.add(circuit);
    }

    return circuits.stream()
        .map(Set::size)
        .sorted(Comparator.reverseOrder())
        .limit(3)
        .reduce(1, (a, b) -> a * b);
  }

  @Override
  public long Part2() {
    Set<Pair<int[], int[]>> allPairs = findAllPairs();
    List<Pair<int[], int[]>> closestPairs = allPairs.stream()
        .sorted(Comparator.comparingDouble(pair -> findDistance(pair.getLeft(), pair.getRight())))
        .collect(Collectors.toList());

    Pair<int[], int[]> search = closestPairs.getFirst();
    Set<int[]> starters = new HashSet<>();
    starters.add(search.getLeft());
    starters.add(search.getRight());
    Pair<int[], int[]> lastToAdd = search;
    int index = 10;
    List<Pair<int[], int[]>> searchSpace = closestPairs.subList(0, index);
    Set<int[]> circuit = findCircuit(starters, searchSpace);
    while (circuit.size() != input.size()) {
      search = closestPairs.getFirst();
      lastToAdd = search;
      starters = new HashSet<>();
      closestPairs.remove(search);
      starters.add(search.getLeft());
      starters.add(search.getRight());
      while (!circuit.contains(search.getLeft()) && !circuit.contains(search.getRight())) {
        search = closestPairs.getFirst();
        lastToAdd = search;
        closestPairs.remove(search);
        starters.add(search.getLeft());
        starters.add(search.getRight());
      }
      index = (input.size() - circuit.size()) / 2;
      searchSpace = closestPairs.subList(0, index);
      Set<int[]> newCircuit = findCircuit(starters, searchSpace);
      circuit.addAll(newCircuit);
    }
    return (long) lastToAdd.getLeft()[0] * lastToAdd.getRight()[0];
  }

  private Set<Pair<int[], int[]>> findAllPairs() {
    Set<Pair<int[], int[]>> allPairs = new HashSet<>();
    for (int[] box : input) {
      for (int[] other : input) {
        if (box != other && !allPairs.contains(Pair.of(other, box))) {
          allPairs.add(Pair.of(box, other));
        }
      }
    }
    return allPairs;
  }

  private Set<int[]> findCircuit(Set<int[]> starters, List<Pair<int[], int[]>> closestPairs) {
    Set<Pair<int[], int[]>> matches = starters.stream()
        .flatMap(box -> closestPairs.stream()
            .filter(pair -> Arrays.equals(pair.getLeft(), box) || Arrays.equals(pair.getRight(), box)))
        .collect(Collectors.toSet());
    if (matches.isEmpty()) {
      return starters;
    }
    closestPairs.removeAll(matches);
    Set<int[]> circuit = matches.stream().flatMap(pair -> Stream.of(
        pair.getLeft(),
        pair.getRight())).collect(Collectors.toSet());
    circuit.addAll(starters);
    return findCircuit(circuit, closestPairs);
  }

  private double findDistance(int[] left, int[] right) {
    return Math.sqrt(
        Math.pow(left[0] - right[0], 2) +
            Math.pow(left[1] - right[1], 2) +
            Math.pow(left[2] - right[2], 2));
  }

}
