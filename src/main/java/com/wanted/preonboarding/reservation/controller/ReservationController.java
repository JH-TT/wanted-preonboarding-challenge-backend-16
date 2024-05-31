package com.wanted.preonboarding.reservation.controller;

import com.wanted.preonboarding.Enum.ReservationStatus;
import com.wanted.preonboarding.reservation.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.dto.ReservationResponse;
import com.wanted.preonboarding.reservation.dto.ReservationUserInfo;
import com.wanted.preonboarding.reservation.service.ReservationService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    // 좌석을 여러개 예약할 수 있다.
    @PostMapping("/{id}")
    public ReservationResponse reserve(@PathVariable("id") UUID performanceId, @RequestBody ReservationRequest request) {
        log.info("reserve 실행");
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

    // 해당 예약 삭제 (만약 예약이 여러자리 예약이었으면 그 좌석들도 전부 예약취소되어야 한다.)
    @DeleteMapping("/{id}")
    public void reservationDelete(@PathVariable long id) {
        reservationService.deleteReservation(id, ReservationStatus.CANCEL);
    }
}
