package com.wanted.preonboarding.seat.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 좌석은 현재 열, 행, 층만 입력 받으면 알아서 등록되도록 하고 있음.
 * 좌석 등급은 VIP, OP, R, S, A 순으로 된다.
 * 행, 열의 개수를 입력받고 반복문으로 만든다.
 * 일단 층은 1층만 사용한다고 한다.
 */
@Getter
@Setter
public class EnrollInfo {
    private int seats;
    private int lines;
}
