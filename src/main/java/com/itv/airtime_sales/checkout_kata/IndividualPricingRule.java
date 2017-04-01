package com.itv.airtime_sales.checkout_kata;

public final class IndividualPricingRule implements PricingRuleChain {

    private final String item;
    private final long price;
    private PricingRuleChain pricingRule;

    public IndividualPricingRule(final String item, final long price) {
        this.item = item;
        this.price = price;
    }

    @Override
    public void nextRule(PricingRuleChain nextRule) {
        this.pricingRule = nextRule;
    }

    @Override
    public Unit apply(String item, Unit unit) {
        if (item.equals(this.item))
            return new Unit(unit.getPrice() + (unit.getRemainder() * price), unit.getRemainder() - 1);

        return unit;
    }
}
