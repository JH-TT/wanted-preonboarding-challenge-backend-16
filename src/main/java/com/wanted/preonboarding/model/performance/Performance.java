package com.wanted.preonboarding.model.performance;

import com.wanted.preonboarding.dto.seat.PerformanceSeat;
import com.wanted.preonboarding.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@SQLRestriction("deleted_at IS NULL")
public class Performance extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "round", nullable = false)
    private int round;
    @Column(name = "type", nullable = false)
    private int type;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "is_reserve", nullable = false, columnDefinition = "VARCHAR(255) default 'disable'")
    private String isReserve;

    @OneToMany
    @JoinColumn(name = "performance_id")
    private List<PerformanceSeatInfo> seats = new ArrayList<>();

    public void reserveSeat(PerformanceSeatInfo seatInfo) {
        this.seats.add(seatInfo);
        seatInfo.updatePerformance(this);
        updateIsReservation(); // 좌석이 꽉 찼는지 확인한다
    }

    private void updateIsReservation() {
        if (getPerformanceSeats().size() == 0) {
            this.isReserve = "disable";
        }
    }

    // 예약 가능 여부
    public boolean isReservationEnable() {
        return "enable".equals(isReserve);
    }

    // 예약 가능 좌석 가져오기
    public List<PerformanceSeat> getPerformanceSeats() {
        return seats.stream()
                .filter(PerformanceSeatInfo::reservationEnable)
                .map(PerformanceSeat::of)
                .collect(Collectors.toList());
    }

    public List<PerformanceSeatInfo> getReserveSeat() {
        return Collections.unmodifiableList(seats);
    }
}
