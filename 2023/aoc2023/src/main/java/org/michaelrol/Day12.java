package org.michaelrol;

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Day12 implements Day {

    private final List<List<Character>> input = new ArrayList<>();
    private final List<List<Integer>> indices = new ArrayList<>();
    private final Map<Pair<List<Character>, List<Integer>>, Long> cache = new HashMap<>();

    public Day12(String inputPath) {
        ClassLoader classLoader = Day1.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
            // Use BufferedReader to read the content of the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // Read lines and add them to the List
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                input.add(splitLine[0].chars().mapToObj(chr -> (char) chr).collect(toList()));
                indices.add(Arrays.stream(splitLine[1].split(","))
                        .map(Integer::parseInt)
                        .collect(toList()));
            }
        } catch (IOException ex) {
            System.out.println("File with path: " + inputPath + " could not be read.");
            System.exit(1);
        }
    }

    @Override
    public long Part1() {
        long count = 0;
        for (int i = 0; i < input.size(); i++) {
            count += matchPattern(input.get(i), indices.get(i), false);
        }
        return count;
    }

    @Override
    public long Part2() {
        long count = 0;
        for (int i = 0; i < input.size(); i++) {
            cache.clear();
            List<Character> line = input.get(i);
            List<Integer> integers = indices.get(i);
            line.add('?');
            List<Character> allLines = Collections.nCopies(5, line).stream()
                    .flatMap(List::stream)
                    .collect(toList());
            allLines.remove(allLines.size() - 1);
            List<Integer> allIntegers = Collections.nCopies(5, integers).stream()
                    .flatMap(List::stream)
                    .collect(toList());
            count += matchPattern(allLines, allIntegers, false);
        }
        return count;
    }

    private long matchPattern(List<Character> line, List<Integer> integers, boolean inHashes) {
        if (cache.containsKey(Pair.of(line, integers))) {
            return cache.get(Pair.of(line, integers));
        }
        if (integers.isEmpty()) {
            return line.contains('#') ? 0 : 1;
        }
        if (line.isEmpty()) {
            return (integers.size() == 1 && integers.get(0) == 0) ? 1 : 0;
        }
        if (line.get(0) == '#') {
            if (integers.get(0) == 0) {
                return 0;
            }
            integers.set(0, integers.get(0) - 1);
            return matchPattern(line.subList(1, line.size()), integers, true);
        } else if (line.get(0) == '.') {
            if (integers.get(0) == 0) {
                integers.remove(0);
            } else {
                if (inHashes) {
                    return 0;
                }
            }
            return matchPattern(line.subList(1, line.size()), integers, false);
        } else {
            ArrayList<Character> dotList = new ArrayList<>(line);
            dotList.set(0, '.');
            ArrayList<Character> hashList = new ArrayList<>(line);
            hashList.set(0, '#');
            long dotPath = matchPattern(dotList, new ArrayList<>(integers), inHashes);
            long hashPath = matchPattern(hashList, new ArrayList<>(integers), inHashes);
            cache.put(Pair.of(line, integers), dotPath + hashPath);
            return dotPath + hashPath;
        }
    }
}
