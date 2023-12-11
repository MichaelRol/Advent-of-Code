package org.michaelrol;

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        insertCorrectS(startCoords);
        Pair<Integer, Integer> currPos = findLoopEnd(startCoords);
        Set<Pair<Integer, Integer>> loop = new HashSet<>();
        loop.add(startCoords);
        loop.add(currPos);
        while (!currPos.equals(startCoords)) {
            currPos = stepForwardInLoop(currPos);
            loop.add(currPos);
        }
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).size(); j++) {
                if (!loop.contains(Pair.of(i, j))) {
                    input.get(i).set(j, ' ');
                }
            }
        }
        int count = 0;
        for (int i = 0; i < input.size(); i++) {
            boolean inside = false;
            char firstHit = ' ';
            for (int j = 0; j < input.get(0).size(); j++) {
                Character currChar = getCharacterAt(i, j);
                if (currChar != ' ') {
                    if (firstHit == ' ') {
                        firstHit = currChar;
                    }
                    if (currChar == '|') {
                        inside = !inside;
                        firstHit = ' ';
                    }
                    if (currChar == 'J' && firstHit == 'F') {
                        inside = !inside;
                        firstHit = ' ';
                    }
                    if (currChar == 'J' && firstHit == 'L') {
                        firstHit = ' ';
                    }
                    if (currChar == '7' && firstHit == 'L') {
                        inside = !inside;
                        firstHit = ' ';
                    }
                    if (currChar == '7' && firstHit == 'F') {
                        firstHit = ' ';
                    }
                } else if (inside) {
                    count++;
                    firstHit = ' ';
                }
            }
        }
        return count;
    }

    private void insertCorrectS(Pair<Integer, Integer> startCoords) {
        List<Direction> directions = new ArrayList<>();
        int y = startCoords.getLeft();
        int x = startCoords.getRight();
        if (y > 0 && List.of('|', '7', 'F').contains(getCharacterAt(y - 1, x))) {
            directions.add(Direction.NORTH);
        }
        if (y < input.size() && List.of('|', 'L', 'J').contains(getCharacterAt(y + 1, x))) {
            directions.add(Direction.SOUTH);
        }
        if (x > 0 && List.of('-', 'L', 'F').contains(getCharacterAt(y, x - 1))) {
            directions.add(Direction.WEST);
        }
        if (x < input.get(0).size() && List.of('-', 'J', '7').contains(getCharacterAt(y, x + 1))) {
            directions.add(Direction.EAST);
        }
        if (directions.contains(Direction.NORTH) && directions.contains(Direction.SOUTH)) {
            input.get(y).set(x, '|');
        } else if (directions.contains(Direction.NORTH) && directions.contains(Direction.EAST)) {
            input.get(y).set(x, 'L');
        } else if (directions.contains(Direction.NORTH) && directions.contains(Direction.WEST)) {
            input.get(y).set(x, 'J');
        } else if (directions.contains(Direction.SOUTH) && directions.contains(Direction.EAST)) {
            input.get(y).set(x, 'F');
        } else if (directions.contains(Direction.SOUTH) && directions.contains(Direction.WEST)) {
            input.get(y).set(x, '7');
        } else {
            throw new IllegalStateException("Could not find start pipe.");
        }
    }

    private Character getCharacterAt(int i, int j) {
        return input.get(i).get(j);
    }

    private Pair<Integer, Integer> findStartCoords() {
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).size(); j++) {
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
