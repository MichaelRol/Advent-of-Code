package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day2 implements Day {

    private final List<String> lines = new ArrayList<>();

    public Day2(String inputPath) {
        ClassLoader classLoader = Day2.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
            // Use BufferedReader to read the content of the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // Read lines and add them to the List
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            System.out.println("File with path: " + inputPath + " could not be read.");
            System.exit(1);
        }
    }

    @Override
    public int Part1() {
        int sum = 0;
        for (Map.Entry<Integer, Map<String, Integer>> game : processInput().entrySet()) {
            Integer red = game.getValue().getOrDefault("red", 0);
            Integer green = game.getValue().getOrDefault("green", 0);
            Integer blue = game.getValue().getOrDefault("blue", 0);
            if (red <= 12 && green <= 13 && blue <= 14) {
                sum += game.getKey();
            }
        }
        return sum;
    }

    @Override
    public int Part2() {
        int sum = 0;
        for (Map<String, Integer> game : processInput().values()) {
            int product = 1;
            for (Integer count : game.values()) {
                product += count;
            }
            sum += product;
        }
        return sum;
    }

    private Map<Integer, Map<String, Integer>> processInput() {
        Map<Integer, Map<String, Integer>> games = new HashMap<>();
        for (String line : lines) {
            int gameNumber = Integer.parseInt(line.split(": ")[0].split(" ")[1]);
            String[] amountsAndColours = line.split(": ")[1].split("(, )|(; )");
            HashMap<String, Integer> coloursToAmounts = new HashMap<>();
            for (String amountAndColour : amountsAndColours) {
                String[] splitAmountAndColour = amountAndColour.split(" ");
                String colour = splitAmountAndColour[1];
                int amount = Integer.parseInt(splitAmountAndColour[0]);
                coloursToAmounts.computeIfPresent(colour, (k, v) -> Math.max(v, amount));
                coloursToAmounts.putIfAbsent(colour, amount);
            }
            games.put(gameNumber, coloursToAmounts);
        }
        return games;
    }
}
