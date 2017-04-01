package com.itv.airtime_sales.checkout_kata;


public interface PricingRuleChain {

    void nextRule(PricingRuleChain nextRule);

    Unit apply(String item, Unit unit);
}