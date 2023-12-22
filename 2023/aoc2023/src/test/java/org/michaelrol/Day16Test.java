
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day16Test {

  @Test
  void testPart1() {
    Day16 day16 = new Day16("test-input/test16-1.txt");
    assertThat(day16.Part1()).isEqualTo(46);
  }

  @Test
  void testPart2() {
    Day16 day16 = new Day16("test-input/test16-1.txt");
    assertThat(day16.Part2()).isEqualTo(51);
  }

}
