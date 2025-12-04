package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Streams;
import com.google.common.primitives.Chars;

public class Day3 implements Day {

  private final List<List<Integer>> input = new ArrayList<>();

  public Day3(String inputPath) {
    ClassLoader classLoader = Day3.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        input.add(Chars.asList(line.toCharArray()).stream().map(chr -> chr - '0').collect(Collectors.toList()));
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    long count = 0;
    for (List<Integer> bank : input) {
      int highestNumber = bank.subList(0, bank.size() - 1).stream().max(Integer::compareTo).orElseThrow();
      int highestIndex = bank.indexOf(highestNumber);
      bank.remove(highestIndex);
      int highestAfterHighest = bank.subList(highestIndex, bank.size()).stream().max(Integer::compareTo).orElseThrow();
      count += highestNumber * 10L + highestAfterHighest;
    }
    return count;
  }

  @Override
  public long Part2() {
    long count = 0;
    for (List<Integer> bank : input) {
      ArrayList<Integer> batteries = new ArrayList<>();
      for (int i = 11; i >= 0; i--) {
        int highestNumber = bank.subList(0, bank.size() - i).stream().max(Integer::compareTo).orElseThrow();
        int highestIndex = bank.indexOf(highestNumber);
        bank = bank.subList(highestIndex + 1, bank.size());
        batteries.add(highestNumber);
      }
      count += Streams.mapWithIndex(
              batteries.stream(),
              (battery, index) -> (long) (battery * Math.pow(10, 11 - index)))
          .reduce(0L, Long::sum);
    }
    return count;
  }

}
