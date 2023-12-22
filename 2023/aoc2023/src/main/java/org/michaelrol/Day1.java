package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public class Day1 implements Day {

    private final List<String> input = new ArrayList<>();

    public Day1(String inputPath) {
        ClassLoader classLoader = Day1.class.getClassLoader();
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
        Map<String, Integer> numbers = new HashMap<>();
        numbers.put("1", 1);
        numbers.put("2", 2);
        numbers.put("3", 3);
        numbers.put("4", 4);
        numbers.put("5", 5);
        numbers.put("6", 6);
        numbers.put("7", 7);
        numbers.put("8", 8);
        numbers.put("9", 9);
        return sumFirstAndLastNumbers(numbers);
    }

    @Override
    public long Part2() {
        Map<String, Integer> numbers = new HashMap<>();
        numbers.put("1", 1);
        numbers.put("2", 2);
        numbers.put("3", 3);
        numbers.put("4", 4);
        numbers.put("5", 5);
        numbers.put("6", 6);
        numbers.put("7", 7);
        numbers.put("8", 8);
        numbers.put("9", 9);
        numbers.put("one", 1);
        numbers.put("two", 2);
        numbers.put("three", 3);
        numbers.put("four", 4);
        numbers.put("five", 5);
        numbers.put("six", 6);
        numbers.put("seven", 7);
        numbers.put("eight", 8);
        numbers.put("nine", 9);
        return sumFirstAndLastNumbers(numbers);
    }

    private int sumFirstAndLastNumbers(Map<String, Integer> numbers) {
        int sum = 0;
        for (String line : input) {
            line = line.toLowerCase();
            sum += 10 * findFirstNumber(line, numbers) + findLastNumber(line, numbers);
        }
        return sum;
    }

    private int findFirstNumber(String line, Map<String, Integer> numbers) {
        return numbers.keySet().stream()
                .filter(line::contains)
                .map(num -> Pair.of(line.indexOf(num), numbers.get(num)))
                .min(Comparator.comparingInt(Pair::getLeft))
                .map(Pair::getRight)
                .orElse(0);
    }

    private int findLastNumber(String line, Map<String, Integer> numbers) {
        return numbers.keySet().stream()
                .filter(line::contains)
                .map(num -> Pair.of(line.lastIndexOf(num), numbers.get(num)))
                .max(Comparator.comparingInt(Pair::getLeft))
                .map(Pair::getRight)
                .orElse(0);
    }
}
