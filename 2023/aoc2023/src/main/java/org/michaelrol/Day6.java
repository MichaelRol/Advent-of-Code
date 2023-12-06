/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 implements Day {

  private List<Integer> times = new ArrayList<>();
  private List<Integer> distances = new ArrayList<>();

  public Day6(String inputPath) {
    ClassLoader classLoader = Day2.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String timeLine = reader.readLine();
      Arrays.stream(timeLine.split(": ")[1].replace("\\w*", " ").split(" "))
          .filter(not(String::isBlank))
          .map(Integer::parseInt)
          .forEach(times::add);
      String distanceLine = reader.readLine();
      Arrays.stream(distanceLine.split(": ")[1].replace("\\w*", " ").split(" "))
          .filter(not(String::isBlank))
          .map(Integer::parseInt)
          .forEach(distances::add);
      assert times.size() == distances.size();
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    int numberOfWaysToWinProduct = 1;
    for (int i = 0; i < times.size(); i++) {
      int numberOfWaysToWin = 0;
      for (int x = 1; x < times.get(i); x++) {
        int distance = x * (times.get(i) - x);
        if (distance > distances.get(i)) {
          numberOfWaysToWin++;
        }
      }
      numberOfWaysToWinProduct *= numberOfWaysToWin;
    }
    return numberOfWaysToWinProduct;
  }

  @Override
  public long Part2() {
    long time = Long.parseLong(times.stream().map(String::valueOf).collect(joining()));
    long bestDistance = Long.parseLong(distances.stream().map(String::valueOf).collect(joining()));
    long numberOfWaysToLose = 0;
    for (long x = 1; x < time; x++) {
      long distance = x * (time - x);
      if (distance > bestDistance) {
        break;
      }
      numberOfWaysToLose++;
    }
    for (long x = time; x > numberOfWaysToLose; x--) {
      long distance = x * (time - x);
      if (distance > bestDistance) {
        break;
      }
      numberOfWaysToLose++;
    }
    return time - numberOfWaysToLose;
  }
}
