package com.wanted.preonboarding.service.discount;

import java.math.BigDecimal;

public interface DiscountCalculator {
    BigDecimal calculateDiscount(BigDecimal price);
}
