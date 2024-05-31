package com.wanted.preonboarding.reservation.service;

import com.wanted.preonboarding.Enum.ReservationStatus;
import com.wanted.preonboarding.reservation.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.dto.ReservationResponse;
import com.wanted.preonboarding.reservation.dto.ReservationUserInfo;
import java.util.List;

public interface ReservationService {
    ReservationResponse reserve(ReservationRequest request);

    List<ReservationResponse> reservationList(ReservationUserInfo request);
    void deleteReservation(long id, ReservationStatus status);
}
