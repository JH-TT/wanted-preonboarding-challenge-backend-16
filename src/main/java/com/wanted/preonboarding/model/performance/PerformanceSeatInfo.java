package com.wanted.preonboarding.model.performance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PerformanceSeatInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", columnDefinition = "BINARY(16)", nullable = false)
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
    @ColumnDefault("enable")
    private String isReserve;

//    @CreatedDate
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    private LocalDateTime updatedAt;

    public PerformanceSeatInfo(int id, Performance referenceById, int round, int gate, String a, int seat) {
        this.id = id;
        this.performance = referenceById;
        this.round = round;
        this.gate = gate;
        this.line = a;
        this.seat = seat;
    }
}
