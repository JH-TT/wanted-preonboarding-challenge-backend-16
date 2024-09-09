package com.wanted.preonboarding.reservation.controller;

import com.wanted.preonboarding.Enum.ReservationStatus;
import com.wanted.preonboarding.reservation.dto.ReservationUserInfo;
import com.wanted.preonboarding.reservation.service.ReservationService;
import java.util.List;
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
}
