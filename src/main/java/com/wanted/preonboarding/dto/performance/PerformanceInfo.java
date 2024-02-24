package com.wanted.preonboarding.dto.performance;

import com.wanted.preonboarding.model.performance.Performance;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PerformanceInfo {
    private String name; // 공연명
    private int round; // 회차
    private int type; // 공연인지 전시회 인지
    private String startDate; // 시작 일시
    private String isReserve; // 예매 가능 여부

    public static PerformanceInfo of(Performance perform) {
        return PerformanceInfo.builder()
                .name(perform.getName())
                .round(perform.getRound())
                .type(perform.getType())
                .startDate(perform.getStartDate().toString())
                .isReserve(perform.getIsReserve())
                .build();
    }
}
