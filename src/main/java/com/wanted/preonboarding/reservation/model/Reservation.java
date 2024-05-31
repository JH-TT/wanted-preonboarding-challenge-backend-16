package com.wanted.preonboarding.reservation.model;

import com.wanted.preonboarding.Enum.ReservationStatus;
import com.wanted.preonboarding.reservation.dto.ReservationRequest;
import com.wanted.preonboarding.model.BaseEntity;
import com.wanted.preonboarding.seat.model.PerformanceSeatInfo;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
@Table(indexes = @Index(name = "idx_performance_id", columnList = "performance_id"))
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private Long performanceId;
    @Column(nullable = false)
    private String performanceName;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private int round;
    @OneToMany(mappedBy = "reservation")
    @Builder.Default
    private List<PerformanceSeatInfo> seats = new ArrayList<>();
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    @ElementCollection
    private List<String> salesList = new ArrayList<>();
    @Column
    private String refundSeats; // 환불하게 되면 남는 좌석 정보

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static Reservation toEntity(ReservationRequest request) {
        return Reservation.builder()
                .performanceId(request.getPerformId())
                .performanceName(request.getPerformName())
                .name(request.getUserName())
                .phoneNumber(request.getPhoneNumber())
                .round(request.getRound())
                .status(ReservationStatus.ACTIVE)
                .salesList(request.getSalesList())
                .build();
    }

    public void reserveSeat(PerformanceSeatInfo seatInfo) {
        this.seats.add(seatInfo);
        seatInfo.reserveSuccess(this); // 좌석을 예약 처리
    }

    // 좌석과의 관계 삭제
    public void refund(ReservationStatus status) {
        refundSeats = String.join(", ", this.getReservedSeats());
        // 좌석들 예약과 연관관계 삭제
        for (PerformanceSeatInfo seat : seats) {
            seat.refundSeat();
        }
        this.status = status;
        deletedAt = LocalDateTime.now();
        seats = new ArrayList<>();
    }

    // 예약한 좌석을 가져온다.
    public List<String> getReservedSeats() {
        return this.seats.stream().map(PerformanceSeatInfo::seatInfo).toList();
    }
    public void updateStatus(ReservationStatus status) {
        this.status = status;
    }
}
