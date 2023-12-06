/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day6Test {

  @Test
  void testPart1() {
    Day6 day6 = new Day6("test-input/test6-1.txt");
    assertThat(day6.Part1()).isEqualTo(288);
  }

  @Test
  void testPart2() {
    Day6 day6 = new Day6("test-input/test6-1.txt");
    assertThat(day6.Part2()).isEqualTo(71503);
  }

}
