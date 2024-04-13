package com.wanted.preonboarding.dto.reservation;

import java.util.ArrayList;
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
    private int round;
    private String performanceName;
    private long balance;

    // 좌석정보
    private String seatInfos;

    // 예매자 정보
    private String name;
    private String phoneNumber;
    private ArrayList<String> salesList;

    public static ReservationResponse of(ReservationRequest request, ArrayList<String> seats) {
        return ReservationResponse.builder()
                .round(request.getRound())
                .performanceName(request.getPerformName())
                .balance(request.getBalance())
                .seatInfos(String.join(", ", seats))
                .name(request.getUserName())
                .phoneNumber(request.getPhoneNumber())
                .salesList(request.getSalesList())
                .build();
    }
}
