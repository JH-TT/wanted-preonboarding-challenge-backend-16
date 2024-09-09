package com.wanted.preonboarding.place.model;

import com.wanted.preonboarding.place.dto.PlaceInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @Column
    private String phoneNumber;

    public static Place toEntity(PlaceInfo info) {
        return Place.builder()
                .name(info.getName())
                .address(info.getAddress())
                .phoneNumber(info.getPhoneNumber())
                .build();
    }
}
