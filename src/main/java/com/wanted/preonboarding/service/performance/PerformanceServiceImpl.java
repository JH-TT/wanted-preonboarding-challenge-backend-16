package com.wanted.preonboarding.service.performance;

import com.wanted.preonboarding.Enum.ReservationStatus;
import com.wanted.preonboarding.dto.performance.PerformanceInfoRequest;
import com.wanted.preonboarding.dto.seat.PerformanceSeat;
import com.wanted.preonboarding.model.performance.Performance;
import com.wanted.preonboarding.model.reservation.Reservation;
import com.wanted.preonboarding.repository.PerformanceRepository;
import com.wanted.preonboarding.repository.ReservationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceServiceImpl implements PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PerformanceSeat> getSeats(PerformanceInfoRequest request) {
        Optional<Performance> performance = performanceRepository.getPerformanceByNameAndRound(request.getName(),
                request.getRound());
        return performance.map(value -> value.getSeats().stream()
                        .map(PerformanceSeat::of)
                        .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    /**
     * 공연 취소
     * 공연이 취소되면 관련 예약들은 전부 취소된다.
     * 공연은 논리적 삭제가 이뤄진다.
     */
    @Override
    public void performanceCancel(UUID id) {
        Performance performance = performanceRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 공연이 존재하지 않습니다."));

        // 해당 공연을 예약한 것들은 전부 취소
        // 해당 공연에 대한 예약을 전부 가져와서 -> 각각 예약을 취소하는 방향
        List<Reservation> reservations = reservationRepository.findByPerformanceId(id);

        for (Reservation reservation : reservations) {
            reservation.refund(ReservationStatus.PERFORMANCE_CANCEL); // 공연취소로 인한 예약 취소
        }
        performance.cancel();
//        performanceRepository.performanceCancel(id, LocalDateTime.now());
    }
}
