/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day15Test {

  @Test
  void testPart1() {
    Day15 day15 = new Day15("test-input/test15-1.txt");
    assertThat(day15.Part1()).isEqualTo(1320);
  }

  @Test
  void testPart2() {
    Day15 day15 = new Day15("test-input/test15-1.txt");
    assertThat(day15.Part2()).isEqualTo(145);
  }

}
