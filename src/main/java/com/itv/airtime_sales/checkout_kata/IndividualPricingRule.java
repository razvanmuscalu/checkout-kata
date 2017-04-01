package com.itv.airtime_sales.checkout_kata;

public final class IndividualPricingRule implements PricingRuleChain {

    private final String item;
    private final long price;
    private PricingRuleChain nextPricingRule;

    public IndividualPricingRule(final String item, final long price) {
        this.item = item;
        this.price = price;
    }

    @Override
    public void nextRule(PricingRuleChain nextRule) {
        this.nextPricingRule = nextRule;
    }

    @Override
    public Unit apply(String item, Unit unit) {
        Unit result = applyRuleAndGetUpdatedUnit(unit);

        if (item.equals(this.item))
            return result;

        return nextPricingRule.apply(item, unit);
    }

    private Unit applyRuleAndGetUpdatedUnit(Unit unit) {
        long itemPrice = unit.getPrice() + (unit.getRemainder() * price);
        int remainder = 0;

        return new Unit(itemPrice, remainder);
    }
}
