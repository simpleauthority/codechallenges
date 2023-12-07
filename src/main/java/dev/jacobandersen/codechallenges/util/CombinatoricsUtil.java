package dev.jacobandersen.codechallenges.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CombinatoricsUtil {
    public static <T> List<List<T>> generateSublists(List<T> list, int numElements) {
        if (numElements <= 0 || numElements > list.size()) {
            throw new IllegalArgumentException(String.format("Cannot generate sublists of size <= 0 or size >= list size (%d)", list.size()));
        }

        List<List<T>> result = new ArrayList<>();
        generateSublists(list, numElements, 0, new ArrayList<>(), result);
        return result;
    }

    private static <T> void generateSublists(List<T> list, int numElements, int idx, List<T> current, List<List<T>> result) {
        if (numElements == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = idx; i < list.size(); i++) {
            current.add(list.get(i));
            generateSublists(list, numElements - 1, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    public static <T> Collection<List<T>> partitionList(List<T> list, int numPerPartition) {
        AtomicInteger numPartitions = new AtomicInteger(0);
        return list.stream().collect(Collectors.groupingBy(item -> numPartitions.getAndIncrement() / numPerPartition)).values();
    }

    public static List<List<Long>> splitRange(List<Long> originalRange, List<Long> subRange) {
        if (subRange.get(0) < originalRange.get(0) && subRange.get(1) > originalRange.get(1)) {
            return List.of(originalRange);
        } else if (subRange.get(0).equals(originalRange.get(0)) && subRange.get(1) < originalRange.get(1)) {
            return List.of(subRange, List.of(subRange.get(0) + 1, originalRange.get(1)));
        } else if (subRange.get(0) > originalRange.get(0) && subRange.get(1) < originalRange.get(1)) {
            return List.of(List.of(originalRange.get(0), subRange.get(0) - 1), subRange, List.of(subRange.get(1) + 1, originalRange.get(1)));
        } else if (subRange.get(0) > originalRange.get(0) && subRange.get(1).equals(originalRange.get(1))) {
            return List.of(List.of(originalRange.get(0), subRange.get(1) - 1), subRange);
        }
        return Collections.emptyList();
    }
}
