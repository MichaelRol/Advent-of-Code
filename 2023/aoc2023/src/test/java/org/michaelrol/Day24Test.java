package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day24Test {

  @Test
  void testPart1() {
    Day24 day24 = new Day24("test-input/test24-1.txt");
    assertThat(day24.runPart1(7, 27)).isEqualTo(2);
  }

  @Test
  void testPart2() {
    Day24 day24 = new Day24("test-input/test24-1.txt");
    assertThat(day24.Part2()).isEqualTo(47);
  }

}
