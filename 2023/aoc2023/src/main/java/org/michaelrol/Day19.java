/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    findPathsToA(pathsToA, "in");
    return sum;
  }

  private List<Map<String, Map<String, Integer>>> findPathsToA(
      Map<String, Map<String, Integer>> pathsToA,
      String curr) {

    List<Map<String, Map<String, Integer>>> paths = new ArrayList<>();
    for (Rule rule : workflows.get(curr).rules) {
      Map<String, Integer> comparisons = pathsToA.getOrDefault(rule.feature, new HashMap<>());
      String comparator = rule.comparator;
      int value = rule.value;
      String ifTrue = rule.ifTrue;
      if (comparator.equals(">")) {
        Integer limit = comparisons.getOrDefault(">", 0);
        if (value > limit) {
          limit = value;
        }
        comparisons.put(">", limit);
      } else if (comparator.equals("<")) {
        Integer limit = comparisons.getOrDefault("<", Integer.MAX_VALUE);
        if (value < limit) {
          limit = value;
        }
        comparisons.put("<", limit);
      }
      if (ifTrue.equals("A")) {
        paths.add(pathsToA);
      } else if (!ifTrue.equals("R")) {
        paths.addAll(findPathsToA(new HashMap<>(pathsToA), ifTrue));
      }
    }


    return paths;
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
