package com.itv.airtime_sales.checkout_kata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class CheckoutTest {

    private final Checkout sut = new Checkout();

    @Parameterized.Parameters(name = "{0} items cost {1}")
    public static Iterable<Object[]> data() {
        return asList(new Object[][]{
                {singletonList(null), 0L},
                {singletonList(""), 0L},
                {singletonList("A"), 50L},
                {asList("A", "A"), 100L},

        });
    }

    private final List<String> items;
    private final Long price;

    public CheckoutTest(List<String> items, Long price) {
        this.items = items;
        this.price = price;
    }

    @Test
    public void shouldCalculateCorrectly() {
        assertThat("should return correct price", sut.getPrice(items), is(price));
    }
}
