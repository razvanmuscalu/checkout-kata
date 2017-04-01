package com.itv.airtime_sales.checkout_kata;

import java.util.LinkedList;

public class PricingRuleChainBuilder {

    private final LinkedList<PricingRuleChain> rules;

    public PricingRuleChainBuilder() {
        this.rules = new LinkedList<>();
    }

    public PricingRuleChainBuilder first(PricingRuleChain rule) {
        this.rules.add(rule);
        return this;
    }

    public PricingRuleChainBuilder next(PricingRuleChain rule) {
        this.rules.getLast().nextRule(rule);
        this.rules.add(rule);
        return this;
    }

    public PricingRuleChain build() {
        return rules.getFirst();
    }

    public static PricingRuleChainBuilder newChain() {
        return new PricingRuleChainBuilder();
    }
}
