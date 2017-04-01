package com.itv.airtime_sales.checkout_kata;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class Checkout {

    private final RuleProvider ruleProvider;

    public Checkout(RuleProvider ruleProvider) {
        this.ruleProvider = ruleProvider;
    }

    public Long getPrice(List<String> items) {
        PricingRuleChain ruleChain = ruleProvider.getRuleChain();

        Map<String, Integer> itemsByCount = items
                .stream()
                .filter(Objects::nonNull)
                .collect(groupingBy(x -> x, summingInt(x -> 1)));

        return itemsByCount.entrySet()
                .stream()
                .map(entry -> ruleChain.apply(entry.getKey(), new Unit(0L, entry.getValue())))
                .mapToLong(Unit::getPrice)
                .sum();

    }
}