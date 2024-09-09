package com.wanted.preonboarding.seat.model;

import com.wanted.preonboarding.Enum.SeatGrade;
import com.wanted.preonboarding.model.BaseEntity;
import com.wanted.preonboarding.performance.model.PerformanceDetail;
import com.wanted.preonboarding.reservation.model.Reservation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SeatDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_detail_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "performance_detail_id")
    private PerformanceDetail performanceDetailId;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat performanceSeatInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(nullable = false)
    private SeatGrade grade;
}
