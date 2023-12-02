package org.michaelrol;

import java.util.List;

public class Aoc2023 {

  private static final List<Integer> VALID_DAYS = List.of(1, 2);

  public static void main(String[] args) {
    if (args.length > 2) {
      throw new IllegalArgumentException("Too many runtime arguments provided.");
    }
    List<Integer> days = getDays(args);
    List<Integer> parts = getParts(args);
    for (int dayNumber : days) {
      for (int part : parts) {
        try {
          Class<?> clazz = Class.forName("org.michaelrol.Day" + dayNumber);
          Day day = (Day) clazz.getDeclaredConstructor(String.class).newInstance("inputs/input" + dayNumber + ".txt");
          runDay(day, part);
        } catch (Exception ex) {
          System.out.println("Error occurred whilst running challenge: " + ex);
        }
      }
    }
  }

  private static void runDay(Day day, int part) {
    final long startTime = System.currentTimeMillis();
    int answer = part == 1 ? day.Part1() : day.Part2();
    final long endTime = System.currentTimeMillis();
    System.out.println(day.getClass().getSimpleName() +
        " - Part " + part + ": " + answer + ", ran in " + (endTime - startTime) + "ms.");
  }

  private static List<Integer> getDays(String[] args) {
    if (args.length >= 1) {
      String dayString = args[0];
      try {
        int dayNumber = Integer.parseInt(dayString);
        if (VALID_DAYS.contains(dayNumber)) {
          return List.of(dayNumber);
        }
        throw new IllegalArgumentException("Invalid day number: " + dayNumber);
      } catch (NumberFormatException ex) {
        throw new IllegalArgumentException("Could not parse number from: " + dayString);
      }
    }
    return VALID_DAYS;
  }

  private static List<Integer> getParts(String[] args) {
    if (args.length == 2) {
      String partString = args[1];
      try {
        int partNumber = Integer.parseInt(partString);
        if (partNumber == 1 || partNumber == 2) {
          return List.of(partNumber);
        }
        throw new IllegalArgumentException("Invalid part number: " + partNumber);
      } catch (NumberFormatException ex) {
        throw new IllegalArgumentException("Could not parse number from: " + partString);
      }
    }
    return List.of(1, 2);
  }
}
