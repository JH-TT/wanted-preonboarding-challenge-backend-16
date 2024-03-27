package com.wanted.preonboarding.service.reservation;

import com.wanted.preonboarding.dto.reservation.ReservationRequest;
import com.wanted.preonboarding.dto.reservation.ReservationResponse;
import com.wanted.preonboarding.dto.reservation.ReservationUserInfo;
import com.wanted.preonboarding.model.performance.Performance;
import com.wanted.preonboarding.model.performance.PerformanceSeatInfo;
import com.wanted.preonboarding.model.reservation.Reservation;
import com.wanted.preonboarding.repository.PerformanceRepository;
import com.wanted.preonboarding.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.repository.ReservationRepository;
import com.wanted.preonboarding.service.discount.DiscountUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    /**
     * reservation process
     * 1. 공연이 유효한가
     * 2. 좌석이 유효한가
     * 3. 돈은 맞게 갖고 있는가
     * 4. 예약 성공
     *      4-1. 해당 좌석 예약 처리
     */
    @Transactional
    public ReservationResponse reserve(ReservationRequest request) {
        Performance performance = performanceRepository.findById(request.getPerformId())
                .orElseThrow(() -> new IllegalArgumentException("공연이 없습니다."));

        PerformanceSeatInfo seat = performanceSeatInfoRepository.findPerformanceSeatInfo(performance, request.getGate(),
                request.getLine(), request.getSeat()).orElseThrow(
                () -> new IllegalArgumentException("해당 좌석이 없습니다."));

        if (!seat.reservationEnable()) {
            throw new IllegalArgumentException("해당 좌석은 이미 예약 완료되었습니다");
        }

        this.pay(request, performance.getPrice(), performance.getStartDate(), LocalDateTime.now()); // 결제 진행

        Reservation reservation = Reservation.toEntity(request);
        reservationRepository.save(reservation);

        seat.reserveSuccess();

        return ReservationResponse.of(reservation);
    }

    @Override
    public List<ReservationResponse> reservationList(ReservationUserInfo request) {
        List<Reservation> reservationList = reservationRepository.findAllByUserNameAndPhoneNumber(request.getUserName(),
                request.getPhoneNumber());
        return reservationList.stream().map(ReservationResponse::of).collect(Collectors.toList());
    }

    private void pay(ReservationRequest request, long price, LocalDateTime startDate, LocalDateTime now) {
        long countReservation = reservationRepository.countByNameAndPhoneNumber(
                request.getUserName(), request.getPhoneNumber()); // 지금까지 결제한 횟수
        // 총 금액 계산하기
        ArrayList<String> salesList = new ArrayList<>();
        request.payable(DiscountUtils.pay(countReservation, price, startDate, now, salesList), salesList);
    }
}
