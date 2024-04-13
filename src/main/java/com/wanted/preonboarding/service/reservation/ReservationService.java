package com.wanted.preonboarding.service.reservation;

import com.wanted.preonboarding.Enum.ReservationStatus;
import com.wanted.preonboarding.dto.reservation.ReservationInfo;
import com.wanted.preonboarding.dto.reservation.ReservationRequest;
import com.wanted.preonboarding.dto.reservation.ReservationResponse;
import com.wanted.preonboarding.dto.reservation.ReservationUserInfo;
import java.util.List;

public interface ReservationService {
    ReservationResponse reserve(ReservationRequest request);

    List<ReservationInfo> reservationList(ReservationUserInfo request);
    void deleteReservation(int id, ReservationStatus status);
}
