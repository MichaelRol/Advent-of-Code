package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public class Day25 implements Day {

  private final SetMultimap<String, String> graph = HashMultimap.create();

  public Day25(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(": ");
        String root = split[0];
        String[] targets = split[1].split(" ");
        for (String target : targets) {
          graph.put(root, target);
          graph.put(target, root);
        }
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    return runPart1("bpz");
  }

  public long runPart1(String start) {
    Set<String> allNodes = new HashSet<>();
    traverseGraph(start, allNodes);

    Set<String> cluster = new HashSet<>();
    cluster.add(start);
    Set<String> links = graph.get(start);
    cluster.addAll(links);
    growCluster(cluster);
    return (long) cluster.size() * (allNodes.size() - cluster.size());
  }

  private void growCluster(Set<String> cluster) {
    List<String> outLinks = findOutLinks(cluster);
    if (new HashSet<>(outLinks).size() == 3) {
      return;
    }
    for (String outLink : outLinks) {
      if (cluster.contains(outLink)) {
        continue; //TODO: not sure
      }

      //auto cluster2 = cluster;
      cluster.add(outLink);

      // Add any other nodes which have more than one link to the
      // cluster. Repeat until the cluster stops growing.
      int size = 0;
      while (size < cluster.size()) {
        size = cluster.size();

        for (String entry : graph.keySet()) {
          // Already in the cluster.
          if (cluster.contains(entry)) {
            continue;
          }

          // If there is only one link to the existing cluster, this
          // node is likely part of another cluster.
          int links = 0;
          for (String dest : graph.get(entry)) {
            if (cluster.contains(dest)) {
              ++links;
            }
          }
          if (links > 1) {
            cluster.add(entry);
          }
        }
      }
      growCluster(cluster);
    }
  }

  private List<String> findOutLinks(Set<String> cluster) {
    List<String> outLinks = new ArrayList<>();
    for (String s : cluster) {
      Set<String> strings = graph.get(s);
      for (String link : strings) {
        if (!cluster.contains(link)) {
          outLinks.add(link);
        }
      }
    }
    return outLinks;
  }

  @Override
  public long Part2() {
    return 0;
  }

  private void traverseGraph(String curr, Set<String> seen) {
    if (seen.contains(curr)) {
      return;
    }
    seen.add(curr);
    Set<String> nextNodes = graph.get(curr);
    for (String nextNode : nextNodes) {
      traverseGraph(nextNode, seen);
    }
  }
}
