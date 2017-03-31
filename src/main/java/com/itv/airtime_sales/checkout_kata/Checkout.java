package com.itv.airtime_sales.checkout_kata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Checkout {

    private static Map<String, Long> prices;

    static {
        prices = new HashMap<>();
        prices.put("A", 50L);
    }

    public Long getPrice(List<String> items) {

        Map<String, Long> itemsByCount = items
                .stream()
                .filter(Objects::nonNull)
                .collect(groupingBy(identity(), counting()));

        return itemsByCount.entrySet()
                .stream()
                .map(entry -> {
                    String item = entry.getKey();
                    Long amount = entry.getValue();

                    return amount * prices.getOrDefault(item, 0L);
                })
                .mapToLong(Long::longValue)
                .sum();

    }
}
