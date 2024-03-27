package com.wanted.preonboarding.Enum;

import com.wanted.preonboarding.service.discount.DiscountCalculator;
import com.wanted.preonboarding.service.discount.concrete.FixedAmountDiscountCalculator;
import com.wanted.preonboarding.service.discount.concrete.PercentageDiscountCalculator;
import java.math.BigDecimal;

public enum DiscountType {
    PURCHASE_BEFORE_ONE_WEEK(new FixedAmountDiscountCalculator(BigDecimal.valueOf(2000)), "일주일 전 결제"), // 일주일 이전에 결제시 2000원 할인
    FIRST_PURCHASE(new PercentageDiscountCalculator(BigDecimal.valueOf(0.1)), "첫 결제"), // 첫 결제시 10퍼센트 할인
    PER_TEN_TIMES(new PercentageDiscountCalculator(BigDecimal.valueOf(0.1)), "10번마다 결제"); // 10번 결제마다 10퍼센트 할인

    private final DiscountCalculator discountCalculator;
    private final String discountDesc;

    DiscountType(DiscountCalculator discountCalculator, String discountDesc) {
        this.discountCalculator = discountCalculator;
        this.discountDesc = discountDesc;
    }

    public DiscountCalculator getDiscountCalculator() {
        return discountCalculator;
    }

    public String getDiscountDesc() {
        return discountDesc;
    }
}
