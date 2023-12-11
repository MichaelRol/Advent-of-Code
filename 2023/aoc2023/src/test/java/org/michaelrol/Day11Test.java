/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day11Test {

  @Test
  void testPart1() {
    Day11 day11 = new Day11("test-input/test11-1.txt");
    assertThat(day11.Part1()).isEqualTo(374);
  }

  @Test
  void testPart2() {
    Day11 day11 = new Day11("test-input/test11-1.txt");
    assertThat(day11.Part2()).isEqualTo(82000210);
  }

}
