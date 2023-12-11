package org.michaelrol;

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Day10 implements Day {

    private enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    private final List<List<Character>> input = new ArrayList<>();

    private Direction direction = Direction.NORTH;

    public Day10(String inputPath) {
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
        Pair<Integer, Integer> startCoords = findStartCoords();
        Pair<Integer, Integer> currPos = findLoopEnd(startCoords);
        int steps = 1;
        while (!currPos.equals(startCoords)) {
            currPos = stepForwardInLoop(currPos);
            steps++;
        }
        return Math.round(steps / 2d);
    }

    @Override
    public long Part2() {
        Pair<Integer, Integer> startCoords = findStartCoords();
        Pair<Integer, Integer> currPos = findLoopEnd(startCoords);
        List<Pair<Integer, Integer>> loop = new ArrayList<>(List.of(startCoords));
        while (!currPos.equals(startCoords)) {
            currPos = stepForwardInLoop(currPos);
            loop.add(currPos);
        }
        int count = 0;
        currPos = findLoopEnd(startCoords);
        while (!currPos.equals(startCoords)) {
            if (input.get(currPos.getLeft()).get(currPos.getRight()) == '-') {
                if (direction == Direction.WEST) {
                    //check south
                    count += findNeighbors(currPos.getLeft() + 1, currPos.getRight(), loop);
                } else  if (direction == Direction.EAST) {
                    // check north
                    count += findNeighbors(currPos.getLeft() - 1, currPos.getRight(), loop);
                }
            } else if (input.get(currPos.getLeft()).get(currPos.getRight()) == '|') {
                if (direction == Direction.SOUTH) {
                    //check EAST
                    count += findNeighbors(currPos.getLeft(), currPos.getRight() + 1, loop);
                } else if (direction == Direction.NORTH) {
                    // check west
                    count += findNeighbors(currPos.getLeft(), currPos.getRight() - 1, loop);
                }
            } else if (input.get(currPos.getLeft()).get(currPos.getRight()) == 'F') {
                if (direction == Direction.SOUTH) {
                    //check NORTH
                    count += findNeighbors(currPos.getLeft() - 1, currPos.getRight(), loop);
                    // check WEST
                    count += findNeighbors(currPos.getLeft(), currPos.getRight() - 1, loop);
                    // check NORTH WEST
                    count += findNeighbors(currPos.getLeft() - 1, currPos.getRight() - 1, loop);
                } else if (direction == Direction.EAST) {
                    // check SOUTH EAST
                    count += findNeighbors(currPos.getLeft() + 1, currPos.getRight() + 1, loop);

                }
            } else if (input.get(currPos.getLeft()).get(currPos.getRight()) == 'L') {
                if (direction == Direction.NORTH) {
                    //check SOUTH
                    count += findNeighbors(currPos.getLeft() + 1, currPos.getRight(), loop);
                    // check WEST
                    count += findNeighbors(currPos.getLeft(), currPos.getRight() - 1, loop);
                    // check SOUTH WEST
                    count += findNeighbors(currPos.getLeft() + 1, currPos.getRight() - 1, loop);
                } else if (direction == Direction.EAST) {
                    // check NORTH EAST
                    count += findNeighbors(currPos.getLeft() - 1, currPos.getRight() + 1, loop);

                }
            } else if (input.get(currPos.getLeft()).get(currPos.getRight()) == 'J') {
                if (direction == Direction.NORTH) {
                    //check SOUTH
                    count += findNeighbors(currPos.getLeft() + 1, currPos.getRight(), loop);
                    // check EAST
                    count += findNeighbors(currPos.getLeft(), currPos.getRight() + 1, loop);
                    // check SOUTH EAST
                    count += findNeighbors(currPos.getLeft() + 1, currPos.getRight() + 1, loop);
                } else if (direction == Direction.EAST) {
                    // check NORTH WEST
                    count += findNeighbors(currPos.getLeft() - 1, currPos.getRight() - 1, loop);

                }
            } else if (input.get(currPos.getLeft()).get(currPos.getRight()) == '7') {
                if (direction == Direction.WEST) {
                    //check NORTH
                    count += findNeighbors(currPos.getLeft() - 1, currPos.getRight(), loop);
                    // check EAST
                    count += findNeighbors(currPos.getLeft(), currPos.getRight() + 1, loop);
                    // check NORTH EAST
                    count += findNeighbors(currPos.getLeft() - 1, currPos.getRight() + 1, loop);
                } else if (direction == Direction.SOUTH) {
                    // check SOUTH WEST
                    count += findNeighbors(currPos.getLeft() + 1, currPos.getRight() - 1, loop);

                }
            }
            currPos = stepForwardInLoop(currPos);
        }
        for (Pair<Integer, Integer> point : loop) {
            input.get(point.getLeft()).set(point.getRight(), 'P');
        }
        for (List<Character> row : input){
            System.out.println(row);
        }
        return count;
    }

    private int findNeighbors(int i, int j, List<Pair<Integer, Integer>> loop) {
        if (i < 0 || j < 0 || j >= input.get(0).size() || i >= input.size() || loop.contains(Pair.of(i, j)) || input.get(i).get(j) == 'I') {
            return 0;
        }
        input.get(i).set(j, 'I');
        return 1 + findNeighbors(i + 1, j, loop) +
                findNeighbors(i - 1, j, loop) +
                findNeighbors(i, j + 1, loop) +
                findNeighbors(i, j - 1, loop);
    }

    private Pair<Integer, Integer> findStartCoords() {
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j <input.get(0).size(); j++) {
                if (input.get(i).get(j) == 'S') {
                    return Pair.of(i, j);
                }
            }
        }
        throw new IllegalStateException("Could not find starting position.");
    }

    private Pair<Integer, Integer> findLoopEnd(Pair<Integer, Integer> startCoords) {
        if (startCoords.getLeft() - 1 >= 0 &&
                (input.get(startCoords.getLeft() - 1).get(startCoords.getRight()) == '|' ||
                        input.get(startCoords.getLeft() - 1).get(startCoords.getRight()) == '7' ||
                        input.get(startCoords.getLeft() - 1).get(startCoords.getRight()) == 'F')) {

            direction = Direction.NORTH;
            return Pair.of(startCoords.getLeft() - 1, startCoords.getRight());
        }
        if (startCoords.getLeft() + 1 < input.size() &&
                (input.get(startCoords.getLeft() + 1).get(startCoords.getRight()) == '|' ||
                        input.get(startCoords.getLeft() + 1).get(startCoords.getRight()) == 'L' ||
                        input.get(startCoords.getLeft() + 1).get(startCoords.getRight()) == 'J')) {

            direction = Direction.SOUTH;
            return Pair.of(startCoords.getLeft() + 1, startCoords.getRight());
        }
        if (startCoords.getRight() - 1 >= 0 &&
                (input.get(startCoords.getRight()).get(startCoords.getRight() - 1) == '|' ||
                        input.get(startCoords.getRight()).get(startCoords.getRight() - 1) == '7' ||
                        input.get(startCoords.getRight()).get(startCoords.getRight() - 1) == 'F')) {

            direction = Direction.WEST;
            return Pair.of(startCoords.getLeft(), startCoords.getRight() - 1);
        }
        if (startCoords.getRight() + 1 >= 0 &&
                (input.get(startCoords.getLeft()).get(startCoords.getRight() + 1) == '|' ||
                        input.get(startCoords.getLeft()).get(startCoords.getRight() + 1) == '7' ||
                        input.get(startCoords.getLeft()).get(startCoords.getRight() + 1) == 'F')) {

            direction = Direction.EAST;
            return Pair.of(startCoords.getLeft(), startCoords.getRight() + 1);
        }
        throw new IllegalStateException("Could not find loop end.");
    }

    private Pair<Integer, Integer> stepForwardInLoop(Pair<Integer, Integer> currPos) {
        if (input.get(currPos.getLeft()).get(currPos.getRight()) == '|') {
            if (direction == Direction.NORTH) {
                currPos = Pair.of(currPos.getLeft() - 1, currPos.getRight());
            } else if (direction == Direction.SOUTH) {
                currPos = Pair.of(currPos.getLeft() + 1, currPos.getRight());
            } else {
                throw new IllegalStateException("Invalid move. Direction: " + direction + ", Position: " + currPos);
            }
        } else if (input.get(currPos.getLeft()).get(currPos.getRight()) == '7') {
            if (direction == Direction.NORTH) {
                currPos = Pair.of(currPos.getLeft(), currPos.getRight() - 1);
                direction = Direction.WEST;
            } else if (direction == Direction.EAST) {
                direction = Direction.SOUTH;
                currPos = Pair.of(currPos.getLeft() + 1, currPos.getRight());
            } else {
                throw new IllegalStateException("Invalid move. Direction: " + direction + ", Position: " + currPos);
            }
        } else if (input.get(currPos.getLeft()).get(currPos.getRight()) == 'F') {
            if (direction == Direction.NORTH) {
                currPos = Pair.of(currPos.getLeft(), currPos.getRight() + 1);
                direction = Direction.EAST;
            } else if (direction == Direction.WEST) {
                direction = Direction.SOUTH;
                currPos = Pair.of(currPos.getLeft() + 1, currPos.getRight());
            } else {
                throw new IllegalStateException("Invalid move. Direction: " + direction + ", Position: " + currPos);
            }
        } else if (input.get(currPos.getLeft()).get(currPos.getRight()) == '-') {
            if (direction == Direction.EAST) {
                currPos = Pair.of(currPos.getLeft(), currPos.getRight() + 1);
            } else if (direction == Direction.WEST) {
                currPos = Pair.of(currPos.getLeft(), currPos.getRight() - 1);
            } else {
                throw new IllegalStateException("Invalid move. Direction: " + direction + ", Position: " + currPos);
            }
        } else if (input.get(currPos.getLeft()).get(currPos.getRight()) == 'L') {
            if (direction == Direction.SOUTH) {
                direction = Direction.EAST;
                currPos = Pair.of(currPos.getLeft(), currPos.getRight() + 1);
            } else if (direction == Direction.WEST) {
                direction = Direction.NORTH;
                currPos = Pair.of(currPos.getLeft() - 1, currPos.getRight());
            } else {
                throw new IllegalStateException("Invalid move. Direction: " + direction + ", Position: " + currPos);
            }
        } else if (input.get(currPos.getLeft()).get(currPos.getRight()) == 'J') {
            if (direction == Direction.SOUTH) {
                direction = Direction.WEST;
                currPos = Pair.of(currPos.getLeft(), currPos.getRight() - 1);
            } else if (direction == Direction.EAST) {
                direction = Direction.NORTH;
                currPos = Pair.of(currPos.getLeft() - 1, currPos.getRight());
            } else {
                throw new IllegalStateException("Invalid move. Direction: " + direction + ", Position: " + currPos);
            }
        } else {
            throw new IllegalStateException("Invalid move. Direction: " + direction + ", Position: " + currPos);
        }
        return currPos;
    }
}
