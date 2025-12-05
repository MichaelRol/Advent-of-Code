
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day5Test {

  @Test
  void testPart1() {
    Day day = new Day5("test-input/test5-1.txt");
    assertThat(day.Part1()).isEqualTo(3);
  }

  @Test
  void testPart2() {
    Day day = new Day5("test-input/test5-1.txt");
    assertThat(day.Part2()).isEqualTo(14);
  }
}
