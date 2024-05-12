package com.wanted.preonboarding.model.performance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanted.preonboarding.model.reservation.Reservation;
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
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Table
@Builder
@ToString(exclude = {"performance", "reservation"})
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PerformanceSeatInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", columnDefinition = "BINARY(16)")
    private Performance performance;

    @Column(nullable = false)
    private int round;

    @Column(nullable = false)
    private int gate;

    @Column(length = 2, nullable = false)
    private String line;

    @Column(nullable = false)
    private int seat;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(columnDefinition = "VARCHAR(255) default 'enable'")
    private String isReserve;

    public PerformanceSeatInfo(Performance referenceById, int round, int gate, String a, int seat) {
        this.performance = referenceById;
        this.round = round;
        this.gate = gate;
        this.line = a;
        this.seat = seat;
    }

    // 좌석 예약 여부
    public boolean canReserve(int gate, String line, int seat) {
        return this.gate == gate
                && this.line.equals(line)
                && this.seat == seat
                && reservationEnable();
    }

    // 예약 완료
    public void reserveSuccess(Reservation reservation) {
        this.isReserve = "disable";
        this.reservation = reservation;
    }

    // 현재 좌석이 매진되었는가
    public boolean reservationEnable() {
        return "enable".equals(isReserve);
    }

    public void updatePerformance(Performance performance) {
        this.performance = performance;
    }

    public String seatInfo() {
        return gate + "관 " + line + seat;
    }
}
