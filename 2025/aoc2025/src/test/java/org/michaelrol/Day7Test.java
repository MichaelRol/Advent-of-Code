
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day7Test {

  @Test
  void testPart1() {
    Day day = new Day7("test-input/test7-1.txt");
    assertThat(day.Part1()).isEqualTo(21);
  }

  @Test
  void testPart2() {
    Day day = new Day7("test-input/test7-1.txt");
    assertThat(day.Part2()).isEqualTo(40);
  }
}
