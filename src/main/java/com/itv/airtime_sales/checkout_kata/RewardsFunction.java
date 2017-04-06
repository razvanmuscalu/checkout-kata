package com.itv.airtime_sales.checkout_kata;

@FunctionalInterface
public interface RewardsFunction {

    Long apply(Long multiplicand);

    static RewardsFunction multiplication(Long multiplier) {
        return (multiplicand) -> multiplier * multiplicand;
    }
}