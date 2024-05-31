package com.wanted.preonboarding.reservation.dto;

import com.wanted.preonboarding.reservation.model.Reservation;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/*
Response Message: 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
 */
@Setter
@Getter
@Builder
@ToString
public class ReservationResponse {
    private long reservationId;
    private int round;
    private String performanceName;
    private long balance;

    // 좌석정보
    private String seatInfos;

    // 예매자 정보
    private String name;
    private String phoneNumber;
    private List<String> salesList;

    public static ReservationResponse of(Reservation reservation) {
        return ReservationResponse.builder()
                .reservationId(reservation.getId())
                .round(reservation.getRound())
                .performanceName(reservation.getPerformanceName())
                .seatInfos(String.join(", ", reservation.getReservedSeats()))
                .name(reservation.getName())
                .phoneNumber(reservation.getPhoneNumber())
                .salesList(reservation.getSalesList() == null ? new ArrayList<>() : reservation.getSalesList())
                .build();
    }
}
