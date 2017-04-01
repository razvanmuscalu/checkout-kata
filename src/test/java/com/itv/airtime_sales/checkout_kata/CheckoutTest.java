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

    @Parameterized.Parameters(name = "{0} items cost {1}")
    public static Iterable<Object[]> data() {
        return asList(new Object[][]{
                {singletonList(null), 0L},
                {singletonList(""), 0L},
                {singletonList("A"), 50L},
                {asList("A", "A"), 100L},
                {asList("A", "A", "A"), 130L},
                {asList("A", "A", "A", "A"), 180L},

        });
    }

    private final List<String> items;
    private final Long price;

    public CheckoutTest(List<String> items, Long price) {
        this.items = items;
        this.price = price;
    }

    @Before
    public void setUp() {
        initMocks(this);
        sut = new Checkout(ruleProvider);
    }

    @Test
    public void shouldCheckoutCorrectlyWithTwoRules() {
        MultiItemPricingRule rule1 = new MultiItemPricingRule("A", 130L, 3);
        IndividualPricingRule rule2 = new IndividualPricingRule("A", 50L);
        rule1.nextRule(rule2);

        when(ruleProvider.getRuleChain()).thenReturn(rule1);

        assertThat("should return correct price using two rules", sut.getPrice(items), is(price));
    }
}
