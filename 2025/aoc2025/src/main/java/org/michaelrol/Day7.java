package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7 implements Day {

  private final List<String> input = new ArrayList<>();

  public Day7(String inputPath) {
    ClassLoader classLoader = Day7.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        input.add(line);
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    Set<Integer> beams = new HashSet<>();
    beams.add(input.getFirst().indexOf("S"));
    List<String> lines = input.subList(1, input.size());
    int count = 0;
    for (String line : lines) {
      for (Integer beam : Set.copyOf(beams)) {
        if (line.charAt(beam) == '^') {
          count++;
          beams.remove(beam);
          beams.add(beam + 1);
          beams.add(beam - 1);
        }
      }
    }
    return count;
  }

  @Override
  public long Part2() {
    return 0;
  }

}
