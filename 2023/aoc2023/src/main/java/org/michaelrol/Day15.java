/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day15 implements Day {

  private final List<String> input = new ArrayList<>();

  public Day15(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        input.addAll(Arrays.stream(line.split(",")).collect(toUnmodifiableList()));
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    return input.stream().map(Day15::calculateHash).mapToLong(Integer::intValue).sum();
  }

  @Override
  public long Part2() {
    List<List<Lens>> boxes = new ArrayList<>();
    for (int i = 0; i < 256; i++) {
      boxes.add(new ArrayList<>());
    }
    for (String op : input) {
      if (op.endsWith("-")) {
        String label = op.substring(0, op.length() - 1);
        int boxNum = calculateHash(label);
        boxes.get(boxNum).removeIf(lens -> lens.labelMatch(label));
      } else {
        int equalsIndex = op.indexOf("=");
        String label = op.substring(0, equalsIndex);
        int focalLength = Integer.parseInt(op.substring(equalsIndex + 1));

        int boxNum = calculateHash(label);
        List<Lens> box = boxes.get(boxNum);

        int lensIndex = findLensWithLabel(box, label);
        Lens lens = Lens.of(label, focalLength);
        if (lensIndex != -1) {
          box.set(lensIndex, lens);
        } else {
          box.add(lens);
        }
      }
    }
    return getTotalFocusingPower(boxes);
  }

  private static int findLensWithLabel(List<Lens> box, String label) {
    for (int j = 0; j < box.size(); j++) {
      if (box.get(j).labelMatch(label)) {
        return j;
      }
    }
    return -1;
  }

  private static long getTotalFocusingPower(List<List<Lens>> boxes) {
    long totalFocusingPower = 0;
    for (int i = 0; i < boxes.size(); i++) {
      for (int j = 0; j < boxes.get(i).size(); j++) {
        Lens lens = boxes.get(i).get(j);
        totalFocusingPower += (i + 1L) * (j + 1L) * lens.getFocalLength();
      }
    }
    return totalFocusingPower;
  }

  private static int calculateHash(String s) {
    int curr = 0;
    for (char c : s.toCharArray()) {
      curr += c;
      curr *= 17;
      curr %= 256;
    }
    return curr;
  }

  private static class Lens {

    private final String label;
    private final int focalLength;

    public static Lens of(String label, int focalLength) {
      return new Lens(label, focalLength);
    }

    private Lens(String label, int focalLength) {
      this.label = label;
      this.focalLength = focalLength;
    }

    private boolean labelMatch(String otherLabel) {
      return label.equals(otherLabel);
    }

    private int getFocalLength() {
      return focalLength;
    }
  }
}
