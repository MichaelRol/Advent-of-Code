
package org.michaelrol;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day9Test {

  @Test
  void testPart1() {
    Day9 day9 = new Day9("test-input/test9-1.txt");
    assertThat(day9.Part1()).isEqualTo(114);
  }

  @Test
  void testPart2() {
    Day9 day9 = new Day9("test-input/test9-1.txt");
    assertThat(day9.Part2()).isEqualTo(2);
  }

}
