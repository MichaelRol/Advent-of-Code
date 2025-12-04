package org.michaelrol;

import static java.lang.Math.floor;
import static java.lang.Math.log10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class Day2 implements Day {

  private final List<Pair<Long, Long>> input = new ArrayList<>();

  public Day2(String inputPath) {
    ClassLoader classLoader = Day2.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      input.addAll(Arrays.stream(reader.readLine().split(","))
          .map(range -> range.split("-"))
          .map(range -> Pair.of(Long.parseLong(range[0]), Long.parseLong(range[1])))
          .toList());
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    long count = 0;
    for (Pair<Long, Long> range : input) {
      for (long i = range.getLeft(); i <= range.getRight(); i++) {
        if (i < 10) {
          continue;
        }
        int digits = (int) floor(log10(i)) + 1;
        if (digits % 2 != 0) {
          continue;
        }
        long firstHalf = (long) (i / Math.pow(10, digits / 2));
        long secondHalf = (long) (i % Math.pow(10, digits / 2));
        if (firstHalf == secondHalf) {
          count += i;
        }
      }
    }
    return count;
  }

  @Override
  public long Part2() {
    long count = 0;
    for (Pair<Long, Long> range : input) {
      for (long i = range.getLeft(); i <= range.getRight(); i++) {
        if (i < 10) {
          continue;
        }
        int digits = (int) floor(log10(i)) + 1;
        for (int j = 1; j <= digits / 2; j++) {
          if (digits % j != 0) {
            continue;
          }
          long pattern = (long) (i % Math.pow(10, j));
          long remainder = (long) (i / Math.pow(10, j));
          boolean match = true;
          while (remainder != 0) {
            long nextPatter = (long) (remainder % Math.pow(10, j));
            if (pattern != nextPatter) {
              match = false;
              break;
            }
            remainder = (long) (remainder / Math.pow(10, j));
          }
          if (match) {
            count += i;
            break;
          }
        }
      }
    }
    return count;
  }

}
