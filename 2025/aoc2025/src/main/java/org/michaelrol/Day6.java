package org.michaelrol;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

public class Day6 implements Day {

  private final List<String> rows = new ArrayList<>();

  public Day6(String inputPath) {
    ClassLoader classLoader = Day6.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      String fullInput = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
      rows.addAll(Arrays.asList(fullInput.split("\n")));
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    List<List<Long>> numbers = rows.subList(0, rows.size() - 1).stream()
        .map(row -> Arrays.stream(row.trim().split("\s+"))
            .map(Long::parseLong)
            .toList())
        .toList();
    List<String> operations = Arrays.asList(rows.getLast().trim().split("\s+"));
    long sum = 0;
    for (int i = 0; i < operations.size(); i++) {
      String operation = operations.get(i);
      int finalI = i;
      sum += numbers.stream()
          .map(list -> list.get(finalI))
          .reduce((a, b) -> operation.equals("*") ? a * b : a + b)
          .orElseThrow();
    }
    return sum;
  }

  @Override
  public long Part2() {
    List<String> operations = Arrays.asList(rows.getLast().trim().split("\s+"));
    List<List<Long>> numbers = new ArrayList<>();
    List<char[]> individualRows = rows.subList(0, rows.size() - 1).stream().map(String::toCharArray).toList();
    List<Long> tempNums = new ArrayList<>();
    int length = individualRows.stream().map(row -> row.length).max(Integer::compareTo).get();
    for (int i = 0; i < length; i++) {
      StringBuilder stringBuilder = new StringBuilder();
      for (char[] row : individualRows) {
        stringBuilder.append(row[i]);
      }
      String number = stringBuilder.toString().trim();
      if (number.isEmpty()) {
        numbers.add(new ArrayList<>(tempNums));
        tempNums.clear();
      } else {
        tempNums.add(Long.parseLong(number));
        if (i == length - 1) {
          numbers.add(new ArrayList<>(tempNums));
        }
      }
    }
    long sum = 0;
    for (int i = 0; i < operations.size(); i++) {
      String operation = operations.get(i);
      sum += numbers.get(i).stream()
          .reduce((a, b) -> operation.equals("*") ? a * b : a + b)
          .orElseThrow();
    }
    return sum;
  }

}
