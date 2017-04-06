package com.itv.airtime_sales.checkout_kata;

public class Receipt {

    private final Long price;
    private final Long points;

    public Receipt(Long price, Long points) {
        this.price = price;
        this.points = points;
    }

    public Long getPrice() {
        return price;
    }

    public Long getPoints() {
        return points;
    }
}
