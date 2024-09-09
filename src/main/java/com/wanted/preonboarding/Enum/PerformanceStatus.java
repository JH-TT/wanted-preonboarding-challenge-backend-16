package com.wanted.preonboarding.Enum;

public enum PerformanceStatus {
    BEFORE("상영전"),
    ACTIVE("상영중"),
    CLOSED("마감"),
    END("종료"),
    CANCEL("취소");

    private final String statusDesc;

    PerformanceStatus(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getStatusDesc() {
        return statusDesc;
    }
}
