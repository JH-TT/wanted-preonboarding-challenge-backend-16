package com.wanted.preonboarding.reservation.dto;

import com.wanted.preonboarding.seat.dto.PerformanceSeat;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReservationRequest {

    private String userName;
    private String phoneNumber;
    private String performName;
    private long performId;
    private int round;
    private long balance;

    // 좌석 정보(입장 게이트, 좌석 열, 좌석 행)
    private List<PerformanceSeat> seatList = new ArrayList<>();
    private List<String> salesList = new ArrayList<>();

    public void payable(long price, List<String> salesList) {
        if (balance < price) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        this.balance -= price;
        this.salesList = salesList;
    }
}
