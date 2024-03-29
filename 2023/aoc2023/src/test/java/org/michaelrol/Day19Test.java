
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day19Test {

  @Test
  void testPart1() {
    Day19 day19 = new Day19("test-input/test19-1.txt");
    assertThat(day19.Part1()).isEqualTo(19114);
  }

  @Test
  void testPart2() {
    Day19 day19 = new Day19("test-input/test19-1.txt");
    assertThat(day19.Part2()).isEqualTo(167409079868000L);
  }

}
