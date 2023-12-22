package org.michaelrol;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class Day7 implements Day {

  private final Map<String, Integer> input = new HashMap<>();

  public Day7(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        String[] splitLine = line.split(" ");
        int bid = Integer.parseInt(splitLine[1]);
        input.put(splitLine[0], bid);
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    Comparator<List<Integer>> camelComparator = (hand1, hand2) -> {
      int hand1Rank = identifyRank(hand1);
      int hand2Rank = identifyRank(hand2);
      if (hand1Rank != hand2Rank) {
        return hand1Rank - hand2Rank;
      }
      for (int i = 0; i < hand1.size(); i++) {
        if (!Objects.equals(hand1.get(i), hand2.get(i))) {
          return hand1.get(i) - hand2.get(i);
        }
      }
      return 0;
    };

    Map<String, String> pictureCards = Map.of(
        "A", "14",
        "K", "13",
        "Q", "12",
        "J", "11",
        "T", "10");

    SortedMap<List<Integer>, Integer> sortedMap = createdSortedMap(camelComparator, pictureCards);
    return calculateScore(sortedMap);
  }

  @Override
  public long Part2() {
    Comparator<List<Integer>> camelComparator = (hand1, hand2) -> {
      int hand1Rank = identifyRankPart2(hand1);
      int hand2Rank = identifyRankPart2(hand2);
      if (hand1Rank != hand2Rank) {
        return hand1Rank - hand2Rank;
      }
      for (int i = 0; i < hand1.size(); i++) {
        if (!Objects.equals(hand1.get(i), hand2.get(i))) {
          return hand1.get(i) - hand2.get(i);
        }
      }
      return 0;
    };

    Map<String, String> pictureCards = Map.of(
        "A", "13",
        "K", "12",
        "Q", "11",
        "J", "1",
        "T", "10");


    SortedMap<List<Integer>, Integer> sortedMap = createdSortedMap(camelComparator, pictureCards);
    return calculateScore(sortedMap);
  }

  private SortedMap<List<Integer>, Integer> createdSortedMap(
      Comparator<List<Integer>> camelComparator,
      Map<String, String> pictureCards) {
    SortedMap<List<Integer>, Integer> sortedMap = new TreeMap<>(camelComparator);
    for (Map.Entry<String, Integer> handAndBid : input.entrySet()) {
      List<Integer> cards = handAndBid.getKey().chars()
          .mapToObj(chr -> String.valueOf((char) chr))
          .map(chr -> pictureCards.getOrDefault(chr, chr))
          .mapToInt(Integer::parseInt)
          .boxed()
          .collect(toList());
      sortedMap.put(cards, handAndBid.getValue());
    }
    return sortedMap;
  }

  private static int calculateScore(SortedMap<List<Integer>, Integer> sortedMap) {
    int score = 0;
    int rank = 1;
    for (int bid : sortedMap.values()) {
      score += rank * bid;
      rank++;
    }
    return score;
  }

  private int identifyRank(List<Integer> hand) {
    int distinctCards = (int) hand.stream().distinct().count();
    switch (distinctCards) {
      case 1:
        return 6;
      case 2:
        if (getMaxCount(hand) == 4) {
          return 5;
        }
        return 4;
      case 3:
        if (getMaxCount(hand) == 3) {
          return 3;
        }
        return 2;
      case 4:
        return 1;
      default:
        return 0;
    }
  }

  private int identifyRankPart2(List<Integer> hand) {
    int distinctCards = (int) hand.stream().distinct().count();
    int jokers = (int) hand.stream().filter(card -> card == 1).count();
    switch (distinctCards) {
      case 1:
        return 6;
      case 2:
        if (getMaxCount(hand) == 4) {
          if (jokers != 0) {
            return 6;
          }
          return 5;
        }
        if (jokers >= 2) {
          return 6;
        } else if (jokers == 1) {
          return 5;
        }
        return 4;
      case 3:
        if (getMaxCount(hand) == 3) {
          if (jokers != 0) {
            return 5;
          }
          return 3;
        }
        if (jokers == 1) {
          return 4;
        }
        if (jokers == 2) {
          return 5;
        }
        return 2;
      case 4:
        if (jokers != 0) {
          return 3;
        }
        return 1;
      default:
        if (jokers != 0) {
          return 1;
        }
        return 0;
    }
  }

  private static int getMaxCount(List<Integer> hand) {
    List<Integer> sortedHand = new java.util.ArrayList<>(hand);
    sortedHand.sort(Integer::compareTo);
    int prev = 0;
    int count = 0;
    int maxCount = 0;
    for (int card : sortedHand) {
      if (card == prev) {
        count++;
      } else {
        prev = card;
        count = 1;
      }
      if (count > maxCount) {
        maxCount = count;
      }
    }
    return maxCount;
  }
}
