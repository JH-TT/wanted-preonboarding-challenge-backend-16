package com.wanted.preonboarding.service.reservation;

import com.wanted.preonboarding.dto.reservation.ReservationRequest;
import com.wanted.preonboarding.dto.reservation.ReservationResponse;
import com.wanted.preonboarding.model.performance.Performance;
import com.wanted.preonboarding.model.performance.PerformanceSeatInfo;
import com.wanted.preonboarding.model.reservation.Reservation;
import com.wanted.preonboarding.repository.PerformanceRepository;
import com.wanted.preonboarding.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.repository.ReservationRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @Transactional
    public ReservationResponse reserve(ReservationRequest request) {
        // 예약하는 과정
        // 일단 해당 공연이 있는지 확인
        Performance performance = performanceRepository.findById(request.getPerformId().toString())
                .orElseThrow(() -> new IllegalArgumentException("공연이 없습니다."));
        // 해당 좌석이 알맞은 좌석인지 확인
        if (!performance.canReserveSeat(request.getGate(), request.getLine(), request.getSeat())) {
            throw new IllegalArgumentException("알맞은 좌석이 없습니다");
        }
        // 예약 확인
        Reservation reservation = Reservation.toEntity(request);
        reservationRepository.save(reservation);

        // 예약 성공했으니 해당 좌석은 만석
        PerformanceSeatInfo seat = performanceSeatInfoRepository.findPerformanceSeatInfo(performance, request.getGate(),
                request.getLine(), request.getSeat()).orElseThrow(
                        () -> new IllegalArgumentException("해당 좌석이 없습니다."));
        seat.reserveSuccess();
        return ReservationResponse.of(request);
    }
}
