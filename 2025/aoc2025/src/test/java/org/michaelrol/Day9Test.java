
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day9Test {

  @Test
  void testPart1() {
    Day day = new Day9("test-input/test9-1.txt");
    assertThat(day.Part1()).isEqualTo(50);
  }

  @Test
  void testPart2() {
    Day day = new Day9("test-input/test9-1.txt");
    assertThat(day.Part2()).isEqualTo(24);
  }
}
