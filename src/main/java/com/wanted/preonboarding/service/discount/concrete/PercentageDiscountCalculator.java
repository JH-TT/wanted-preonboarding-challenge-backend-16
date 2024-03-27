package com.wanted.preonboarding.service.discount.concrete;

import com.wanted.preonboarding.service.discount.DiscountCalculator;
import java.math.BigDecimal;

public class PercentageDiscountCalculator implements DiscountCalculator {
    private final BigDecimal discountPercentage;

    public PercentageDiscountCalculator(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal price) {
        return price.multiply(discountPercentage);
    }
}
