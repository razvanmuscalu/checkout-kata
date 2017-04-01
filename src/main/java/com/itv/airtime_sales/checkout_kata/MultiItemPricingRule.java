package com.itv.airtime_sales.checkout_kata;

public final class MultiItemPricingRule implements PricingRuleChain {

    private final String item;
    private final long price;
    private final int special;
    private PricingRuleChain nextPricingRule;

    public MultiItemPricingRule(final String item, final long price, final int special) {
        this.item = item;
        this.price = price;
        this.special = special;
    }

    @Override
    public void nextRule(PricingRuleChain nextRule) {
        this.nextPricingRule = nextRule;
    }

    @Override
    public Unit apply(String item, Unit unit) {
        Unit result = applyRuleAndGetUpdatedUnit(unit);

        if (item.equals(this.item))
            if (result.getRemainder() > 0 && nextPricingRule != null)
                return nextPricingRule.apply(item, result);
            else
                return result;

        return nextPricingRule.apply(item, unit);
    }

    private Unit applyRuleAndGetUpdatedUnit(Unit unit) {
        long multiItemPrice = unit.getPrice() + this.price * (unit.getRemainder() / special);
        int remainder = unit.getRemainder() - (unit.getRemainder() / special) * special;

        return new Unit(multiItemPrice, remainder);
    }
}