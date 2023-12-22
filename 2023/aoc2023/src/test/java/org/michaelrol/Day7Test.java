
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day7Test {

  @Test
  void testPart1() {
    Day7 day7 = new Day7("test-input/test7-1.txt");
    assertThat(day7.Part1()).isEqualTo(6440);
  }

  @Test
  void testPart2() {
    Day7 day7 = new Day7("test-input/test7-1.txt");
    assertThat(day7.Part2()).isEqualTo(5905);
  }

}
