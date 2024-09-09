package com.wanted.preonboarding.performance.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PerformanceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performance_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @Column(nullable = false)
    private String date; // 해당 공연 날짜

    @Column(nullable = false)
    private String bookingEndDate; // 예약 끝나는 시간 dateFormat : yyyymmddHHmmss

    @Column(nullable = false)
    private String cancelableDate; // 취소 가능 마지막 시간 dateFormat : yyyymmddHHmmss

    @Column(nullable = false)
    private String playTime; // 분 단위
}
