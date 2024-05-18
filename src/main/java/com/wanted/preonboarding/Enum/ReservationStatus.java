package com.wanted.preonboarding.Enum;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public enum ReservationStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    CANCEL("cancel"),
    PERFORMANCE_CANCEL("performance_cancel"),
    EMPTY("Nothing");

    private final String typeDesc;

    ReservationStatus(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public static ReservationStatus getType(String typeDesc) {
        return Arrays.stream(ReservationStatus.values())
                .filter(status -> StringUtils.equals(typeDesc, status.typeDesc))
                .findAny()
                .orElse(EMPTY);
    }
}
