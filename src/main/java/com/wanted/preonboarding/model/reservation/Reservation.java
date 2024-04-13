package com.wanted.preonboarding.model.reservation;

import com.wanted.preonboarding.Enum.ReservationStatus;
import com.wanted.preonboarding.dto.reservation.ReservationRequest;
import com.wanted.preonboarding.dto.seat.PerformanceSeat;
import com.wanted.preonboarding.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
@Table(indexes = @Index(name = "idx_performance_id", columnList = "performance_id"))
@SQLRestriction("deleted_at IS NULL")
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;
    @Column(nullable = false)
    private String performanceName;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private int round;
    @Column(nullable = false)
    private int gate;
    @Column(nullable = false)
    private String line;
    @Column(nullable = false)
    private int seat;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public static Reservation toEntity(ReservationRequest request, PerformanceSeat performSeat) {
        return Reservation.builder()
                .performanceId(request.getPerformId())
                .performanceName(request.getPerformName())
                .name(request.getUserName())
                .phoneNumber(request.getPhoneNumber())
                .round(request.getRound())
                .gate(performSeat.getGate())
                .line(performSeat.getLine())
                .seat(performSeat.getSeat())
                .status(ReservationStatus.ACTIVE)
                .build();
    }

    public void updateStatus(ReservationStatus status) {
        this.status = status;
    }
    public String getSeatInfo() {
        return gate + "관 " + line + seat;
    }
}
