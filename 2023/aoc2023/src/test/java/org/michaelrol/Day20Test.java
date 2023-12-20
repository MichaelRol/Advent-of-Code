/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day20Test {

  @Test
  void testPart1() {
    Day20 day20 = new Day20("test-input/test20-1.txt");
    assertThat(day20.Part1()).isEqualTo(11687500);
  }
}
