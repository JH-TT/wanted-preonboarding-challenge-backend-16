package com.wanted.preonboarding.dto.reservation;

import java.util.UUID;
import lombok.Data;

@Data
public class ReservationRequest {

    private String userName;
    private String phoneNumber;
    private String performName;
    private UUID performId;
    private int round;
    private long balance;

    // 좌석 정보(입장 게이트, 좌석 열, 좌석 행)
    private int gate;
    private String line;
    private int seat;

    public void canReserve(long price) {
        if (balance < price) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
    }
}
