package com.itv.airtime_sales.checkout_kata;

public final class IndividualPricingRule implements PricingRuleChain {

    private final String item;
    private final long price;
    private PricingRuleChain pricingRule;

    public IndividualPricingRule(final String item, final long price) {
        super();
        this.item = item;
        this.price = price;
    }

    @Override
    public void nextRule(PricingRuleChain nextRule) {
        this.pricingRule = nextRule;
    }

    @Override
    public Long apply(String item, Long amount) {
        if (item.equals(this.item))
            return amount * price;

        return 0L;
    }
}
