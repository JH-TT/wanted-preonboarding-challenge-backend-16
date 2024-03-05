package com.wanted.preonboarding.service.reservation;

import com.wanted.preonboarding.dto.reservation.ReservationRequest;
import com.wanted.preonboarding.dto.reservation.ReservationResponse;

public interface ReservationService {
    ReservationResponse reserve(ReservationRequest request);
}
