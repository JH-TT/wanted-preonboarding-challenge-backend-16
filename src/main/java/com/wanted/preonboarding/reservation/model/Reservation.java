package com.wanted.preonboarding.reservation.model;

import com.wanted.preonboarding.Enum.ReservationStatus;
import com.wanted.preonboarding.performance.model.PerformanceDetail;
import com.wanted.preonboarding.model.BaseEntity;
import com.wanted.preonboarding.seat.model.Seat;
import com.wanted.preonboarding.seat.model.SeatDetail;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 예약의 경우 일단은 3년 유지
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
@Table
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_detail_id", nullable = false)
    private PerformanceDetail performanceDetail;
    @Column(nullable = false)
    private String performanceName;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String sex;
    @Column(nullable = false)
    private int age;
    @OneToMany(mappedBy = "reservation")
    @Builder.Default
    private List<SeatDetail> seats = new ArrayList<>();
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    @ElementCollection
    private List<String> salesList = new ArrayList<>();
    @Column
    private String refundSeats; // 환불하게 되면 남는 좌석 정보
    @Column
    @Builder.Default
    private boolean deleteFlag = false; // 만료된 예약인지...
}
