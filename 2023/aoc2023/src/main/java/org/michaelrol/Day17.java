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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

public class Day17 implements Day {

  private enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Set<Direction> getNext() {
      if (this.equals(NORTH)) {
        return Set.of(EAST, WEST, NORTH);
      }
      if (this.equals(SOUTH)) {
        return Set.of(EAST, WEST, SOUTH);
      }
      if (this.equals(EAST)) {
        return Set.of(NORTH, EAST, SOUTH);
      }
      if (this.equals(WEST)) {
        return Set.of(WEST, NORTH, SOUTH);
      }
      throw new IllegalStateException("Could not find opposite direction.");
    }
  }

  private final List<List<Integer>> input = new ArrayList<>();
  private final Map<Pair<Integer, Integer>, Pair<Integer, Integer>> parentMap = new HashMap<>();

  public Day17(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        input.add(line.chars().map(chr -> chr - '0').boxed().collect(toList()));
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    return calculateHeatLoss();
  }

  @Override
  public long Part2() {
    return 0;
  }

  private int calculateHeatLoss() {
    Pair<Integer, Integer> goal = Pair.of(input.size() - 1, input.get(0).size() - 1);
    Set<Node> seen = new HashSet<>();
    PriorityQueue<NodeHeat> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.heatloss));

    Node start = new Node(Pair.of(0, 0), Direction.EAST, 0);
    queue.add(new NodeHeat(start, 0));
    seen.add(start);

    while (!queue.isEmpty()) {
      NodeHeat nodeHeat = queue.poll();
      Node current = nodeHeat.node;
      int heatLoss = nodeHeat.heatloss;
      if (current.vertex.getLeft().equals(goal.getLeft()) && current.vertex.getRight().equals(goal.getRight()) && current.steps >= 1) {
        return heatLoss;
      }

      Set<Node> possibleNextNodes = current.direction.getNext().stream()
          .filter(direction -> isValidNextMove(current, direction))
          .map(direction -> getNextMove(current, direction))
          .filter(node -> !seen.contains(node))
          .collect(Collectors.toSet());

      for (Node next : possibleNextNodes) {
        queue.add(new NodeHeat(next, heatLoss + input.get(next.vertex.getLeft()).get(next.vertex.getRight())));
        seen.add(next);
      }
    }
    throw new IllegalStateException("No path to target found.");
  }

  private Node getNextMove(Node current, Direction direction) {
    if (direction == Direction.NORTH) {
      return new Node(
          Pair.of(current.vertex.getLeft() - 1, current.vertex.getRight()),
          direction,
          direction == current.direction ? current.steps + 1 : 1);
    }
    if (direction == Direction.SOUTH) {
      return new Node(
          Pair.of(current.vertex.getLeft() + 1, current.vertex.getRight()),
          direction,
          direction == current.direction ? current.steps + 1 : 1);
    }
    if (direction == Direction.EAST) {
      return new Node(
          Pair.of(current.vertex.getLeft(), current.vertex.getRight() + 1),
          direction,
          direction == current.direction ? current.steps + 1 : 1);
    }
    if (direction == Direction.WEST) {
      return new Node(
          Pair.of(current.vertex.getLeft(), current.vertex.getRight() - 1),
          direction,
          direction == current.direction ? current.steps + 1 : 1);
    }
    throw new IllegalStateException("Could not find next move.");
  }

  private boolean isValidNextMove(Node current, Direction direction) {
    if (current.steps < 3 || current.direction != direction) {
      if (direction == Direction.NORTH) {
        return current.vertex.getLeft() - 1 >= 0;
      }
      if (direction == Direction.SOUTH) {
        return current.vertex.getLeft() + 1 < input.size();
      }
      if (direction == Direction.EAST) {
        return current.vertex.getRight() + 1 < input.get(0).size();
      }
      if (direction == Direction.WEST) {
        return current.vertex.getRight() - 1 >= 0;
      }
    }
    return false;
  }

  private static class NodeHeat {

    Node node;
    int heatloss;

    NodeHeat(Node node, int heatloss) {
      this.node = node;
      this.heatloss = heatloss;
    }
  }

  private static class Node {

    Pair<Integer, Integer> vertex;
    Direction direction;
    int steps;

    Node(Pair<Integer, Integer> vertex, Direction direction, int steps) {
      this.vertex = vertex;
      this.direction = direction;
      this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Node node = (Node) o;
      return steps == node.steps &&
          Objects.equals(vertex, node.vertex) &&
          direction == node.direction;
    }

    @Override
    public int hashCode() {
      return Objects.hash(vertex, direction, steps);
    }
  }
}

