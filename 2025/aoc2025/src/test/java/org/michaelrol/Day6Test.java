
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day6Test {

  @Test
  void testPart1() {
    Day day = new Day6("test-input/test6-1.txt");
    assertThat(day.Part1()).isEqualTo(4277556);
  }

  @Test
  void testPart2() {
    Day day = new Day6("test-input/test6-1.txt");
    assertThat(day.Part2()).isEqualTo(3263827);
  }
}
