package com.wanted.preonboarding.seat.dto;

import com.wanted.preonboarding.seat.model.Seat;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatInfo {
    private Integer id;
    private int floor;
    private String line;
    private Integer seat;
    private Long placeId;

    public static SeatInfo of(Seat seat) {
        return SeatInfo.builder()
                .id(seat.getId())
                .floor(seat.getFloor())
                .line(seat.getLine())
                .seat(seat.getSeat())
                .placeId(seat.getPlaceId().getId())
                .build();
    }
}
