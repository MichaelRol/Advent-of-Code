
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day17Test {

  @Test
  void testPart1() {
    Day17 day17 = new Day17("test-input/test17-1.txt");
    assertThat(day17.Part1()).isEqualTo(102);
  }

  @Test
  void testPart2() {
    Day17 day17 = new Day17("test-input/test17-1.txt");
    assertThat(day17.Part2()).isEqualTo(94);
  }

}
