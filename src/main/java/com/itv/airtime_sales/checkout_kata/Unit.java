package com.itv.airtime_sales.checkout_kata;

public class Unit {

    private final long price;
    private final long remainder;

    public Unit(final long price, final long remainder) {
        this.price = price;
        this.remainder = remainder;
    }

    public long getPrice() {
        return price;
    }

    public long getRemainder() {
        return remainder;
    }

}
