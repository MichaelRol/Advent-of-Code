package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toUnmodifiableList;

public class Day9 implements Day {

    private final List<List<Integer>> input = new ArrayList<>();

    public Day9(String inputPath) {
        ClassLoader classLoader = Day1.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
            // Use BufferedReader to read the content of the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // Read lines and add them to the List
            String line;
            while ((line = reader.readLine()) != null) {
                input.add(Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(toUnmodifiableList()));
            }
        } catch (IOException ex) {
            System.out.println("File with path: " + inputPath + " could not be read.");
            System.exit(1);
        }
    }

    @Override
    public long Part1() {
        int sum = 0;
        for (int i = 0; i < input.size(); i++) {
            List<Integer> currList = input.get(i);
            List<List<Integer>> differences = new ArrayList<>();
            while (differences.isEmpty() || differences.get(differences.size() - 1).stream().anyMatch(num -> !Objects.equals(num, differences.get(differences.size() - 1).get(0)))) {
                List<Integer> newList = new ArrayList<>();
                for (int j = 0; j < currList.size() - 1; j++) {
                    newList.add(currList.get(j + 1) - currList.get(j));
                }
                differences.add(newList);
                currList = newList;
                if (newList.stream().allMatch(num -> num.equals(newList.get(0)))) {
                    int nums = 0;
                    for (List<Integer> list : differences) {
                        nums += list.get(list.size() - 1);
                    }
                    sum += (nums + input.get(i).get(input.get(i).size() - 1));
                    break;
                }
            }
        }
        return sum;
    }

    @Override
    public long Part2() {
        int sum = 0;
        for (int i = 0; i < input.size(); i++) {
            List<Integer> currList = input.get(i);
            List<List<Integer>> differences = new ArrayList<>();
            while (differences.isEmpty() || differences.get(differences.size() - 1).stream().anyMatch(num -> !Objects.equals(num, differences.get(differences.size() - 1).get(0)))) {
                List<Integer> newList = new ArrayList<>();
                for (int j = 0; j < currList.size() - 1; j++) {
                    newList.add(currList.get(j + 1) - currList.get(j));
                }
                differences.add(newList);
                currList = newList;
                if (newList.stream().allMatch(num -> num.equals(newList.get(0)))) {
                    int num = differences.get(differences.size() - 1).get(0);
                    for (int k = differences.size() - 1; k > 0; k--) {
                        int firstNum = differences.get(k - 1).get(0);
                        num = firstNum - num;
                    }
                    sum += (input.get(i).get(0) - num);
                    break;
                }
            }
        }
        return sum;
    }
}
