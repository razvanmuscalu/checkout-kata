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
        Unit result = applyRuleAndGetUpdatedUnit(unit);

        if (item.equals(this.item))
            if (pricingRule != null)
                return pricingRule.apply(item, result);
            else
                return result;

        return unit;
    }

    private Unit applyRuleAndGetUpdatedUnit(Unit unit) {
        long multiItemPrice = this.price * (unit.getRemainder() / special);
        int remainder = unit.getRemainder() - (unit.getRemainder() / special) * special;

        return new Unit(multiItemPrice, remainder);
    }
}