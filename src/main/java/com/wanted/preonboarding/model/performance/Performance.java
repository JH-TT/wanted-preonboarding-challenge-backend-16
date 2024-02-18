package com.wanted.preonboarding.model.performance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
public class Performance {
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
    @Builder.Default
    private List<PerformanceSeatInfo> seats = new ArrayList<>();

    public void reserveSeat(PerformanceSeatInfo seatInfo) {
        this.seats.add(seatInfo);
        seatInfo.updatePerformance(this);
    }

    public List<PerformanceSeatInfo> getReserveSeat() {
        return Collections.unmodifiableList(seats);
    }
}
