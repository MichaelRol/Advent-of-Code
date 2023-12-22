
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day5Test {

  @Test
  void testPart1() {
    Day5 day5 = new Day5("test-input/test5-1.txt");
    assertThat(day5.Part1()).isEqualTo(35);
  }

  @Test
  void testPart2() {
    Day5 day5 = new Day5("test-input/test5-1.txt");
    assertThat(day5.Part2()).isEqualTo(46);
  }

}
