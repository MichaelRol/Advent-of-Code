package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day3Test {

    @Test
    void testPart1() {
        Day3 day3 = new Day3("test-input/test3-1.txt");
        assertThat(day3.Part1()).isEqualTo(4361);
    }

    @Test
    void testPart2() {
        Day3 day3 = new Day3("test-input/test3-1.txt");
        assertThat(day3.Part2()).isEqualTo(467835);
    }
}
