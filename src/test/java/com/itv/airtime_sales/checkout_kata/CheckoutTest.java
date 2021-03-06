package com.itv.airtime_sales.checkout_kata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;

import java.util.List;

import static com.itv.airtime_sales.checkout_kata.RewardsFunction.multiplication;
import static com.itv.airtime_sales.checkout_kata.PricingRuleChainBuilder.newChain;
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

    @Mock
    private RewardsProvider rewardsProvider;

    private Checkout sut;

    @Parameterized.Parameters(name = "{1} items cost {2}")
    public static Iterable<Object[]> data() {

        PricingRuleChain multiAIndividualA = newChain()
                .first(new MultiItemPricingRule("A", 130L, 3))
                .next(new IndividualPricingRule("A", 50L))
                .build();

        PricingRuleChain individualAindividualB = newChain()
                .first(new IndividualPricingRule("A", 50L))
                .next(new IndividualPricingRule("B", 30L))
                .build();

        PricingRuleChain multiAmultiB = newChain()
                .first(new MultiItemPricingRule("A", 130L, 3))
                .next(new MultiItemPricingRule("B", 45L, 2))
                .build();

        PricingRuleChain multiAmultiBIndividualAindividualB = newChain()
                .first(new MultiItemPricingRule("A", 130L, 3))
                .next(new MultiItemPricingRule("B", 45L, 2))
                .next(new IndividualPricingRule("A", 50L))
                .next(new IndividualPricingRule("B", 30L))
                .build();

        return asList(new Object[][]{
                {multiAIndividualA, singletonList(null), 0L},
                {multiAIndividualA, singletonList(""), 0L},
                {multiAIndividualA, singletonList("C"), 0L},
                {multiAIndividualA, singletonList("A"), 50L},
                {multiAIndividualA, asList("A", "A"), 100L},
                {multiAIndividualA, asList("A", "A", "A"), 130L},
                {multiAIndividualA, asList("A", "A", "A", "A"), 180L},
                {individualAindividualB, singletonList(null), 0L},
                {individualAindividualB, singletonList(""), 0L},
                {individualAindividualB, singletonList("C"), 0L},
                {individualAindividualB, singletonList("A"), 50L},
                {individualAindividualB, singletonList("B"), 30L},
                {individualAindividualB, asList("A", "B"), 80L},
                {multiAmultiBIndividualAindividualB, singletonList(null), 0L},
                {multiAmultiBIndividualAindividualB, singletonList(""), 0L},
                {multiAmultiBIndividualAindividualB, singletonList("C"), 0L},
                {multiAmultiBIndividualAindividualB, asList("A", "B", "A", "B", "A"), 175L},
                {multiAmultiBIndividualAindividualB, asList("A", "B", "A", "B", "A", "A", "B"), 255L},
                {multiAmultiB, singletonList(null), 0L},
                {multiAmultiB, singletonList(""), 0L},
                {multiAmultiB, singletonList("C"), 0L},
                {multiAmultiB, asList("A", "B"), 0L},
                {multiAmultiB, asList("A", "B", "A", "B", "A"), 175L}
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
        sut = new Checkout(ruleProvider, rewardsProvider);
    }

    @Test
    public void shouldCheckoutCorrectly() {
        when(ruleProvider.getRuleChain()).thenReturn(pricingRuleChain);

        assertThat("should return correct price using given rules", sut.getPrice(items), is(price));
    }

    @Test
    public void shouldProduceReceiptCorrectly() {
        when(ruleProvider.getRuleChain()).thenReturn(pricingRuleChain);
        when(rewardsProvider.getRewardsFunction()).thenReturn(multiplication(2L));

        assertThat("should return correct points", sut.getReceipt(items).getPoints(), is(price * 2));
        assertThat("should return correct price", sut.getReceipt(items).getPrice(), is(price));
    }
}
