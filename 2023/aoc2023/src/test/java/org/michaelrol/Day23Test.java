package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day23Test {

  @Test
  void testPart1() {
    Day23 day23 = new Day23("test-input/test23-1.txt");
    assertThat(day23.Part1()).isEqualTo(94);
  }

  @Test
  void testPart2() {
    Day23 day23 = new Day23("test-input/test23-1.txt");
    assertThat(day23.Part2()).isEqualTo(154);
  }

}
