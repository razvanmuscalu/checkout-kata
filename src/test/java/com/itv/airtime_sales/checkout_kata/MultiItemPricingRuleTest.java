package com.itv.airtime_sales.checkout_kata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class MultiItemPricingRuleTest {

    public MultiItemPricingRule sut = new MultiItemPricingRule("A", 130L, 3);

    @Parameterized.Parameters(name = "ITEM {0} | BEFORE: {1} price, {2} remainder | AFTER: {3} price, {4} remainder")
    public static Iterable<Object[]> data() {
        return asList(new Object[][]{
                {"A", 0L, 2, 0L, 2},
                {"A", 0L, 3, 130L, 0},
                {"A", 0L, 4, 130L, 1},
                {"A", 0L, 6, 260L, 0},
                {"B", 0L, 2, 0L, 2},
                {"B", 0L, 3, 0L, 3},
        });
    }

    private final String item;
    private final long beforePrice;
    private final int beforeRemainder;
    private final long afterPrice;
    private final int afterReminder;

    public MultiItemPricingRuleTest(String item,
                                    long beforePrice,
                                    int beforeRemainder,
                                    long afterPrice,
                                    int afterReminder) {
        this.item = item;
        this.beforePrice = beforePrice;
        this.beforeRemainder = beforeRemainder;
        this.afterPrice = afterPrice;
        this.afterReminder = afterReminder;
    }

    @Test
    public void shouldApplyRuleCorrectly() {
        Unit result = sut.apply(item, new Unit(beforePrice, beforeRemainder));

        assertThat("should produce correct price", result.getPrice(), is(afterPrice));
        assertThat("should produce correct remainder", result.getRemainder(), is(afterReminder));
    }

}
