package com.wanted.preonboarding.service.discount;

import com.wanted.preonboarding.Enum.DiscountType;
import java.math.BigDecimal;

public class DiscountService {
    private final BigDecimal price;

    public DiscountService(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal applyDiscount(DiscountType discountType) {
        DiscountCalculator discountCalculator = discountType.getDiscountCalculator();
        return discountCalculator.calculateDiscount(price);
    }
}
