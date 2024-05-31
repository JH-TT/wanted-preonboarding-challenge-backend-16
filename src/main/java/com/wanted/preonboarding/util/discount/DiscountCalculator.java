package com.wanted.preonboarding.util.discount;

import java.math.BigDecimal;

public interface DiscountCalculator {
    BigDecimal calculateDiscount(BigDecimal price);
}
