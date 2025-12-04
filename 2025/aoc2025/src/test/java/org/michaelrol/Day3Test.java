
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day3Test {

  @Test
  void testPart1() {
    Day day = new Day3("test-input/test3-1.txt");
    assertThat(day.Part1()).isEqualTo(357);
  }

  @Test
  void testPart2() {
    Day day = new Day3("test-input/test3-1.txt");
    assertThat(day.Part2()).isEqualTo(3121910778619L);
  }
}
