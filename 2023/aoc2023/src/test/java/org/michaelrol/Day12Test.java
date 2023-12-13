/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day12Test {

  @Test
  void testPart1() {
    Day12 day12 = new Day12("test-input/test12-1.txt");
    assertThat(day12.Part1()).isEqualTo(21);
  }

  @Test
  void testPart2() {
    Day12 day12 = new Day12("test-input/test12-1.txt");
    assertThat(day12.Part2()).isEqualTo(525152);
  }

}
