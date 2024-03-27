package com.wanted.preonboarding.service.discount;

import com.wanted.preonboarding.Enum.DiscountType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DiscountUtils {

    public static long pay(long cnt, long price, LocalDateTime startDate, LocalDateTime now, ArrayList<String> salesList) {
        long totalDiscount = 0;
        totalDiscount += firstPay(cnt, price, salesList);
        totalDiscount += payBeforeOneWeek(price, startDate, now, salesList);
        totalDiscount += payPerTenTimes(cnt, price, salesList);

        return price - totalDiscount; // 최종 결제할 금액
    }

    private static long firstPay(long cnt, long price, ArrayList<String> salesList) {
        if (cnt != 0) return 0;
        DiscountService discountService = new DiscountService(BigDecimal.valueOf(price));
        salesList.add(DiscountType.FIRST_PURCHASE.getDiscountDesc());
        return discountService.applyDiscount(DiscountType.FIRST_PURCHASE).longValue();
    }

    private static long payBeforeOneWeek(long price, LocalDateTime startDate, LocalDateTime now, ArrayList<String> salesList) {
        if (ChronoUnit.WEEKS.between(now, startDate) < 1) return 0;
        DiscountService discountService = new DiscountService(BigDecimal.valueOf(price));
        salesList.add(DiscountType.PURCHASE_BEFORE_ONE_WEEK.getDiscountDesc());
        return discountService.applyDiscount(DiscountType.PURCHASE_BEFORE_ONE_WEEK).longValue();
    }

    private static long payPerTenTimes(long cnt, long price, ArrayList<String> salesList) {
        if (cnt == 0 || cnt % 10 != 0) return 0;
        DiscountService discountService = new DiscountService(BigDecimal.valueOf(price));
        salesList.add(DiscountType.PER_TEN_TIMES.getDiscountDesc());
        return discountService.applyDiscount(DiscountType.PER_TEN_TIMES).longValue();
    }
}
