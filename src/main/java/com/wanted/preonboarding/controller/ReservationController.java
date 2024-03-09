package com.wanted.preonboarding.controller;

import com.wanted.preonboarding.dto.reservation.ReservationRequest;
import com.wanted.preonboarding.dto.reservation.ReservationResponse;
import com.wanted.preonboarding.dto.reservation.ReservationUserInfo;
import com.wanted.preonboarding.service.reservation.ReservationService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{id}")
    public ReservationResponse reserve(@PathVariable("id") UUID performanceId, @RequestBody ReservationRequest request) {
        request.setPerformId(performanceId);
        return reservationService.reserve(request);
    }

    /**
     * 유저 이름과 유저 전화번호 정보를 받고 해당 예약 정보를 리턴한다.
     */
    @GetMapping("")
    public List<ReservationResponse> reservationList(@RequestBody ReservationUserInfo request) {
        return reservationService.reservationList(request);
    }
}
