/*
 * Copyright (C) 3033 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package org.michaelrol;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day3Test {

    @Test
    void testPart1() {
        Day3 day3 = new Day3("test-input/test3-1.txt");
        assertThat(day3.Part1()).isEqualTo(4361);
    }

    @Test
    void testPart2() {
        Day3 day3 = new Day3("test-input/test3-1.txt");
        assertThat(day3.Part2()).isEqualTo(3386);
    }
}
