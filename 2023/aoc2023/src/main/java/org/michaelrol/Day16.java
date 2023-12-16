/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Day16 implements Day {

  private enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST
  }

  private final List<List<Character>> input = new ArrayList<>();

  public Day16(String inputPath) {
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
    List<List<Character>> newEnergy = getNewBoard();
    List<List<Character>> board = getNewBoard();
    laser(0, 0, Direction.EAST, board, newEnergy, board.size(), board.get(0).size());
    return energizedTiles(newEnergy);
  }

  @Override
  public long Part2() {
    int boardLength = input.size();
    int boardWidth = input.get(0).size();
    List<Long> energies = new ArrayList<>();

    ExecutorService executor = Executors.newFixedThreadPool(2 * boardWidth + 2 * boardLength);
    for (int i = 0; i < input.size(); i++) {
      int finalI = i;
      executor.submit(() -> runSides(finalI, boardLength, boardWidth, energies));
    }
    for (int j = 0; j < input.get(0).size(); j++) {
      int finalJ = j;
      executor.submit(() -> runTopAndBottom(finalJ, boardLength, boardWidth, energies));
    }

    // Shut down the ExecutorService to release resources
    executor.shutdown();

    return energies.stream()
        .mapToLong(l -> l)
        .max()
        .orElseThrow(() -> new IllegalStateException("Could not find max energy."));
  }

  private void runSides(int i, int boardLength, int boardWidth, List<Long> energies) {
    List<List<Character>> newEnergy = getNewBoard();
    List<List<Character>> board = getNewBoard();
    laser(i, 0, Direction.EAST, board, newEnergy, boardLength, boardWidth);
    energies.add(energizedTiles(newEnergy));
    newEnergy = getNewBoard();
    board = getNewBoard();
    laser(i, input.get(0).size() - 1, Direction.WEST, board, newEnergy, boardLength, boardWidth);
    energies.add(energizedTiles(newEnergy));
  }

  private void runTopAndBottom(int j, int boardLength, int boardWidth, List<Long> energies) {
    List<List<Character>> newEnergy = getNewBoard();
    List<List<Character>> board = getNewBoard();
    laser(0, j, Direction.SOUTH, board, newEnergy, boardLength, boardWidth);
    energies.add(energizedTiles(newEnergy));
    newEnergy = getNewBoard();
    board = getNewBoard();
    laser(input.size() - 1, j, Direction.NORTH, board, newEnergy, boardLength, boardWidth);
    energies.add(energizedTiles(newEnergy));
  }

  private void laser(
      int i,
      int j,
      Direction direction,
      List<List<Character>> board,
      List<List<Character>> newEnergy,
      int boardLength,
      int boardWidth) {

    if (i < 0 || i >= boardLength || j < 0 || j >= boardWidth) {
      return;
    }
    newEnergy.get(i).set(j, '#');
    Character beam = board.get(i).get(j);
    if (beam.equals('>') && direction.equals(Direction.EAST)) {
      return;
    }
    if (beam.equals('<') && direction.equals(Direction.WEST)) {
      return;
    }
    if (beam.equals('^') && direction.equals(Direction.NORTH)) {
      return;
    }
    if (beam.equals('v') && direction.equals(Direction.SOUTH)) {
      return;
    }
    if (beam.equals('.') || beam.equals('>') || beam.equals('<') || beam.equals('^') || beam.equals('v')) {
      if (direction.equals(Direction.EAST)) {
        board.get(i).set(j, '>');
        laser(i, j + 1, direction, board, newEnergy, boardLength, boardWidth);
      } else if (direction.equals(Direction.WEST)) {
        board.get(i).set(j, '<');
        laser(i, j - 1, direction, board, newEnergy, boardLength, boardWidth);
      } else if (direction.equals(Direction.NORTH)) {
        board.get(i).set(j, '^');
        laser(i - 1, j, direction, board, newEnergy, boardLength, boardWidth);
      } else {
        board.get(i).set(j, 'v');
        laser(i + 1, j, direction, board, newEnergy, boardLength, boardWidth);
      }
    } else if (beam.equals('/')) {
      if (direction.equals(Direction.NORTH)) {
        laser(i, j + 1, Direction.EAST, board, newEnergy, boardLength, boardWidth);
      } else if (direction.equals(Direction.SOUTH)) {
        laser(i, j - 1, Direction.WEST, board, newEnergy, boardLength, boardWidth);
      } else if (direction.equals(Direction.EAST)) {
        laser(i - 1, j, Direction.NORTH, board, newEnergy, boardLength, boardWidth);
      } else {
        laser(i + 1, j, Direction.SOUTH, board, newEnergy, boardLength, boardWidth);
      }
    } else if (beam.equals('\\')) {
      if (direction.equals(Direction.NORTH)) {
        laser(i, j - 1, Direction.WEST, board, newEnergy, boardLength, boardWidth);
      } else if (direction.equals(Direction.SOUTH)) {
        laser(i, j + 1, Direction.EAST, board, newEnergy, boardLength, boardWidth);
      } else if (direction.equals(Direction.EAST)) {
        laser(i + 1, j, Direction.SOUTH, board, newEnergy, boardLength, boardWidth);
      } else {
        laser(i - 1, j, Direction.NORTH, board, newEnergy, boardLength, boardWidth);
      }
    } else if (beam.equals('-')) {
      if (direction.equals(Direction.EAST)) {
        laser(i, j + 1, direction, board, newEnergy, boardLength, boardWidth);
      } else if (direction.equals(Direction.WEST)) {
        laser(i, j - 1, direction, board, newEnergy, boardLength, boardWidth);
      } else if (direction.equals(Direction.NORTH)) {
        laser(i, j - 1, Direction.WEST, board, newEnergy, boardLength, boardWidth);
        laser(i, j + 1, Direction.EAST, board, newEnergy, boardLength, boardWidth);
      } else if (direction.equals(Direction.SOUTH)) {
        laser(i, j - 1, Direction.WEST, board, newEnergy, boardLength, boardWidth);
        laser(i, j + 1, Direction.EAST, board, newEnergy, boardLength, boardWidth);
      }
    } else if (beam.equals('|')) {
      if (direction.equals(Direction.NORTH)) {
        laser(i - 1, j, direction, board, newEnergy, boardLength, boardWidth);
      } else if (direction.equals(Direction.SOUTH)) {
        laser(i + 1, j, direction, board, newEnergy, boardLength, boardWidth);
      } else if (direction.equals(Direction.EAST)) {
        laser(i + 1, j, Direction.SOUTH, board, newEnergy, boardLength, boardWidth);
        laser(i - 1, j, Direction.NORTH, board, newEnergy, boardLength, boardWidth);
      } else if (direction.equals(Direction.WEST)) {
        laser(i - 1, j, Direction.NORTH, board, newEnergy, boardLength, boardWidth);
        laser(i + 1, j, Direction.SOUTH, board, newEnergy, boardLength, boardWidth);
      }
    }
  }

  private List<List<Character>> getNewBoard() {
    List<List<Character>> board = new ArrayList<>();
    for (List<Character> characters : input) {
      board.add(new ArrayList<>(characters));
    }
    return board;
  }

  private long energizedTiles(List<List<Character>> newEnergy) {
    int count = 0;
    for (List<Character> characters : newEnergy) {
      for (int j = 0; j < newEnergy.get(0).size(); j++) {
        Character tile = characters.get(j);
        if (tile == '#') {
          count++;
        }
      }
    }
    return count;
  }
}
