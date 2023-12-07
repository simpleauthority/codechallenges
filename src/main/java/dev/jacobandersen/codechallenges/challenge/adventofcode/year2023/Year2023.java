package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day1.Day1;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day2.Day2;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day3.Day3;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day4.Day4;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day5.Day5Faster;
import dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day6.Day6;

import java.util.List;

public class Year2023 {
    public static List<Day> getDays() {
        return List.of(
                new Day1(),
                new Day2(),
                new Day3(),
                new Day4(),
                new Day5Faster(),
                new Day6()
        );
    }
}
