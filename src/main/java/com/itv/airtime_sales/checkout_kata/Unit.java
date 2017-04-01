package com.itv.airtime_sales.checkout_kata;

public class Unit {

    private final long price;
    private final int remainder;

    public Unit(final long price, final int remainder) {
        this.price = price;
        this.remainder = remainder;
    }

    public long getPrice() {
        return price;
    }

    public int getRemainder() {
        return remainder;
    }

}
