/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

public class Day23 implements Day {

  private final List<List<Character>> input = new ArrayList<>();

  public Day23(String inputPath) {
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
    Pair<Integer, Integer> start = Pair.of(0, 1);
    List<List<Pair<Integer, Integer>>> paths = findPaths(start, new ArrayList<>());
    paths.sort(Comparator.comparingInt(List::size));
    return paths.get(paths.size() - 1).size();
  }

  @Override
  public long Part2() {
    Pair<Integer, Integer> start = Pair.of(0, 1);
    Set<Pair<Integer, Integer>> nodes = new HashSet<>();
    Map<Pair<Integer, Integer>, Map<Pair<Integer, Integer>, Integer>> edges = new HashMap<>();
    nodes.add(start);
    edges.put(start, new HashMap<>());
    findNodesAndEdges(input, start, nodes, edges, new ArrayList<>());
    List<List<Pair<Integer, Integer>>> paths = findPathsInGraph(start, edges, new ArrayList<>(List.of(start)));
    int max = 0;
    for (List<Pair<Integer, Integer>> path : paths) {
      int dist = findDistanceOfPath(path, edges);
      if (dist > max) {
        max = dist;
      }
    }
    return max;
  }

  private int findDistanceOfPath(
      List<Pair<Integer, Integer>> path,
      Map<Pair<Integer, Integer>, Map<Pair<Integer, Integer>, Integer>> edges) {

    int dist = 0;
    for (int i = 0; i < path.size() - 1; i++) {
      dist += edges.get(path.get(i)).get(path.get(i + 1));
    }
    return dist;
  }

  private List<List<Pair<Integer, Integer>>> findPaths(
      Pair<Integer, Integer> pos,
      List<Pair<Integer, Integer>> path) {

    if (pos.getLeft().equals(input.size() - 1)) {
      return List.of(path);
    }

    if (input.get(pos.getLeft()).get(pos.getRight()) == '>') {
      Pair<Integer, Integer> next = Pair.of(pos.getLeft(), pos.getRight() + 1);
      path.add(next);
      return findPaths(next, path);
    } else if (input.get(pos.getLeft()).get(pos.getRight()) == '<') {
      Pair<Integer, Integer> next = Pair.of(pos.getLeft(), pos.getRight() - 1);
      path.add(next);
      return findPaths(next, path);
    } else if (input.get(pos.getLeft()).get(pos.getRight()) == '^') {
      Pair<Integer, Integer> next = Pair.of(pos.getLeft() - 1, pos.getRight());
      path.add(next);
      return findPaths(next, path);
    } else if (input.get(pos.getLeft()).get(pos.getRight()) == 'v') {
      Pair<Integer, Integer> next = Pair.of(pos.getLeft() + 1, pos.getRight());
      path.add(next);
      return findPaths(next, path);
    } else {
      List<List<Pair<Integer, Integer>>> paths = new ArrayList<>();
      List<Pair<Integer, Integer>> neighbors = findNeighbors(pos, path);
      for (Pair<Integer, Integer> neighbor : neighbors) {
        List<Pair<Integer, Integer>> newPath = new ArrayList<>(path);
        newPath.add(neighbor);
        paths.addAll(findPaths(neighbor, newPath));
      }
      return paths;
    }
  }

  private List<Pair<Integer, Integer>> findNeighbors(Pair<Integer, Integer> pos, List<Pair<Integer, Integer>> path) {
    List<Pair<Integer, Integer>> neighbors = new ArrayList<>();
    if (pos.getLeft() > 1 &&
        (input.get(pos.getLeft() - 1).get(pos.getRight()) == '.' ||
            input.get(pos.getLeft() - 1).get(pos.getRight()) == '^') &&
        !path.contains(Pair.of(pos.getLeft() - 1, pos.getRight()))) {

      neighbors.add(Pair.of(pos.getLeft() - 1, pos.getRight()));
    }
    if ((input.get(pos.getLeft() + 1).get(pos.getRight()) == '.' ||
        input.get(pos.getLeft() + 1).get(pos.getRight()) == 'v') &&
        !path.contains(Pair.of(pos.getLeft() + 1, pos.getRight()))) {

      neighbors.add(Pair.of(pos.getLeft() + 1, pos.getRight()));
    }
    if ((input.get(pos.getLeft()).get(pos.getRight() - 1) == '.' ||
        input.get(pos.getLeft()).get(pos.getRight() - 1) == '<') &&
        !path.contains(Pair.of(pos.getLeft(), pos.getRight() - 1))) {

      neighbors.add(Pair.of(pos.getLeft(), pos.getRight() - 1));
    }
    if ((input.get(pos.getLeft()).get(pos.getRight() + 1) == '.' ||
        input.get(pos.getLeft()).get(pos.getRight() + 1) == '>') &&
        !path.contains(Pair.of(pos.getLeft(), pos.getRight() + 1))) {

      neighbors.add(Pair.of(pos.getLeft(), pos.getRight() + 1));
    }
    return neighbors;
  }

