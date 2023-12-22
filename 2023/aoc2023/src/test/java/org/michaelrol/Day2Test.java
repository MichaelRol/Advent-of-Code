
package org.michaelrol;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day2Test {

    @Test
    void testPart1() {
        Day2 day2 = new Day2("test-input/test2-1.txt");
        assertThat(day2.Part1()).isEqualTo(8);
    }

    @Test
    void testPart2() {
        Day2 day2 = new Day2("test-input/test2-1.txt");
        assertThat(day2.Part2()).isEqualTo(2286);
    }
}
