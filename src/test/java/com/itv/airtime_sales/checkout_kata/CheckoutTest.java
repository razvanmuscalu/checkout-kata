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
        MultiItemPricingRule multiAIndividualA = new MultiItemPricingRule("A", 130L, 3);
        IndividualPricingRule individualRuleForA = new IndividualPricingRule("A", 50L);
        multiAIndividualA.nextRule(individualRuleForA);

        IndividualPricingRule individualAindividualB = new IndividualPricingRule("A", 50L);
        IndividualPricingRule individualRuleForB = new IndividualPricingRule("B", 30L);
        individualAindividualB.nextRule(individualRuleForB);

        MultiItemPricingRule multiAmultiBIndividualAindividualB = new MultiItemPricingRule("A", 130L, 3);
        MultiItemPricingRule multiRuleForB = new MultiItemPricingRule("B", 45L, 2);
        multiAmultiBIndividualAindividualB.nextRule(multiRuleForB);
        multiRuleForB.nextRule(individualRuleForA);
        individualRuleForA.nextRule(individualRuleForB);

        return asList(new Object[][]{
                {multiAIndividualA, singletonList(null), 0L},
//                {multiAIndividualA, singletonList(""), 0L},
                {multiAIndividualA, singletonList("A"), 50L},
                {multiAIndividualA, asList("A", "A"), 100L},
                {multiAIndividualA, asList("A", "A", "A"), 130L},
                {multiAIndividualA, asList("A", "A", "A", "A"), 180L},
                {individualAindividualB, asList("A", "B"), 80L},
                {individualAindividualB, singletonList("A"), 50L},
                {individualAindividualB, singletonList("B"), 30L},
                {multiAmultiBIndividualAindividualB, asList("A", "B", "A", "B", "A"), 175L},
                {multiAmultiBIndividualAindividualB, asList("A", "B", "A", "B", "A", "A", "B"), 255L}
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
