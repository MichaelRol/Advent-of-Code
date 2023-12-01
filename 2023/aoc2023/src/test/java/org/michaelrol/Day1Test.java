/*
 * Copyright (C) 2023 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Day1Test {

  @Test
  void testPart1() {
    Day1 day1 = new Day1("test-input/test1-1.txt");
    assertThat(day1.Part1()).isEqualTo(142);
  }

  @Test
  void testPart2() {
    Day1 day1 = new Day1("test-input/test1-2.txt");
    assertThat(day1.Part2()).isEqualTo(281);
  }
}
