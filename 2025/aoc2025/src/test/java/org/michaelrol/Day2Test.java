
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day2Test {

  @Test
  void testPart1() {
    Day day = new Day2("test-input/test2-1.txt");
    assertThat(day.Part1()).isEqualTo(1227775554);
  }

  @Test
  void testPart2() {
    Day day = new Day2("test-input/test2-1.txt");
    assertThat(day.Part2()).isEqualTo(4174379265L);
  }
}
