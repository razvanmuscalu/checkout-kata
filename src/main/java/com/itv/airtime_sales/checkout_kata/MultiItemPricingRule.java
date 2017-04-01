package com.itv.airtime_sales.checkout_kata;

public final class MultiItemPricingRule implements PricingRuleChain {

    private final String item;
    private final long price;
    private final int special;
    private PricingRuleChain pricingRule;

    public MultiItemPricingRule(final String item, final long price, final int special) {
        this.item = item;
        this.price = price;
        this.special = special;
    }

    @Override
    public void nextRule(PricingRuleChain nextRule) {
        this.pricingRule = nextRule;
    }

    @Override
    public Unit apply(String item, Unit unit) {
        if (item.equals(this.item))
            if (hasEnoughAmountForOffer(unit))
                return pricingRule.apply(item, applyRuleAndGetUpdatedUnit(unit));
            else
                return pricingRule.apply(item, unit);

        return unit;
    }

    private boolean hasEnoughAmountForOffer(Unit unit) {
        return unit.getRemainder() / special > 0;
    }

    private Unit applyRuleAndGetUpdatedUnit(Unit unit) {
        long multiItemPrice = this.price * (unit.getRemainder() / special);
        long remainder = unit.getRemainder() - special;

        return new Unit(multiItemPrice, remainder);
    }
}