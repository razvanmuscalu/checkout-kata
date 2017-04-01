package com.itv.airtime_sales.checkout_kata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(Parameterized.class)
public class CheckoutTest {

    @Mock
    private RuleProvider ruleProvider;

    private Checkout sut;

    @Parameterized.Parameters(name = "{1} items cost {2}")
    public static Iterable<Object[]> data() {
        MultiItemPricingRule chainedRuleForA = new MultiItemPricingRule("A", 130L, 3);
        IndividualPricingRule individualRuleForA = new IndividualPricingRule("A", 50L);
        chainedRuleForA.nextRule(individualRuleForA);

        return asList(new Object[][]{
                {chainedRuleForA, singletonList(null), 0L},
                {chainedRuleForA, singletonList(""), 0L},
                {chainedRuleForA, singletonList("A"), 50L},
                {chainedRuleForA, asList("A", "A"), 100L},
                {chainedRuleForA, asList("A", "A", "A"), 130L},
                {chainedRuleForA, asList("A", "A", "A", "A"), 180L}
        });
    }

    private final PricingRuleChain pricingRuleChain;
    private final List<String> items;
    private final Long price;

    public CheckoutTest(PricingRuleChain pricingRuleChain, List<String> items, Long price) {
        this.pricingRuleChain = pricingRuleChain;
        this.items = items;
        this.price = price;
    }

    @Before
    public void setUp() {
        initMocks(this);
        sut = new Checkout(ruleProvider);
    }

    @Test
    public void shouldCheckoutCorrectly() {
        when(ruleProvider.getRuleChain()).thenReturn(pricingRuleChain);

        assertThat("should return correct price using given rules", sut.getPrice(items), is(price));
    }
}
