package org.michaelrol;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 implements Day {

  private final List<Long> seeds = new ArrayList<>();
  private final List<List<Long>> sources = new ArrayList<>();
  private final List<List<Long>> dests = new ArrayList<>();
  private final List<List<Long>> ranges = new ArrayList<>();

  public Day5(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      // Extract the seed values
      String seedLine = reader.lines().findFirst().get();
      String justTheSeeds = seedLine.replace("seeds: ", "");
      Arrays.stream(justTheSeeds.split(" ")).map(Long::parseLong).forEach(seeds::add);

      List<String> mapStrings = reader.lines().skip(3).filter(not(String::isBlank)).collect(toUnmodifiableList());
      int mapIndex = 0;
      sources.add(new ArrayList<>());
      dests.add(new ArrayList<>());
      ranges.add(new ArrayList<>());
      for (String line : mapStrings) {
        if (line.contains(":")) {
          mapIndex++;
          sources.add(new ArrayList<>());
          dests.add(new ArrayList<>());
          ranges.add(new ArrayList<>());
          continue;
        }
        String[] numberStrings = line.split(" ");
        long destStart = Long.parseLong(numberStrings[0]);
        long sourceStart = Long.parseLong(numberStrings[1]);
        long range = Long.parseLong(numberStrings[2]);
        sources.get(mapIndex).add(sourceStart);
        dests.get(mapIndex).add(destStart);
        ranges.get(mapIndex).add(range);
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    long min = Long.MAX_VALUE;
    for (long seed : seeds) {
      long curr = seed;
      for (int i = 0; i < ranges.size(); i++) {
        for (int j = 0; j < ranges.get(i).size(); j++) {
          long source = sources.get(i).get(j);
          if (curr >= source && curr < source + ranges.get(i).get(j)) {
            curr = dests.get(i).get(j) + (curr - source);
            break;
          }
        }
      }
      if (curr < min) {
        min = curr;
      }
    }
    return min;
  }

  @Override
  public long Part2() {
    long min = Long.MAX_VALUE;
    for (int seedIndex = 0; seedIndex < seeds.size(); seedIndex += 2) {
      for (long seed = seeds.get(seedIndex); seed < seeds.get(seedIndex) + seeds.get(seedIndex + 1); seed++) {
        long curr = seed;
        long nearestBoundary = Long.MAX_VALUE;
        for (int i = 0; i < ranges.size(); i++) {
          for (int j = 0; j < ranges.get(i).size(); j++) {
            long source = sources.get(i).get(j);
            if (curr >= source && curr < source + ranges.get(i).get(j)) {
              long distanceToBoundary = (source + ranges.get(i).get(j)) - curr;
              if (distanceToBoundary < nearestBoundary) {
                nearestBoundary = distanceToBoundary;
              }
              curr = dests.get(i).get(j) + (curr - source);
              break;
            }
          }
        }
        if (curr < min) {
          min = curr;
        }
        seed += nearestBoundary - 1;
      }
    }
    return min;
  }
}
