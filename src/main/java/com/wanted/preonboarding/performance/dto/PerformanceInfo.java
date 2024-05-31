package com.wanted.preonboarding.performance.dto;

import com.wanted.preonboarding.performance.model.Performance;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PerformanceInfo {
    private String name; // 공연명
    private long performanceId; // 공연/전시회 ID
    private int round; // 회차
    private int type; // 공연인지 전시회 인지
    private String startDate; // 시작 일시
    private String isReserve; // 예매 가능 여부
    private long remainSeat; // 남은 좌석의 수

    public static PerformanceInfo of(Performance perform) {
        return PerformanceInfo.builder()
                .name(perform.getName())
                .performanceId(perform.getId())
                .round(perform.getRound())
                .type(perform.getType())
                .startDate(perform.getStartDate().toString())
                .isReserve(perform.getIsReserve())
                .remainSeat(perform.getPerformanceSeatsCount())
                .build();
    }
}
