package com.wanted.preonboarding.seat.controller;

import com.wanted.preonboarding.seat.dto.EnrollInfo;
import com.wanted.preonboarding.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("seat")
public class SeatController {

    private final SeatService seatService;

    @PatchMapping("/{placeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enrollSeats(@PathVariable("placeId") Long placeId, @RequestBody EnrollInfo info) throws Exception {
        seatService.enrollSeats(placeId, info);
    }
}
