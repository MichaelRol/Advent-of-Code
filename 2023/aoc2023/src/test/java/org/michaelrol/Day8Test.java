/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day8Test {

  @Test
  void testPart1() {
    Day8 day8 = new Day8("test-input/test8-1.txt");
    assertThat(day8.Part1()).isEqualTo(6);
  }

  @Test
  void testPart2() {
    Day8 day8 = new Day8("test-input/test8-2.txt");
    assertThat(day8.Part2()).isEqualTo(6);
  }

}
