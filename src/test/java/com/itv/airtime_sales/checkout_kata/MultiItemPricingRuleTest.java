package com.itv.airtime_sales.checkout_kata;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(Enclosed.class)
public class MultiItemPricingRuleTest {

    @RunWith(Parameterized.class)
    public static class ParameterizedTest {

        private MultiItemPricingRule sut = new MultiItemPricingRule("A", 130L, 3);

        @Parameterized.Parameters(name = "ITEM {0} | BEFORE: {1} price, {2} remainder | AFTER: {3} price, {4} remainder")
        public static Iterable<Object[]> data() {
            return asList(new Object[][]{
                    {"A", 0L, 2, 0L, 2},
                    {"A", 0L, 3, 130L, 0},
                    {"A", 0L, 4, 130L, 1},
                    {"A", 0L, 6, 260L, 0},
                    {"B", 0L, 2, 0L, 2},
                    {"B", 0L, 3, 0L, 3},
                    {"A", 25L, 2, 25L, 2},
                    {"A", 25L, 3, 155L, 0},
                    {"A", 25L, 4, 155L, 1},
                    {"A", 25L, 6, 285L, 0},
                    {"B", 25L, 2, 25L, 2},
                    {"B", 25L, 3, 25L, 3},
            });
        }

        private final String item;
        private final long beforePrice;
        private final int beforeRemainder;
        private final long afterPrice;
        private final int afterReminder;

        public ParameterizedTest(String item,
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

    @RunWith(MockitoJUnitRunner.class)
    public static class NonParameterizedTest {

        private MultiItemPricingRule sut = new MultiItemPricingRule("A", 130L, 3);

        @Mock
        private PricingRuleChain nextPricingRule;

        @Before
        public void setUp() {
            sut.nextRule(nextPricingRule);
        }

        @Test
        public void shouldNotCallNextRuleWhenRemainderIsZero() {
            sut.apply("A", new Unit(0L, 3));

            verify(nextPricingRule, times(0)).apply(anyString(), any(Unit.class));
        }

        @Test
        public void shouldCallNextRuleWhenRemainderBiggerThanZero() {
            sut.apply("A", new Unit(0L, 4));

            verify(nextPricingRule, times(1)).apply(anyString(), any(Unit.class));
        }

        @Test
        public void shouldCallNextRuleWhenDoesNotMatchItem() {
            sut.apply("B", new Unit(0L, 2));

            verify(nextPricingRule, times(1)).apply(anyString(), any(Unit.class));
        }
    }

}