  private void findNodesAndEdges(
      List<List<Character>> input,
      Pair<Integer, Integer> startNode,
      Set<Pair<Integer, Integer>> nodes,
      Map<Pair<Integer, Integer>, Map<Pair<Integer, Integer>, Integer>> edges,
      List<Pair<Integer, Integer>> history) {

    Pair<Integer, Integer> curr = Pair.of(startNode.getLeft(), startNode.getRight());
    history.add(curr);
    List<Pair<Integer, Integer>> neighbors = findNeighbors2(curr, history);
    while (neighbors.size() == 1) {
      curr = neighbors.get(0);
      if (curr.getLeft() == input.size() - 1) {
        nodes.add(curr);
        edges.putIfAbsent(curr, new HashMap<>());
        edges.get(history.get(0)).put(curr, history.size());
        edges.get(curr).put(history.get(0), history.size());
        return;
      }
      history.add(curr);
      neighbors = findNeighbors2(curr, history);
    }
    if (nodes.contains(curr)) {
      edges.get(history.get(0)).put(curr, history.size() - 1);
      edges.get(curr).put(history.get(0), history.size() - 1);
      return;
    }
    nodes.add(curr);
    edges.putIfAbsent(curr, new HashMap<>());
    edges.get(history.get(0)).put(curr, history.size() - 1);
    edges.get(curr).put(history.get(0), history.size() - 1);
    for (Pair<Integer, Integer> neighbor : neighbors) {
      findNodesAndEdges(input, neighbor, nodes, edges, new ArrayList<>(List.of(curr)));
    }
  }

  private List<Pair<Integer, Integer>> findNeighbors2(
      Pair<Integer, Integer> pos,
      List<Pair<Integer, Integer>> history) {

    List<Pair<Integer, Integer>> neighbors = new ArrayList<>();
    if (pos.getLeft() > 1 && input.get(pos.getLeft() - 1).get(pos.getRight()) != '#') {
      neighbors.add(Pair.of(pos.getLeft() - 1, pos.getRight()));
    }
    if (input.get(pos.getLeft() + 1).get(pos.getRight()) != '#') {
      neighbors.add(Pair.of(pos.getLeft() + 1, pos.getRight()));
    }
    if (input.get(pos.getLeft()).get(pos.getRight() - 1) != '#') {
      neighbors.add(Pair.of(pos.getLeft(), pos.getRight() - 1));
    }
    if (input.get(pos.getLeft()).get(pos.getRight() + 1) != '#') {
      neighbors.add(Pair.of(pos.getLeft(), pos.getRight() + 1));
    }
    return neighbors.stream().filter(not(history::contains)).collect(toList());
  }

  private List<List<Pair<Integer, Integer>>> findPathsInGraph(
      Pair<Integer, Integer> pos,
      Map<Pair<Integer, Integer>, Map<Pair<Integer, Integer>, Integer>> edges,
      List<Pair<Integer, Integer>> path) {

    if (pos.getLeft().equals(input.size() - 1)) {
      return List.of(path);
    }
    List<List<Pair<Integer, Integer>>> paths = new ArrayList<>();
    Map<Pair<Integer, Integer>, Integer> neighbors = edges.get(pos);
    for (Pair<Integer, Integer> neighbor : neighbors.keySet()) {
      if (path.contains(neighbor)) {
        continue;
      }
      List<Pair<Integer, Integer>> newPath = new ArrayList<>(path);
      newPath.add(neighbor);
      paths.addAll(findPathsInGraph(neighbor, edges, newPath));
    }
    return paths;
  }
}
