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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

public class Day17 implements Day {

  private enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Direction getOpposite() {
      if (this.equals(NORTH)) {
        return SOUTH;
      }
      if (this.equals(SOUTH)) {
        return NORTH;
      }
      ;
      if (this.equals(EAST)) {
        return WEST;
      }
      if (this.equals(WEST)) {
        return EAST;
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
    List<List<Integer>> distances1 = dijkstra(Pair.of(0, 1), 4, Direction.EAST, 3);
    //List<List<Integer>> distances2 = dijkstra(Pair.of(1, 0), 3, Direction.SOUTH, 3);
    Pair<Integer, Integer> child = Pair.of(distances1.size() - 1, distances1.get(0).size() - 1);
    while (parentMap.containsKey(child)) {
      child = parentMap.get(child);
      input.get(child.getLeft()).set(child.getRight(), 0);
    }
    for (List<Integer> row : input) {
      System.out.println(row);
    }
    System.out.println("---------------------------");
    for (List<Integer> row : distances1) {
      System.out.println(row);
    }
    return distances1.get(distances1.size() - 1).get(distances1.get(0).size() - 1);
    //return distances.get(1).get(3);
  }

  @Override
  public long Part2() {
    return 0;
  }

  private List<List<Integer>> dijkstra(
      Pair<Integer, Integer> source,
      int sourceWeight,
      Direction sourceDirection,
      int maxSteps) {

    List<List<Integer>> distance = new ArrayList<>();
    for (List<Integer> line : input) {
      distance.add(new ArrayList<>(Collections.nCopies(line.size(), Integer.MAX_VALUE)));
    }
    distance.get(source.getLeft()).set(source.getRight(), sourceWeight);

    PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
    minHeap.add(new Node(source, sourceWeight, sourceDirection, 1));

    while (!minHeap.isEmpty()) {
      Node currentNode = minHeap.poll();

      Pair<Integer, Integer> currentVertex = currentNode.vertex;
      int currentDistance = currentNode.distance;
      int currentSteps = currentNode.steps;

      if (currentDistance > distance.get(currentVertex.getLeft()).get(currentVertex.getRight())) {
        continue;
      }

      for (Pair<Pair<Integer, Integer>, Direction> neighbor : findNeighbors(currentNode, maxSteps)) {
        Pair<Integer, Integer> neighborVertex = neighbor.getLeft();
        Direction neighborDirection = neighbor.getRight();
        int newDistance = currentDistance + input.get(neighborVertex.getLeft()).get(neighborVertex.getRight());
        int newSteps = 1;
        if (neighborDirection == currentNode.direction) {
          newSteps = currentSteps + 1;
        }

        if (newDistance < distance.get(neighborVertex.getLeft()).get(neighborVertex.getRight())) {
          distance.get(neighborVertex.getLeft()).set(neighborVertex.getRight(), newDistance);
          Node newNode = new Node(neighborVertex, newDistance, neighborDirection, newSteps);
          minHeap.add(newNode);
          parentMap.put(newNode.vertex, currentNode.vertex);
        }
      }
    }

    return distance;
  }

  private List<Pair<Pair<Integer, Integer>, Direction>> findNeighbors(Node currentNode, int maxSteps) {
    Pair<Integer, Integer> currentVertex = currentNode.vertex;
    List<Pair<Pair<Integer, Integer>, Direction>> collect = Stream.of(
            Pair.of(currentVertex.getLeft(), currentVertex.getRight() - 1),
            Pair.of(currentVertex.getLeft(), currentVertex.getRight() + 1),
            Pair.of(currentVertex.getLeft() - 1, currentVertex.getRight()),
            Pair.of(currentVertex.getLeft() + 1, currentVertex.getRight()))
        .filter(vertex -> vertex.getLeft() >= 0)
        .filter(vertex -> vertex.getRight() >= 0)
        .filter(vertex -> vertex.getLeft() < input.size())
        .filter(vertex -> vertex.getRight() < input.get(0).size())
        .map(vertex -> Pair.of(vertex, lessThanMaxSteps(vertex, currentNode)))
        .filter(vertexAndDirection -> currentNode.steps < maxSteps || currentNode.direction != vertexAndDirection.getRight())
        .filter(vertexAndDirection ->
            currentNode.direction == null || !currentNode.direction.getOpposite().equals(vertexAndDirection.getRight()))
        .collect(toList());
    return collect;
  }

  private Direction lessThanMaxSteps(Pair<Integer, Integer> vertex, Node currentNode) {
    if (currentNode.vertex.getLeft() > vertex.getLeft()) {
      return Direction.NORTH;
    }
    if (currentNode.vertex.getLeft() < vertex.getLeft()) {
      return Direction.SOUTH;
    }
    if (currentNode.vertex.getRight() < vertex.getRight()) {
      return Direction.EAST;
    }
    if (currentNode.vertex.getRight() > vertex.getRight()) {
      return Direction.WEST;
    }
    throw new IllegalStateException("Could not find next direction.");
  }

  private static class Node {

    Pair<Integer, Integer> vertex;
    int distance;
    Direction direction;
    int steps;


    Node(Pair<Integer, Integer> vertex, int distance, Direction direction, int steps) {
      this.vertex = vertex;
      this.distance = distance;
      this.direction = direction;
      this.steps = steps;
    }
  }
}

