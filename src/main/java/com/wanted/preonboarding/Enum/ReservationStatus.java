package com.wanted.preonboarding.Enum;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public enum ReservationStatus {
    ACTIVE("유효", false),
    INACTIVE("만료", true),
    CANCEL("예약 취소", true),
    PERFORMANCE_CANCEL("공연 취소", true),
    EMPTY("Nothing", true);

    private final String typeDesc;
    private final boolean isDeleted;

    ReservationStatus(String typeDesc, boolean isDeleted) {
        this.typeDesc = typeDesc;
        this.isDeleted = isDeleted;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public static ReservationStatus getType(String typeDesc) {
        return Arrays.stream(ReservationStatus.values())
                .filter(status -> StringUtils.equals(typeDesc, status.typeDesc))
                .findAny()
                .orElse(EMPTY);
    }
}
