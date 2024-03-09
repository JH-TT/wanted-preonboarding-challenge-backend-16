package com.wanted.preonboarding.dto.reservation;

import com.wanted.preonboarding.model.reservation.Reservation;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/*
Response Message: 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
 */
@Setter
@Getter
@Builder
public class ReservationResponse {
    private int round;
    private String performanceName;
    private UUID performId;
    // 좌석정보
    private int gate;
    private String line;
    private int seat;

    // 예매자 정보
    private String name;
    private String phoneNumber;

    public static ReservationResponse of(Reservation request) {
        return ReservationResponse.builder()
                .round(request.getRound())
                .performanceName(request.getPerformanceName())
                .performId(request.getPerformanceId())
                .gate(request.getGate())
                .line(request.getLine())
                .seat(request.getSeat())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
}
