/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day4 implements Day {

  private final List<List<Integer>> winners = new ArrayList<>();
  private final List<List<Integer>> numbers = new ArrayList<>();

  public Day4(String inputPath) {
    ClassLoader classLoader = Day3.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        String[] winnersAndNumbers = line.split(": ")[1].split(" \\| ");
        winners.add(Arrays.stream(winnersAndNumbers[0].split(" "))
            .filter(not(String::isBlank))
            .map(Integer::parseInt)
            .collect(toUnmodifiableList()));
        numbers.add(Arrays.stream(winnersAndNumbers[1].split(" "))
            .filter(not(String::isBlank))
            .map(Integer::parseInt)
            .collect(toUnmodifiableList()));
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    int sum = 0;
    for (int i = 0; i < numbers.size(); i++) {
      long winningCount = numbers.get(i).stream().filter(winners.get(i)::contains).count();
      if (winningCount != 0) {
        sum += (int) Math.pow(2, winningCount - 1);
      }
    }
    return sum;
  }

  @Override
  public long Part2() {
    List<Integer> cardCount = new ArrayList<>(Collections.nCopies(winners.size(), 1));
    for (int i = 0; i < numbers.size(); i++) {
      long winningCount = numbers.get(i).stream().filter(winners.get(i)::contains).count();
      if (winningCount != 0) {
        for (int j = 1; j <= winningCount; j++) {
          cardCount.set(i + j, cardCount.get(i + j) + cardCount.get(i));
        }
      }
    }
    return cardCount.stream().mapToInt(Integer::intValue).sum();
  }
}
