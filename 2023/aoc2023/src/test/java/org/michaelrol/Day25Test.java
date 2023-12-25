package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day25Test {

  @Test
  void testPart1() {
    Day25 day25 = new Day25("test-input/test25-1.txt");
    assertThat(day25.runPart1("frs")).isEqualTo(54);
  }

  @Test
  void testPart2() {
    Day25 day25 = new Day25("test-input/test25-1.txt");
    assertThat(day25.Part2()).isEqualTo(0);
  }

}
