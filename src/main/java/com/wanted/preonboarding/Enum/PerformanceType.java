package com.wanted.preonboarding.Enum;

import java.util.Arrays;

public enum PerformanceType {
    EXHIBIION("전시회", 0),
    PERFORMANCE("공연", 1),
    NOTHING("불분명", -1);

    private final String typeDesc;
    private final int typeCode;

    PerformanceType(String typeDesc, int typeCode) {
        this.typeDesc = typeDesc;
        this.typeCode = typeCode;
    }

    public static String getTypeDesc(int typeCode) {
        return Arrays.stream(PerformanceType.values())
                .filter(status -> status.typeCode == typeCode)
                .findAny()
                .orElse(NOTHING).typeDesc;
    }
}
