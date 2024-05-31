package com.wanted.preonboarding.performance.model;

import com.wanted.preonboarding.model.BaseEntity;
import com.wanted.preonboarding.seat.model.PerformanceSeatInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Performance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "round", nullable = false)
    private int round;

    /**
     *  0: 공연
     *  1: 전시회
     */
    @Column(name = "type", nullable = false)
    private int type;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "is_reserve", nullable = false, columnDefinition = "VARCHAR(255) default 'disable'")
    private String isReserve;

    @OneToMany(mappedBy = "performance")
    @Builder.Default
    private List<PerformanceSeatInfo> seats = new ArrayList<>();

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public void registerSeat(PerformanceSeatInfo seatInfo) {
        this.seats.add(seatInfo);
        seatInfo.updatePerformance(this);
        updateIsReservation(); // 좌석이 꽉 찼는지 확인한다
    }

    private void updateIsReservation() {
        if (getPerformanceSeatsCount() == 0) {
            this.isReserve = "disable";
        }
    }

    // 예약 가능 좌석 가져오기
    public long getPerformanceSeatsCount() {
        return seats.stream().filter(PerformanceSeatInfo::reservationEnable).count();
    }

    public void cancel() {
        this.isReserve = "disable";
        this.deletedAt = LocalDateTime.now();
    }
}
