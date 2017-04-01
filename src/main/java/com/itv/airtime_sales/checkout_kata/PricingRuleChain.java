package com.itv.airtime_sales.checkout_kata;


public interface PricingRuleChain {

    void nextRule(PricingRuleChain nextRule);

    Long apply(String item, Long amount);
}