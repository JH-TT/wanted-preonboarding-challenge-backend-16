package com.wanted.preonboarding.service.reservation;

import com.wanted.preonboarding.Enum.ReservationStatus;
import com.wanted.preonboarding.dto.reservation.ReservationInfo;
import com.wanted.preonboarding.dto.reservation.ReservationRequest;
import com.wanted.preonboarding.dto.reservation.ReservationResponse;
import com.wanted.preonboarding.dto.reservation.ReservationUserInfo;
import com.wanted.preonboarding.dto.seat.PerformanceSeat;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final JdbcTemplate jdbcTemplate;

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

        List<Reservation> reservationList = new ArrayList<>();
        ArrayList<String> seatInfos = new ArrayList<>();
        for (PerformanceSeat performanceSeat : request.getSeatList()) {
            // 좌석을 가져온다.
            PerformanceSeatInfo seat = getSeat(performance, performanceSeat);

            // 예약을 진행한다.
            Reservation reservation = Reservation.toEntity(request, performanceSeat);
            reservationList.add(reservation);
            seatInfos.add(reservation.getSeatInfo());
            seat.reserveSuccess(reservation.getId()); // 좌석을 예약처리한다.
        }
        this.pay(request, (long) performance.getPrice() * request.getSeatList().size(), performance.getStartDate(), LocalDateTime.now()); // 결제 진행
        reservationRepository.saveAll(reservationList);

        return ReservationResponse.of(request, seatInfos);
    }

    private PerformanceSeatInfo getSeat(Performance performance, PerformanceSeat performanceSeat) {
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.findPerformanceSeatInfo(
                        performance,
                        performanceSeat.getGate(),
                        performanceSeat.getLine(),
                        performanceSeat.getSeat())
                .orElseThrow(() -> new IllegalArgumentException("해당 좌석이 없습니다."));

        if (!performanceSeatInfo.reservationEnable()) {
            throw new IllegalArgumentException("예약할 수 없는 좌석입니다.");
        }
        return performanceSeatInfo;
    }

    @Override
    public List<ReservationInfo> reservationList(ReservationUserInfo request) {
        List<Reservation> reservationList = reservationRepository.findAllByUserNameAndPhoneNumber(request.getUserName(),
                request.getPhoneNumber());
        return reservationList.stream().map(ReservationInfo::of).collect(Collectors.toList());
    }

    @Override
    public void deleteReservation(int id, ReservationStatus status) {
        reservationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 예약이 없습니다.")
        );
        reservationRepository.softDeleteById(status, LocalDateTime.now(), id);
        performanceSeatInfoRepository.deleteByReservationId(id);
    }


    private void pay(ReservationRequest request, long price, LocalDateTime startDate, LocalDateTime now) {
        long countReservation = reservationRepository.countByNameAndPhoneNumber(
                request.getUserName(), request.getPhoneNumber()); // 지금까지 결제한 횟수
        // 총 금액 계산하기
        ArrayList<String> salesList = new ArrayList<>();
        request.payable(DiscountUtils.pay(countReservation, price, startDate, now, salesList), salesList);
    }
}
