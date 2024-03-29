package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

public class Day18 implements Day {

  private final List<DigPlan> inputPart1 = new ArrayList<>();
  private final List<DigPlan> inputPart2 = new ArrayList<>();

  public Day18(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(" ");
        Direction direction = Direction.of(split[0]);
        int length = Integer.parseInt(split[1]);
        String substring = split[2].substring(2, split[2].length() - 1);
        int hex = Integer.parseInt(substring.substring(0, substring.length() - 1), 16);
        Direction directionPart2 = Direction.of(substring.substring(substring.length() - 1));
        inputPart1.add(new DigPlan(direction, length));
        inputPart2.add(new DigPlan(directionPart2, hex));
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    Pair<Integer, Integer> currentPos = Pair.of(0, 0);
    Set<Pair<Integer, Integer>> borders = new HashSet<>();
    borders.add(currentPos);
    for (DigPlan digPlan : inputPart1) {
      if (digPlan.direction == Direction.UP) {
        for (int i = 0; i < digPlan.length; i++) {
          currentPos = Pair.of(currentPos.getLeft() - 1, currentPos.getRight());
          borders.add(currentPos);
        }
      } else if (digPlan.direction == Direction.DOWN) {
        for (int i = 0; i < digPlan.length; i++) {
          currentPos = Pair.of(currentPos.getLeft() + 1, currentPos.getRight());
          borders.add(currentPos);
        }
      } else if (digPlan.direction == Direction.LEFT) {
        for (int i = 0; i < digPlan.length; i++) {
          currentPos = Pair.of(currentPos.getLeft(), currentPos.getRight() - 1);
          borders.add(currentPos);
        }
      } else if (digPlan.direction == Direction.RIGHT) {
        for (int i = 0; i < digPlan.length; i++) {
          currentPos = Pair.of(currentPos.getLeft(), currentPos.getRight() + 1);
          borders.add(currentPos);
        }
      }
    }
    int startX = borders.stream().mapToInt(Pair::getLeft).min().getAsInt();
    int startY = borders.stream().filter(pair -> pair.getLeft() == startX).mapToInt(Pair::getRight).min().getAsInt();
    floodFill(startX + 1, startY + 1, borders);
    return borders.size();
  }

  @Override
  public long Part2() {
    Pair<Integer, Integer> currentPos = Pair.of(0, 0);
    List<Pair<Integer, Integer>> vertices = new ArrayList<>();
    Collections.reverse(inputPart2);
    int trenchLength = 0;
    for (DigPlan digPlan : inputPart2) {
      trenchLength += digPlan.length;
      if (digPlan.direction == Direction.UP) {
        currentPos = Pair.of(currentPos.getLeft() - digPlan.length, currentPos.getRight());
        vertices.add(currentPos);
      } else if (digPlan.direction == Direction.DOWN) {
        currentPos = Pair.of(currentPos.getLeft() + digPlan.length, currentPos.getRight());
        vertices.add(currentPos);
      } else if (digPlan.direction == Direction.LEFT) {
        currentPos = Pair.of(currentPos.getLeft(), currentPos.getRight() - digPlan.length);
        vertices.add(currentPos);
      } else if (digPlan.direction == Direction.RIGHT) {
        currentPos = Pair.of(currentPos.getLeft(), currentPos.getRight() + digPlan.length);
        vertices.add(currentPos);
      }
    }
    return shoelaceTheorem(vertices, trenchLength);
  }

  private long shoelaceTheorem(List<Pair<Integer, Integer>> vertices, int trenchLength) {
    long sum = 0;
    for (int i = 0; i < vertices.size(); i++) {
      sum += (long) vertices.get(i).getLeft() * vertices.get((i + 1) % vertices.size()).getRight();
      sum -= (long) vertices.get((i + 1) % vertices.size()).getLeft() * vertices.get(i).getRight();
    }
    return (sum / 2) + (trenchLength / 2) + 1;
  }

  private void floodFill(int left, int right, Set<Pair<Integer, Integer>> borders) {
    ArrayDeque<Pair<Integer, Integer>> toCheck = new ArrayDeque<>();
    toCheck.add(Pair.of(left, right));
    while (!toCheck.isEmpty()) {
      Pair<Integer, Integer> point = toCheck.pop();
      if (borders.contains(point)) {
        continue;
      } else {
        borders.add(point);
      }
      toCheck.add(Pair.of(point.getLeft() - 1, point.getRight()));
      toCheck.add(Pair.of(point.getLeft() + 1, point.getRight()));
      toCheck.add(Pair.of(point.getLeft(), point.getRight() - 1));
      toCheck.add(Pair.of(point.getLeft(), point.getRight() + 1));
    }
  }

  private static class DigPlan {

    Direction direction;
    int length;

    DigPlan(Direction direction, int length) {
      this.direction = direction;
      this.length = length;
    }
  }

  private enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    static Direction of(String str) {
      switch (str) {
        case "U":
        case "3":
          return UP;
        case "D":
        case "1":
          return DOWN;
        case "R":
        case "0":
          return RIGHT;
        case "L":
        case "2":
          return LEFT;
        default:
          throw new IllegalArgumentException("Could not parse Direction from " + str);
      }
    }
  }
}
