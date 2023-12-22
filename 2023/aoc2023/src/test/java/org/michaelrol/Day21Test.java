package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day21Test {

  @Test
  void testPart1() {
    Day21 day21 = new Day21("test-input/test21-1.txt");
    assertThat(day21.Part1()).isEqualTo(42);
  }
}
