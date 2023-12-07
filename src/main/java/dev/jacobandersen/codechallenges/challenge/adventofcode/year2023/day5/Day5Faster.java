package dev.jacobandersen.codechallenges.challenge.adventofcode.year2023.day5;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.CombinatoricsUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day5Faster extends Day {
    public Day5Faster() {
        super(2023, 5, "If You Give A Seed A Fertilizer (Faster)");
    }

    private List<List<String>> getComponents() {
        List<List<String>> components = new ArrayList<>();
        List<String> currentComponent = new ArrayList<>();
        getInputLines().forEach(line -> {
            if (line.isBlank()) {
                if (!currentComponent.isEmpty()) {
                    components.add(new ArrayList<>(currentComponent));
                    currentComponent.clear();
                }
            } else {
                currentComponent.add(line);
            }
        });
        return components;
    }

    private List<Long> getSeeds(List<List<String>> components) {
        return components.get(0)
                .stream()
                .limit(1)
                .flatMap(line -> Arrays.stream(line.split(": ")[1].trim().split(" ")))
                .map(Long::parseLong).toList();
    }

    private List<Map> getMaps(List<List<String>> components) {
        return components.stream()
                .skip(1)
                .map(Map::create)
                .toList();
    }

    @Override
    public String partOne() {
        final List<List<String>> components = getComponents();
        final List<Long> seeds = getSeeds(components);
        final List<Map> maps = getMaps(components);

        return String.valueOf(seeds.stream().map(seed -> {
            long value = seed;

            for (Map map : maps) {
                value = map.get(value);
            }

            return value;
        }).min(Long::compareTo).orElseThrow());
    }

    @Override
    public String partTwo() {
        final List<List<String>> components = getComponents();
        final List<List<Long>> seedRanges = CombinatoricsUtil.partitionList(getSeeds(components), 2).stream().map(range -> List.of(range.get(0), range.get(0) + range.get(1) - 1)).toList();
        final List<Map> maps = getMaps(components);

        seedRanges.stream().forEach(range -> {
            System.out.printf("original range: %s%n", range);

            List<List<List<Long>>> mappedRanges = List.of(List.of(range));

            for (Map map : maps) {
                System.out.println();
                System.out.printf("map: %s%n", map.name());
                System.out.printf("before: %s%n", mappedRanges);
                mappedRanges = mappedRanges.stream().map(mappedRange -> mappedRange.stream().map(rng -> map.entries().stream().flatMap(entry -> CombinatoricsUtil.splitRange(rng, List.of(entry.sourceStart(), entry.sourceEnd())).stream()).filter(list -> !list.isEmpty()))).toList();
                System.out.printf("after: %s%n", mappedRanges);
                System.out.println();
        }});

        return null;
    }
}
