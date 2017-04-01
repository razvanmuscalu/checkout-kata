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
    public Long apply(String item, Long amount) {
        if (item.equals(this.item))
            if (amount / special > 0)
                return price * (amount / special);
            else
                return pricingRule.apply(item, amount);

        return 0L;
    }
}