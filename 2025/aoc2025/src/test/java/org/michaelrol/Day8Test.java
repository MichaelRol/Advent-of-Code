
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day8Test {

  @Test
  void testPart1() {
    Day day = new Day8("test-input/test8-1.txt", true);
    assertThat(day.Part1()).isEqualTo(40);
  }

  @Test
  void testPart2() {
    Day day = new Day8("test-input/test8-1.txt");
    assertThat(day.Part2()).isEqualTo(25272);
  }
}
