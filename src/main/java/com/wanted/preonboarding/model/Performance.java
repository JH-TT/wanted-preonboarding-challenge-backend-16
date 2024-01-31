package com.wanted.preonboarding.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table
@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Performance {
    @Id
//    @GeneratedValue(generator = "uuid2")
//    @Column(columnDefinition = "BINARY(16)", nullable = false)
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
//    @CreatedDate
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
}
