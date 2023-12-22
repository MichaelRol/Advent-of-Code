package org.michaelrol;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day14 implements Day {

  private final List<List<Character>> input = new ArrayList<>();
  private final List<List<List<Character>>> history = new ArrayList<>();

  public Day14(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        input.add(line.chars().mapToObj(chr -> (char) chr).collect(toList()));
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    List<List<Character>> platform = new ArrayList<>(input);
    rollNorth(platform);
    return calculateLoad(platform);
  }

  @Override
  public long Part2() {
    List<List<Character>> platform = new ArrayList<>(input);
    for (int ign = 0; ign < 1000000000; ign++) {
      List<List<Character>> after = new ArrayList<>();
      for (List<Character> line : platform) {
        after.add(new ArrayList<>(line));
      }
      history.add(after);
      rollNorth(platform);
      rollWest(platform);
      rollSouth(platform);
      rollEast(platform);
      if (history.contains(platform)) {
        int offset = history.indexOf(platform);
        int loop = history.size() - history.indexOf(platform);
        int finish = ((1000000000 - offset) % loop) + offset;
        platform = history.get(finish);
        break;
      }
    }
    return calculateLoad(platform);
  }

  private static void rollNorth(List<List<Character>> platform) {
    int colSize = platform.get(0).size();
    int rowSize = platform.size();
    for (int i = 1; i < rowSize; i++) {
      List<Character> row = platform.get(i);
      for (int j = 0; j < colSize; j++) {
        if (row.get(j) == 'O') {
          int inFront = i - 1;
          while (inFront >= -1) {
            if (inFront == -1 || platform.get(inFront).get(j) != '.') {
              row.set(j, '.');
              platform.get(inFront + 1).set(j, 'O');
              break;
            }
            inFront--;
          }
        }
      }
    }
  }

  private static void rollWest(List<List<Character>> platform) {
    int colSize = platform.get(0).size();
    for (int i = 1; i < colSize; i++) {
      for (List<Character> characters : platform) {
        if (characters.get(i) == 'O') {
          int inFront = i - 1;
          while (inFront >= -1) {
            if (inFront == -1 || characters.get(inFront) != '.') {
              characters.set(i, '.');
              characters.set(inFront + 1, 'O');
              break;
            }
            inFront--;
          }
        }
      }
    }
  }

  private static void rollSouth(List<List<Character>> platform) {
    int colSize = platform.size();
    int rowSize = platform.get(0).size();
    for (int i = colSize - 2; i >= 0; i--) {
      for (int j = 0; j < rowSize; j++) {
        List<Character> row = platform.get(i);
        if (row.get(j) == 'O') {
          int inFront = i + 1;
          while (inFront < colSize) {
            if (platform.get(inFront).get(j) != '.') {
              break;
            }
            inFront++;
          }
          row.set(j, '.');
          platform.get(inFront - 1).set(j, 'O');
        }
      }
    }
  }

  private static void rollEast(List<List<Character>> platform) {
    int rowSize = platform.get(0).size();
    for (int i = rowSize - 2; i >= 0; i--) {
      for (List<Character> characters : platform) {
        if (characters.get(i) == 'O') {
          int inFront = i + 1;
          while (inFront <= rowSize) {
            if (inFront == rowSize || characters.get(inFront) != '.') {
              characters.set(i, '.');
              characters.set(inFront - 1, 'O');
              break;
            }
            inFront++;
          }
        }
      }
    }
  }

  private long calculateLoad(List<List<Character>> platform) {
    long sum = 0;
    for (int i = 0; i < platform.size(); i++) {
      for (Character chr : platform.get(i)) {
        if (chr == 'O') {
          sum += platform.size() - i;
        }
      }
    }
    return sum;
  }
}
