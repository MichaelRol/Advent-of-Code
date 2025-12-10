package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class Day9 implements Day {

  private final List<Coordinate> input = new ArrayList<>();

  public Day9(String inputPath) {
    ClassLoader classLoader = Day9.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(",");
        input.add(new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    return Lists.cartesianProduct(ImmutableList.of(input, input)).stream()
        .map(corners -> new Box(corners.getFirst(), corners.getLast()).area)
        .max(Comparator.naturalOrder())
        .orElseThrow();
  }


  @Override
  public long Part2() {
    Set<Box> tileBorders = Sets.newHashSet();
    for (int i = 0; i < input.size(); i++) {
      for (int j = i + 1; j < input.size(); j++) {
        tileBorders.add(new Box(input.get(i), input.get(j)));
      }
    }
    Set<Coordinate> edges = getEdges(input);
    List<Box> sorted = tileBorders.stream().sorted(Comparator.comparingLong(Box::area).reversed()).toList();
    for (Box box : sorted) {
      if (enclosed(box, edges)) {
        return box.area();
      }
    }
    return 0;
  }

  private boolean enclosed(Box shape, Set<Coordinate> tileEdges) {
    int minX = Math.min(shape.a().x(), shape.b().x());
    int maxX = Math.max(shape.a().x(), shape.b().x());
    int minxY = Math.min(shape.a().y(), shape.b().y());
    int maxY = Math.max(shape.a().y(), shape.b().y());
    for (Coordinate coordinate : tileEdges) {
      if (coordinate.x() > minX && coordinate.x() < maxX && coordinate.y() > minxY && coordinate.y() < maxY) {
        return false;
      }
    }
    return true;
  }

  private Set<Coordinate> getEdges(List<Coordinate> corners) {
    Set<Coordinate> edges = new HashSet<>();
    for (int i = 0; i < corners.size(); i++) {
      Coordinate a = corners.get(i);
      Coordinate b = corners.get((i + 1) % corners.size());
      if (a.x() == b.x()) {
        for (int j = Math.min(a.y(), b.y()); j <= Math.max(a.y(), b.y()); j++) {
          edges.add(new Coordinate(a.x(), j));
        }
      } else {
        for (int j = Math.min(a.x(), b.x()); j <= Math.max(a.x(), b.x()); j++) {
          edges.add(new Coordinate(j, a.y()));
        }
      }
    }
    return edges;
  }

  private record Box(Coordinate a, Coordinate b, long area) {

    Box(Coordinate a, Coordinate b) {
      this(a, b, (Math.abs(b.x() - a.x()) + 1L) * (Math.abs(b.y() - a.y()) + 1L));
    }
  }

  private record Coordinate(int x, int y) {

  }
}
