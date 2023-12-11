/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day10Test {

  @Test
  void testPart1() {
    Day10 day10 = new Day10("test-input/test10-1.txt");
    assertThat(day10.Part1()).isEqualTo(8);
  }

  @Test
  void testPart2() {
    Day10 day10 = new Day10("test-input/test10-2.txt");
    assertThat(day10.Part2()).isEqualTo(10);
  }

}
