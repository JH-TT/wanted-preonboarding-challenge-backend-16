package com.wanted.preonboarding.seat.model;

import com.wanted.preonboarding.Enum.SeatGrade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_price_id")
    private Long id;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatGrade grade;
    @Column(nullable = false)
    private Long performanceId;
}
