package com.wanted.preonboarding.model.performance;

import com.wanted.preonboarding.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Table
@ToString(exclude = "performance")
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PerformanceSeatInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @Column(columnDefinition = "VARCHAR(255) default 'enable'")
    private String isReserve;

    public PerformanceSeatInfo(int id, Performance referenceById, int round, int gate, String a, int seat) {
        this.id = id;
        this.performance = referenceById;
        this.round = round;
        this.gate = gate;
        this.line = a;
        this.seat = seat;
    }

    public void updatePerformance(Performance performance) {
        this.performance = performance;
    }
}
