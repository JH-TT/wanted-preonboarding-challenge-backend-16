package com.wanted.preonboarding.performance.dto;

import com.wanted.preonboarding.performance.model.Performance;
import com.wanted.preonboarding.place.model.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PerformanceInfo {
    private String name; // 공연명
    private Place place; // 장소
    private String type; // 공연인지 전시회 인지
    private String startDate; // 시작 일시
    private String endDate; // 마지막 공연 날짜

    public static PerformanceInfo of(Performance p) {
        return PerformanceInfo.builder()
                .name(p.getName())
                .place(p.getPlaceId())
                .type(p.getType())
                .startDate(p.getStartDate())
                .endDate(p.getEndDate())
                .build();
    }
}
