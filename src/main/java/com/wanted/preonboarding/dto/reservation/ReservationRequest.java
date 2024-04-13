package com.wanted.preonboarding.dto.reservation;

import com.wanted.preonboarding.dto.seat.PerformanceSeat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    private UUID performId;
    private int round;
    private long balance;

    // 좌석 정보(입장 게이트, 좌석 열, 좌석 행)
    private List<PerformanceSeat> seatList = new ArrayList<>();
    private ArrayList<String> salesList;

    public void payable(long price, ArrayList<String> salesList) {
        if (balance < price) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        this.balance -= price;
        this.salesList = salesList;
    }
}
