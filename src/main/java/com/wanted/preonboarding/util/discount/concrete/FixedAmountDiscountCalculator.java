package com.wanted.preonboarding.util.discount.concrete;

import com.wanted.preonboarding.util.discount.DiscountCalculator;
import java.math.BigDecimal;

public class FixedAmountDiscountCalculator implements DiscountCalculator {
    private final BigDecimal discountAmount;

    public FixedAmountDiscountCalculator(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal price) {
        return discountAmount;
    }
}
