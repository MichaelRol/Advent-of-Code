/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day14Test {

  @Test
  void testPart1() {
    Day14 day14 = new Day14("test-input/test14-1.txt");
    assertThat(day14.Part1()).isEqualTo(136);
  }

  @Test
  void testPart2() {
    Day14 day14 = new Day14("test-input/test14-1.txt");
    assertThat(day14.Part2()).isEqualTo(64);
  }

}
