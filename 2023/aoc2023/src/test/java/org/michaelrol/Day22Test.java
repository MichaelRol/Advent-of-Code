package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day22Test {

  @Test
  void testPart1() {
    Day22 day22 = new Day22("test-input/test22-1.txt");
    assertThat(day22.Part1()).isEqualTo(5);
  }

  @Test
  void testPart2() {
    Day22 day22 = new Day22("test-input/test22-1.txt");
    assertThat(day22.Part2()).isEqualTo(7);
  }

}
