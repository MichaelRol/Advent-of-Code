
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day13Test {

  @Test
  void testPart1() {
    Day13 day13 = new Day13("test-input/test13-1.txt");
    assertThat(day13.Part1()).isEqualTo(405);
  }

  @Test
  void testPart2() {
    Day13 day13 = new Day13("test-input/test13-1.txt");
    assertThat(day13.Part2()).isEqualTo(400);
  }

}
