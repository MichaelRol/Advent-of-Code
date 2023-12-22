package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day19 implements Day {

  private final Map<String, Workflow> workflows = new HashMap<>();
  private final List<Map<String, Integer>> parts = new ArrayList<>();

  public Day19(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.contains(":")) {
          String[] split = line.split("\\{");
          String code = split[0];
          String workflowString = split[1].substring(0, split[1].length() - 1);
          workflows.put(code, Workflow.parse(workflowString));
        } else if (!line.isBlank()) {
          Map<String, Integer> features = new HashMap<>();
          String substring = line.substring(1, line.length() - 1);
          String[] featuresAndValues = substring.split(",");
          for (String featureAndValue : featuresAndValues) {
            String[] split = featureAndValue.split("=");
            features.put(split[0], Integer.parseInt(split[1]));
          }
          parts.add(features);
        }
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    long sum = 0;
    for (Map<String, Integer> part : parts) {
      String result = workflows.get("in").apply(part);
      while (!result.equals("A") && !result.equals("R")) {
        result = workflows.get(result).apply(part);
      }
      if (result.equals("A")) {
        sum += part.values().stream().mapToInt(i -> i).sum();
      }
    }
    return sum;
  }

  @Override
  public long Part2() {
    long sum = 0;
    Map<String, Map<String, Integer>> pathsToA = new HashMap<>();
    List<Map<String, Map<String, Integer>>> results = findPathsToA(pathsToA, "in");
    for (Map<String, Map<String, Integer>> result : results) {
      List<Integer> maxsAndMins = new ArrayList<>(List.of(0, 4001, 0, 4001, 0, 4001, 0, 4001));
      List<String> keys = new ArrayList<>(List.of("a", "x", "s", "m"));
      for (int i = 0; i < keys.size(); i++) {
        String key = keys.get(i);
        if (result.containsKey(key)) {
          if (result.get(key).containsKey(">")) {
            maxsAndMins.set(i * 2, result.get(key).get(">"));
          }
          if (result.get(key).containsKey("<")) {
            maxsAndMins.set(i * 2 + 1, result.get(key).get("<"));
          }
        }
      }
      long product = 1;
      for (int i = 0; i < maxsAndMins.size(); i += 2) {
        product *= (maxsAndMins.get(i + 1) - maxsAndMins.get(i)) - 1;
      }
      sum += product;
    }
    return sum;
  }

  private List<Map<String, Map<String, Integer>>> findPathsToA(
      Map<String, Map<String, Integer>> pathsToA,
      String curr) {

    List<Map<String, Map<String, Integer>>> paths = new ArrayList<>();
    Map<String, Map<String, Integer>> applyFalse = deepCopyOuterMap(pathsToA);
    for (Rule rule : workflows.get(curr).rules) {
      Map<String, Map<String, Integer>> applyTrue = addRuleToPath(deepCopyOuterMap(applyFalse), rule, true);
      applyFalse = addRuleToPath(deepCopyOuterMap(applyFalse), rule, false);
      if (rule.ifTrue.equals("A")) {
        paths.add(applyTrue);
      } else if (!rule.ifTrue.equals("R")) {
        paths.addAll(findPathsToA(deepCopyOuterMap(applyTrue), rule.ifTrue));
      }
    }
    if (workflows.get(curr).otherwise.equals("A")) {
      paths.add(deepCopyOuterMap(applyFalse));
    } else if (!workflows.get(curr).otherwise.equals("R")) {
      paths.addAll(findPathsToA(deepCopyOuterMap(applyFalse), workflows.get(curr).otherwise));
    }
    return paths;
  }

  private Map<String, Map<String, Integer>> addRuleToPath(
      Map<String, Map<String, Integer>> pathsToA,
      Rule rule,
      boolean b) {

    Map<String, Integer> comparisons = pathsToA.getOrDefault(rule.feature, new HashMap<>());
    String comparator = rule.comparator;
    int value = rule.value;
    if ((comparator.equals(">") && b) || (comparator.equals("<") && !b)) {
      Integer limit = comparisons.getOrDefault(">", 0);
      if (value - (b ? 0 : 1) > limit) {
        limit = value - (b ? 0 : 1);
      }
      comparisons.put(">", limit);
    } else {
      Integer limit = comparisons.getOrDefault("<", Integer.MAX_VALUE);
      if (value + (b ? 0 : 1) < limit) {
        limit = value + (b ? 0 : 1);
      }
      comparisons.put("<", limit);
    }
    pathsToA.put(rule.feature, comparisons);
    return pathsToA;
  }

  private static Map<String, Map<String, Integer>> deepCopyOuterMap(Map<String, Map<String, Integer>> map) {
    return map.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> deepCopyInnerMap(e.getValue())));
  }

  private static Map<String, Integer> deepCopyInnerMap(Map<String, Integer> map) {
    return map.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private static class Workflow {

    List<Rule> rules;
    String otherwise;

    static Workflow parse(String workflowString) {
      String[] ruleStrings = workflowString.split(",");
      List<Rule> rules = new ArrayList<>();
      String otherwise = null;
      for (String rule : ruleStrings) {
        if (rule.contains(":")) {
          rules.add(Rule.parse(rule));
        } else {
          otherwise = rule;
        }
      }
      if (otherwise == null || rules.isEmpty()) {
        throw new IllegalStateException("Could not parse workflow.");
      }
      return new Workflow(rules, otherwise);
    }

    private Workflow(List<Rule> rules, String otherwise) {
      this.rules = rules;
      this.otherwise = otherwise;
    }

    String apply(Map<String, Integer> features) {
      for (Rule rule : rules) {
        Integer i = features.get(rule.feature);
        if (rule.comparator.equals(">")) {
          if (i > rule.value) {
            return rule.ifTrue;
          }
        } else if (rule.comparator.equals("<")) {
          if (i < rule.value) {
            return rule.ifTrue;
          }
        }
      }
      return otherwise;
    }

  }

  private static class Rule {

    String feature;
    String comparator;
    int value;
    String ifTrue;

    static Rule parse(String ruleString) {
      String[] ruleAndOutcome = ruleString.split(":");
      String ifTrue = ruleAndOutcome[1];
      if (ruleAndOutcome[0].contains(">")) {
        String[] featureAndValue = ruleAndOutcome[0].split(">");
        return new Rule(featureAndValue[0], ">", Integer.parseInt(featureAndValue[1]), ifTrue);
      }
      String[] featureAndValue = ruleAndOutcome[0].split("<");
      return new Rule(featureAndValue[0], "<", Integer.parseInt(featureAndValue[1]), ifTrue);
    }

    private Rule(String feature, String comparator, int value, String ifTrue) {
      this.feature = feature;
      this.comparator = comparator;
      this.value = value;
      this.ifTrue = ifTrue;
    }
  }
}
