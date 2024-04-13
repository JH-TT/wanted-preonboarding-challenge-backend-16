package com.wanted.preonboarding.dto.reservation;

import com.wanted.preonboarding.model.reservation.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ReservationInfo {
    private int round;
    private String performanceName;

    // 좌석정보
    private int gate;
    private String line;
    private int seat;

    // 예매자 정보
    private String name;
    private String phoneNumber;

    public static ReservationInfo of(Reservation request) {
        return ReservationInfo.builder()
                .round(request.getRound())
                .performanceName(request.getPerformanceName())
                .gate(request.getGate())
                .line(request.getLine())
                .seat(request.getSeat())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
}
