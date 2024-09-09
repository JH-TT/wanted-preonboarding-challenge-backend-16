package com.wanted.preonboarding.performance.model;

import com.wanted.preonboarding.Enum.PerformanceStatus;
import com.wanted.preonboarding.Enum.PerformanceType;
import com.wanted.preonboarding.model.BaseEntity;
import com.wanted.preonboarding.place.model.Place;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Performance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performance_id")
    private Long id;
    @Column(nullable = false)
    private String name;

    /**
     *  0: 공연
     *  1: 전시회
     */
    @Column(nullable = false)
    private int type;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'BEFORE'")
    @Column(nullable = false)
    private PerformanceStatus status;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place placeId; // 장소

    // 상영시간 분단위
    @Column(nullable = false)
    private int runtime;

    // 예약 시작 날짜 (디폴트: 1달 전부터)
    @Column
    private String bookableDate;

    public String getType() {
        return PerformanceType.getTypeDesc(type);
    }
}
