package org.michaelrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day4Test {

    @Test
    void testPart1() {
        Day4 day4 = new Day4("test-input/test4-1.txt");
        assertThat(day4.Part1()).isEqualTo(13);
    }

    @Test
    void testPart2() {
        Day4 day4 = new Day4("test-input/test4-1.txt");
        assertThat(day4.Part2()).isEqualTo(30);
    }
}
