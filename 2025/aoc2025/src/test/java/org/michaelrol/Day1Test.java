
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day1Test {

  @Test
  void testPart1() {
    Day1 day1 = new Day1("test-input/test1-1.txt");
    assertThat(day1.Part1()).isEqualTo(3);
  }

  @Test
  void testPart2() {
    Day1 day1 = new Day1("test-input/test1-1.txt");
    assertThat(day1.Part2()).isEqualTo(6);
  }
}
