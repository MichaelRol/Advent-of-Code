package org.michaelrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.util.VisibleForTesting;

public class Day24 implements Day {

  private final List<Hail> input = new ArrayList<>();

  public Day24(String inputPath) {
    ClassLoader classLoader = Day1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(inputPath)) {
      // Use BufferedReader to read the content of the file
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      // Read lines and add them to the List
      String line;
      while ((line = reader.readLine()) != null) {
        input.add(Hail.parse(line));
      }
    } catch (IOException ex) {
      System.out.println("File with path: " + inputPath + " could not be read.");
      System.exit(1);
    }
  }

  @Override
  public long Part1() {
    return runPart1(200000000000000L, 400000000000000L);
  }

  @Override
  public long Part2() {
    for (int vx = -500; vx < 500; vx++) {
      for (int vy = -500; vy < 500; vy++) {
        List<Hail> updatedHail = new ArrayList<>();
        for (Hail hail : input) {
          updatedHail.add(hail.adjustVelocity(vx, vy, 0));
        }
        List<Pair<Double, Double>> intersections = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
          for (int j = i + 1; j < 5; j++) {
            Pair<Double, Double> intersection = findIntersection(updatedHail.get(i), updatedHail.get(j));
            if (intersection != null && !intersection.getRight().isNaN() && !intersection.getLeft().isNaN()) {
              intersections.add(intersection);
            }
          }
        }
        boolean allSame = true;
        for (Pair<Double, Double> intersection : intersections) {
          if (Math.abs(intersection.getLeft() - intersections.get(0).getLeft()) > 1d ||
              Math.abs(intersection.getRight() - intersections.get(0).getRight()) > 1d) {

            allSame = false;
            break;
          }
        }
        if (allSame) {
          for (int vz = -500; vz < 500; vz++) {
            List<Hail> updatedHailAgain = new ArrayList<>();
            for (Hail hail : updatedHail) {
              updatedHailAgain.add(hail.adjustVelocity(0, 0, vz));
            }
            List<Pair<Double, Double>> intersectionsAgain = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
              for (int j = i + 1; j < 5; j++) {
                Pair<Double, Double> intersection = findIntersectionZx(
                    updatedHailAgain.get(i),
                    updatedHailAgain.get(j));
                if (intersection != null && !intersection.getRight().isNaN() && !intersection.getLeft().isNaN()) {
                  intersectionsAgain.add(intersection);
                }
              }
            }
            boolean allSameAgain = true;
            for (Pair<Double, Double> intersection : intersectionsAgain) {
              if (Math.abs(intersection.getLeft() - intersectionsAgain.get(0).getLeft()) > 1d ||
                  Math.abs(intersection.getRight() - intersectionsAgain.get(0).getRight()) > 1d) {

                allSameAgain = false;
                break;
              }
            }
            if (allSameAgain) {
              return (long) (intersections.get(0).getLeft() +
                  intersections.get(0).getRight() +
                  intersectionsAgain.get(0).getRight());
            }
          }
        }
      }
    }
    return 0;
  }

  @VisibleForTesting
  public long runPart1(long min, long max) {
    long count = 0;
    for (int i = 0; i < input.size(); i++) {
      for (int j = i + 1; j < input.size(); j++) {
        Pair<Double, Double> intersection = findIntersection(input.get(i), input.get(j));
        if (intersection != null) {
          if (intersection.getLeft() >= min &&
              intersection.getRight() >= min &&
              intersection.getLeft() <= max &&
              intersection.getRight() <= max) {

            if (isInFuture(intersection, input.get(i), input.get(j))) {
              count++;
            }
          }
        }
      }
    }
    return count;
  }

  private Pair<Double, Double> findIntersection(Hail hail1, Hail hail2) {
    double m1 = (double) hail1.vY / hail1.vX;
    double m2 = (double) hail2.vY / hail2.vX;

    if (m1 == m2) {
      return null;
    }

    double x = ((m1 * hail1.x) - hail1.y + hail2.y - (m2 * hail2.x)) / (m1 - m2);
    double y = m1 * (x - hail1.x) + hail1.y;
    return Pair.of(x, y);
  }

  private Pair<Double, Double> findIntersectionZx(Hail hail1, Hail hail2) {
    double m1 = (double) hail1.vZ / hail1.vX;
    double m2 = (double) hail2.vZ / hail2.vX;

    if (m1 == m2) {
      return null;
    }

    double x = ((m1 * hail1.x) - hail1.z + hail2.z - (m2 * hail2.x)) / (m1 - m2);
    double z = m1 * (x - hail1.x) + hail1.z;
    return Pair.of(x, z);
  }

  private boolean isInFuture(Pair<Double, Double> intersection, Hail hail1, Hail hail2) {
    if (hail1.vX < 0 && intersection.getLeft() > hail1.x) {
      return false;
    }
    if (hail1.vX > 0 && intersection.getLeft() < hail1.x) {
      return false;
    }
    if (hail2.vX < 0 && intersection.getLeft() > hail2.x) {
      return false;
    }
    if (hail2.vX > 0 && intersection.getLeft() < hail2.x) {
      return false;
    }
    if (hail1.vY < 0 && intersection.getRight() > hail1.y) {
      return false;
    }
    if (hail1.vY > 0 && intersection.getRight() < hail1.y) {
      return false;
    }
    if (hail2.vY < 0 && intersection.getRight() > hail2.y) {
      return false;
    }
    if (hail2.vY > 0 && intersection.getRight() < hail2.y) {
      return false;
    }
    return true;
  }

  private static class Hail {

    long x;
    long y;
    long z;

    long vX;
    long vY;
    long vZ;

    private static Hail parse(String x) {
      String[] split = x.split(" @ ");
      String[] pos = split[0].split(", ");
      String[] vel = split[1].split(", ");
      return new Hail(
          Long.parseLong(pos[0]),
          Long.parseLong(pos[1]),
          Long.parseLong(pos[2]),
          Long.parseLong(vel[0]),
          Long.parseLong(vel[1]),
          Long.parseLong(vel[2]));
    }

    private Hail(long x, long y, long z, long vX, long vY, long vZ) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.vX = vX;
      this.vY = vY;
      this.vZ = vZ;
    }

    public Hail adjustVelocity(long dx, long dy, long dz) {
      return new Hail(x, y, z, vX - dx, vY - dy, vZ - dz);
    }
  }
}
