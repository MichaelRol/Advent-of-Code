/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day18Test {

  @Test
  void testPart1() {
    Day18 day18 = new Day18("test-input/test18-1.txt");
    assertThat(day18.Part1()).isEqualTo(62);
  }

  @Test
  void testPart2() {
    Day18 day18 = new Day18("test-input/test18-1.txt");
    assertThat(day18.Part2()).isEqualTo(952408144115L);
  }

}
