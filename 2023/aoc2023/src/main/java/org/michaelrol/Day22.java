package org.michaelrol;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Triple;

public class Day22 implements Day {

  private final List<List<Triple<Integer, Integer, Integer>>> input = new ArrayList<>();

  public Day22(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split("~");
        List<Integer> first = Arrays.stream(split[0].split(","))
            .mapToInt(Integer::parseInt)
            .boxed()
            .collect(toList());
        List<Integer> second = Arrays.stream(split[1].split(","))
            .mapToInt(Integer::parseInt).boxed()
            .collect(toList());
        List<Triple<Integer, Integer, Integer>> block = findBlock(first, second);
        input.add(block);
      }
      input.sort((a, b) -> a.stream().map(Triple::getRight).mapToInt(i -> i).min().getAsInt() -
          b.stream().map(Triple::getRight).mapToInt(i -> i).min().getAsInt());
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  private List<Triple<Integer, Integer, Integer>> findBlock(List<Integer> first, List<Integer> second) {
    List<Triple<Integer, Integer, Integer>> block = new ArrayList<>();
    for (int x = Math.min(first.get(0), second.get(0)); x <= Math.max(first.get(0), second.get(0)); x++) {
      for (int y = Math.min(first.get(1), second.get(1)); y <= Math.max(first.get(1), second.get(1)); y++) {
        for (int z = Math.min(first.get(2), second.get(2)); z <= Math.max(first.get(2), second.get(2)); z++) {
          block.add(Triple.of(x, y, z));
        }
      }
    }
    return block;
  }

  @Override
  public long Part1() {
    Map<Triple<Integer, Integer, Integer>, Integer> blockMap = produceBlockMap(); //TODO: use tree
    Map<Integer, Set<Integer>> supportMap = new HashMap<>();
    for (List<Triple<Integer, Integer, Integer>> triples : input) {
      List<Triple<Integer, Integer, Integer>> triplesCopy = new ArrayList<>(triples);
      boolean settled = false;
      while (!settled) {
        List<Triple<Integer, Integer, Integer>> newTriple = triplesCopy.stream()
            .map(triple -> Triple.of(triple.getLeft(), triple.getMiddle(), triple.getRight() - 1))
            .collect(toList());
        settled = checkIfSettled(newTriple, settled, blockMap, triplesCopy, supportMap);
        if (!settled) {
          updateBlockMap(blockMap, triplesCopy, newTriple);
        }
        triplesCopy = newTriple;
      }
    }

    Set<Integer> safeToRemove = findSafeToRemove(blockMap, supportMap);
    return safeToRemove.size();
  }

  @Override
  public long Part2() {
    Map<Triple<Integer, Integer, Integer>, Integer> blockMap = produceBlockMap();
    Map<Integer, Set<Integer>> supportMap = new HashMap<>();
    for (List<Triple<Integer, Integer, Integer>> triples : input) {
      List<Triple<Integer, Integer, Integer>> triplesCopy = new ArrayList<>(triples);
      boolean settled = false;
      while (!settled) {
        List<Triple<Integer, Integer, Integer>> newTriple = triplesCopy.stream()
            .map(triple -> Triple.of(triple.getLeft(), triple.getMiddle(), triple.getRight() - 1))
            .collect(toList());
        settled = checkIfSettled(newTriple, settled, blockMap, triplesCopy, supportMap);
        if (!settled) {
          updateBlockMap(blockMap, triplesCopy, newTriple);
        }
        triplesCopy = newTriple;
      }
    }
    long sum = 0;
    for (int i = 0; i < input.size(); i++) {
      int collapsed = 0;
      Map<Integer, Set<Integer>> supportMapCopy = deepCopyMap(supportMap);
      Queue<Integer> toRemove = new ArrayDeque<>(List.of(i));
      while (!toRemove.isEmpty()) {
        Integer removing = toRemove.poll();
        for (Set<Integer> supporters : supportMapCopy.values()) {
          supporters.remove(removing);
        }
        for (Integer blockNumber : new HashSet<>(supportMapCopy.keySet())) {
          if (supportMapCopy.get(blockNumber).isEmpty()) {
            supportMapCopy.remove(blockNumber);
            collapsed++;
            toRemove.add(blockNumber);
          }
        }
      }
      sum += collapsed;
    }
    return sum;
  }

  private Map<Triple<Integer, Integer, Integer>, Integer> produceBlockMap() {
    Map<Triple<Integer, Integer, Integer>, Integer> blockMap = new HashMap<>();
    for (List<Triple<Integer, Integer, Integer>> triples : input) {
      for (Triple<Integer, Integer, Integer> triple : triples) {
        blockMap.put(triple, input.indexOf(triples));
      }
    }
    return blockMap;
  }

  private static void updateBlockMap(
      Map<Triple<Integer, Integer, Integer>, Integer> blockMap,
      List<Triple<Integer, Integer, Integer>> triplesCopy,
      List<Triple<Integer, Integer, Integer>> newTriple) {

    int blockToMove = blockMap.get(triplesCopy.get(0));
    triplesCopy.forEach(blockMap::remove);
    newTriple.forEach(newBlock -> blockMap.put(newBlock, blockToMove));
  }

  private static boolean checkIfSettled(
      List<Triple<Integer, Integer, Integer>> newTriple,
      boolean settled,
      Map<Triple<Integer, Integer, Integer>, Integer> blockMap,
      List<Triple<Integer, Integer, Integer>> triplesCopy,
      Map<Integer, Set<Integer>> supportMap) {

    for (Triple<Integer, Integer, Integer> block : newTriple) {
      if (block.getRight() <= 0) {
        settled = true;
        break;
      } else if (blockMap.containsKey(block)) {
        Integer onBot = blockMap.get(block);
        int onTop = blockMap.get(triplesCopy.get(0));
        if (onTop == onBot) {
          continue;
        }
        settled = true;
        Set<Integer> existingSupports = supportMap.getOrDefault(onTop, new HashSet<>());
        existingSupports.add(onBot);
        supportMap.put(onTop, existingSupports);
      }
    }
    return settled;
  }

  private static Set<Integer> findSafeToRemove(
      Map<Triple<Integer, Integer, Integer>, Integer> blockMap,
      Map<Integer, Set<Integer>> supportMap) {

    Set<Integer> possibles = new HashSet<>(blockMap.values());
    for (Set<Integer> value : supportMap.values()) {
      if (value.size() == 1) {
        possibles.removeAll(value);
      }
    }
    return possibles;
  }

  private static Map<Integer, Set<Integer>> deepCopyMap(Map<Integer, Set<Integer>> map) {
    return map.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, entry -> new HashSet<>(entry.getValue())));
  }
}
