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
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.lang3.NotImplementedException;

public class Day20 implements Day {

  private final Map<String, Module> input1 = new HashMap<>();
  private final Map<String, Module> input2 = new HashMap<>();

  private static final Map<Boolean, Long> pulses = new HashMap<>(Map.of(true, 0L, false, 0L));
  private static long presses = 0;
  private static long jgLoop = 0;
  private static long kvLoop = 0;
  private static long rzLoop = 0;
  private static long mrLoop = 0;


  public Day20(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        Module module1 = Module.parse(line);
        input1.put(module1.getName(), module1);
        Module module2 = Module.parse(line);
        input2.put(module2.getName(), module2);
      }
      for (String name : input1.keySet()) {
        for (String target : input1.get(name).getTargets()) {
          if (input1.containsKey(target) && input1.get(target) instanceof Conjunction) {
            ((Conjunction) input1.get(target)).inputMap.put(name, false);
          }
        }
      }
      for (String name : input2.keySet()) {
        for (String target : input2.get(name).getTargets()) {
          if (input2.containsKey(target) && input2.get(target) instanceof Conjunction) {
            ((Conjunction) input2.get(target)).inputMap.put(name, false);
          }
        }
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    Button button = new Button();
    for (int i = 0; i < 1000; i++) {
      Queue<Module> queue = new ArrayDeque<>();
      queue.add(button);
      processSignals(queue, input1);
    }
    return pulses.get(true) * pulses.get(false);
  }

  @Override
  public long Part2() {
    Button button = new Button();
    while (kvLoop == 0 || jgLoop == 0 || rzLoop == 0 || mrLoop == 0) {
      Queue<Module> queue = new ArrayDeque<>();
      queue.add(button);
      presses++;
      processSignals(queue, input2);
    }
    return kvLoop * jgLoop * rzLoop * mrLoop;
  }

  private void processSignals(Queue<Module> queue, Map<String, Module> modules) {
    while (!queue.isEmpty()) {
      Module module = queue.poll();
      module.outputSignal(modules);
      for (String target : module.getTargets()) {
        if (modules.containsKey(target)) {
          Module targetModule = modules.get(target);
          if (targetModule.canPulse()) {
            queue.add(targetModule);
          }
        }
      }
    }
  }

  private interface Module {

    public abstract void receiveInput(boolean pulse, String sender);

    public abstract void outputSignal(Map<String, Module> modules);

    public abstract List<String> getTargets();

    public abstract String getName();

    public abstract boolean canPulse();

    public static Module parse(String line) {
      String[] split = line.split(" -> ");
      String name = split[0];
      List<String> targets = List.of(split[1].split(", "));
      if (name.equals("broadcaster")) {
        return new Broadcaster(targets);
      }
      if (name.startsWith("%")) {
        return new FlipFlop(name.replace("%", ""), targets);
      }
      if (name.startsWith("&")) {
        return new Conjunction(name.replace("&", ""), targets);
      }
      throw new IllegalArgumentException("Could not identify module type.");
    }
  }

  private static class FlipFlop implements Module {

    private final String name;
    private boolean state;
    private boolean last;
    private final List<String> targets;

    private FlipFlop(String name, List<String> targets) {
      this.name = name;
      this.targets = targets;
      this.state = false;
      this.last = false;
    }

    @Override
    public void receiveInput(boolean pulse, String sender) {
      if (!pulse) {
        state = !state;
      }
      last = pulse;
    }

    @Override
    public void outputSignal(Map<String, Module> modules) {
      targets.forEach(target -> {
        if (modules.containsKey(target)) {
          modules.get(target).receiveInput(state, getName());
        }
        pulses.put(state, pulses.get(state) + 1);
      });
    }

    @Override
    public List<String> getTargets() {
      return targets;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public boolean canPulse() {
      return !last;
    }
  }

  private static class Conjunction implements Module {

    private final String name;
    private final List<String> targets;
    private final Map<String, Boolean> inputMap = new HashMap<>();

    private Conjunction(String name, List<String> targets) {
      this.name = name;
      this.targets = targets;
    }

    public boolean getState() {
      for (Boolean value : inputMap.values()) {
        if (!value) {
          return true;
        }
      }
      return false;
    }

    @Override
    public void receiveInput(boolean pulse, String sender) {
      inputMap.put(sender, pulse);
    }

    @Override
    public void outputSignal(Map<String, Module> modules) {
      getTargets().forEach(target -> {
        boolean state = getState();
        if (state) {
          switch (name) {
            case "kv":
              kvLoop = presses;
              break;
            case "jg":
              jgLoop = presses;
              break;
            case "rz":
              rzLoop = presses;
              break;
            case "mr":
              mrLoop = presses;
              break;
          }
        }
        if (modules.containsKey(target)) {
          modules.get(target).receiveInput(state, getName());
        }
        pulses.put(state, pulses.get(state) + 1);
      });
    }

    @Override
    public List<String> getTargets() {
      return targets;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public boolean canPulse() {
      return true;
    }
  }

  private static class Broadcaster implements Module {

    private boolean state;
    private final List<String> targets;

    public Broadcaster(List<String> targets) {
      this.targets = targets;
      state = false;
    }

    @Override
    public void receiveInput(boolean pulse, String sender) {
      state = pulse;
    }

    @Override
    public void outputSignal(Map<String, Module> modules) {
      getTargets().forEach(target -> {
        if (modules.containsKey(target)) {
          modules.get(target).receiveInput(state, getName());
        }
        pulses.put(state, pulses.get(state) + 1);
      });
    }

    @Override
    public List<String> getTargets() {
      return targets;
    }

    @Override
    public String getName() {
      return "broadcaster";
    }

    @Override
    public boolean canPulse() {
      return true;
    }
  }

  private static class Button implements Module {

    @Override
    public void receiveInput(boolean pulse, String sender) {
      throw new NotImplementedException("Buttons can not receive input.");

    }

    @Override
    public void outputSignal(Map<String, Module> modules) {
      getTargets().forEach(target -> {
        modules.get(target).receiveInput(false, getName());
        pulses.put(false, pulses.get(false) + 1);
      });
    }

    @Override
    public List<String> getTargets() {
      return List.of("broadcaster");
    }

    @Override
    public String getName() {
      return "button";
    }

    @Override
    public boolean canPulse() {
      return true;
    }
  }
}
