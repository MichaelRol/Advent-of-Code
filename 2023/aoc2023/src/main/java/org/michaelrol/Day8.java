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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public class Day8 implements Day {

  private final Map<String, Pair<String, String>> graph = new HashMap<>();
  private final List<Character> directions = new ArrayList<>();

  public Day8(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line = reader.readLine();
      line.chars().mapToObj(c -> (char) c).forEach(directions::add);
      while ((line = reader.readLine()) != null) {
        if (line.isBlank()) {
          continue;
        }
        String[] splitLine = line.split(" = ");
        String node = splitLine[0];
        String[] branches = splitLine[1].replace("(", "").replace(")", "").split(", ");
        graph.put(node, Pair.of(branches[0], branches[1]));
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    String curr = "AAA";
    int steps = 0;
    while (true) {
      for (Character direction : directions) {
        if (direction == 'L') {
          curr = graph.get(curr).getLeft();
        } else {
          curr = graph.get(curr).getRight();
        }
        steps++;
        if (curr.equals("ZZZ")) {
          return steps;
        }
      }
    }
  }

  @Override
  public long Part2() {
    List<String> nodes = findStartingNodes();
    int steps = 0;
    while (true) {
      for (Character direction : directions) {
        for (int i = 0; i < nodes.size(); i++) {
          if (direction == 'L') {
            nodes.set(i, graph.get(nodes.get(i)).getLeft());
          } else {
            nodes.set(i, graph.get(nodes.get(i)).getRight());
          }
        }
        steps++;
        if (nodes.stream().allMatch(node -> node.endsWith("Z"))) {
          return steps;
        }
      }
    }
  }

  private List<String> findStartingNodes() {
    return graph.keySet().stream().filter(key -> key.endsWith("A")).collect(toList());
  }
}
