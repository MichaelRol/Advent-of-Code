package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day1 implements Day {

  private final List<Integer> input = new ArrayList<>();

  public Day1(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("R")) {
          input.add(Integer.parseInt(line.substring(1)));
        } else {
          input.add(-Integer.parseInt(line.substring(1)));
        }
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    int count = 0;
    int dial = 50;
    for (int i : input) {
      dial = (dial + i) % 100;
      if (dial == 0) {
        count++;
      }
    }
    return count;
  }

  @Override
  public long Part2() {
    int count = 0;
    int dial = 50;
    for (int i : input) {
      int tempDial = dial + i;
      int divisor = Math.abs(tempDial / 100);
      int remainder = Math.floorMod(tempDial, 100);
      if (divisor == 0 && remainder == 0) {
        count++;
      } else if (tempDial < 0 && dial != 0) {
        count += divisor + 1;
      } else {
        count += divisor;
      }
      dial = remainder;
    }
    return count;
  }

}
