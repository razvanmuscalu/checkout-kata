package com.itv.airtime_sales.checkout_kata;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Checkout {

    public Long getPrice(List<String> items) {

        MultiItemPricingRule rule1 = new MultiItemPricingRule("A", 130L, 3);
        IndividualPricingRule rule2 = new IndividualPricingRule("A", 50L);
        rule1.nextRule(rule2);

        Map<String, Long> itemsByCount = items
                .stream()
                .filter(Objects::nonNull)
                .collect(groupingBy(identity(), counting()));

        return itemsByCount.entrySet()
                .stream()
                .mapToLong(entry -> rule1.apply(entry.getKey(), entry.getValue()))
                .sum();

    }
}