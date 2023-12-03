package org.michaelrol;

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

public class Day3 implements Day {

    private final List<List<Character>> input = new ArrayList<>();

    public Day3(String inputPath) {
        ClassLoader classLoader = Day3.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
            // Use BufferedReader to read the content of the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // Read lines and add them to the List
            String line;
            while ((line = reader.readLine()) != null) {
                List<Character> chars = line.chars().mapToObj(c -> (char) c).collect(toList());
                input.add(chars);
            }
        } catch (IOException ex) {
            System.out.println("File with path: " + inputPath + " could not be read.");
            System.exit(1);
        }
    }

    @Override
    public int Part1() {
        int sum = 0;
        for (int x = 0; x < input.size(); x++) {
            for (int y = 0; y < input.get(0).size(); y++) {
                if (Character.isDigit(input.get(x).get(y))) {
                    List<Character> number = new ArrayList<>(List.of(input.get(x).get(y)));
                    int lastDigit = y + 1;
                    while (lastDigit < input.get(0).size() && Character.isDigit(input.get(x).get(lastDigit))) {
                        number.add(input.get(x).get(lastDigit));
                        lastDigit++;
                    }
                    int numLength = number.size();
                    if (hasAdjacentSymbol(x, y, numLength)) {
                        String stringNum = number.stream()
                                .map(chr -> chr - '0')
                                .map(String::valueOf)
                                .collect(Collectors.joining());
                        sum += Integer.parseInt(stringNum);
                    }
                    y += numLength - 1;
                }
            }
        }
        return sum;
    }

    @Override
    public int Part2() {
        int sum = 0;
        for (int x = 0; x < input.size(); x++) {
            for (int y = 0; y < input.get(0).size(); y++) {
                if (input.get(x).get(y) == '*') {
                    List<Pair<Integer, Integer>> numberCoords = findSurroundingNumbers(x, y);
                    if (numberCoords.size() == 2) {
                        Pair<Integer, Integer> first = numberCoords.get(0);
                        Pair<Integer, Integer> second = numberCoords.get(1);
                        int firstNum = findNumber(first.getLeft(), first.getRight());
                        int secondNum = findNumber(second.getLeft(), second.getRight());
                        sum += firstNum * secondNum;
                    }
                }
            }
        }
        return sum;
    }

    private int findNumber(int x, int knownY) {
        int sum = input.get(x).get(knownY) - '0';
        if (knownY > 0 && Character.isDigit(input.get(x).get(knownY - 1))) {
            sum += (input.get(x).get(knownY - 1) - '0') * 10;
            if (knownY > 1 && Character.isDigit(input.get(x).get(knownY - 2))) {
                sum = (input.get(x).get(knownY - 2) - '0') * 100 + sum;
            } else if (knownY < input.get(0).size() - 1 && Character.isDigit(input.get(x).get(knownY + 1))) {
                sum = sum * 10 + (input.get(x).get(knownY + 1) - '0');
            }
        } else if (knownY < input.get(0).size() - 1 && Character.isDigit(input.get(x).get(knownY + 1))) {
            sum = sum * 10 + (input.get(x).get(knownY + 1) - '0');
            if (knownY < input.get(0).size() - 2 && Character.isDigit(input.get(x).get(knownY + 2))) {
                sum = sum * 10 + (input.get(x).get(knownY + 2) - '0');
            }
        }
        return sum;
    }

    private List<Pair<Integer, Integer>> findSurroundingNumbers(int gearX, int gearY) {
        ArrayList<Pair<Integer, Integer>> numbers = new ArrayList<>();
        int xLimit = gearX >= input.get(0).size() - 1 ? input.size() - 1 : gearX + 1;
        int yLimit = gearY >= input.get(0).size() - 1 ? input.size() - 1 : gearY + 1;
        for (int x = gearX <= 0 ? 0 : gearX - 1; x <= xLimit; x++) {
            for (int y = gearY <= 0 ? 0 : gearY - 1; y <= yLimit; y++) {
                if (Character.isDigit(input.get(x).get(y))) {
                    numbers.add(Pair.of(x, y));
                    if (Character.isDigit(input.get(x).get(y+1))) {
                        break;
                    }
                }
            }
        }
        return numbers;
    }

    private boolean hasAdjacentSymbol(int startX, int startY, int numLength) {
        List<Integer> xRange = new ArrayList<>(3);
        xRange.add(startX);
        if (startX > 0) {
            xRange.add(startX - 1);
        }
        if (startX < input.size() - 1) {
            xRange.add(startX + 1);
        }
        List<Integer> yRange = new ArrayList<>(IntStream.range(startY, startY + numLength).boxed().collect(toUnmodifiableList()));
        if (startY > 0) {
            yRange.add(startY - 1);
        }
        if (startY + numLength < input.get(0).size()) {
            yRange.add(startY + numLength);
        }
        for (int x : xRange) {
            for (int y : yRange) {
                if (x != startX || (y == startY - 1 || y == startY + numLength)) {
                    if (!input.get(x).get(y).equals('.') && !Character.isDigit(input.get(x).get(y))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
