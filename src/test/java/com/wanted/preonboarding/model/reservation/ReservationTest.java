package com.wanted.preonboarding.model.reservation;


import com.wanted.preonboarding.dto.reservation.ReservationRequest;
import com.wanted.preonboarding.model.performance.Performance;
import com.wanted.preonboarding.model.performance.PerformanceSeatInfo;
import com.wanted.preonboarding.repository.PerformanceRepository;
import com.wanted.preonboarding.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.repository.ReservationRepository;
import com.wanted.preonboarding.service.reservation.ReservationService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@Transactional
class ReservationTest {

    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PerformanceSeatInfoRepository performanceSeatInfoRepository;
    @Autowired
    private ReservationService reservationService;

    private static final UUID uuid = UUID.randomUUID();
    private static final String PERFORMANCE_NAME = "게르테나 미술관";
    private static final String USER_NAME = "이종호";
    private static final String PHONE_NUMBER = "010-1234-5678";

    @Test
    @DisplayName("예약 성공 테스트")
    void 테스트() throws Exception {
        // given
        Performance performance = getSamplePerformance(PERFORMANCE_NAME, 20000, 1, 0, LocalDateTime.now(), "enable"); // 게르테나 미술관 전시회 정보
        Performance savedPerformance = performanceRepository.save(performance);

        for (int i = 1; i < 5; i++) {
            PerformanceSeatInfo seatInfo = getSampleSeat(savedPerformance, i);
            performanceSeatInfoRepository.save(seatInfo);
            performance.reserveSeat(seatInfo);
        }

        // when
        ReservationRequest request = new ReservationRequest();
        request.setPerformId(savedPerformance.getId());
        request.setPerformName(PERFORMANCE_NAME);
        request.setGate(1);
        request.setLine("A");
        request.setSeat(1);
        request.setRound(1);
        request.setBalance(100000);
        request.setUserName(USER_NAME);
        request.setPhoneNumber(PHONE_NUMBER);
        System.out.println("reserve하기전 공연ID : " + savedPerformance.getId());
        reservationService.reserve(request);

        // then
        //  예약을 성공했으니 해당 유저의 예약좌석 개수는 1개일 것이고, 해당 좌석은 예약된 자리로 표시되어야 한다.
        List<Reservation> reservationList = reservationRepository.findAllByUserNameAndPhoneNumber(USER_NAME,
                PHONE_NUMBER);
        PerformanceSeatInfo seatInfo = performanceSeatInfoRepository.findPerformanceSeatInfo(savedPerformance, 1, "A", 1)
                .orElseThrow(() -> new IllegalArgumentException("좌석이 없습니다."));

        Assertions.assertThat(reservationList.size()).isEqualTo(1);
        Assertions.assertThat(seatInfo.getIsReserve()).isEqualTo("disable");
    }

    /**
     * 공연 Fixture 생성
     * @param performanceName 게르테나 미술관
     * @param price 20000
     * @param round 1
     * @param type 0
     * @param startDate 현재시간
     * @param isReserve enable
     * @return Performance
     */
    private Performance getSamplePerformance(String performanceName, int price, int round, int type, LocalDateTime startDate, String isReserve) {
        return Performance.builder()
                .name(performanceName)
                .price(price)
                .round(round) // 1회차
                .type(type)  // 0: 전시회, 1: 공연
                .startDate(startDate)
                .isReserve(isReserve)
                .build();
    }

    /**
     * 공연 좌석 Fixture 생성
     * @param performance 게르테나 미술관
     * @param seat 좌석 열
     * @return PerformanceSeatInfo
     */
    private PerformanceSeatInfo getSampleSeat(Performance performance, int seat) {
        return PerformanceSeatInfo.builder()
                .seat(seat)
                .performance(performance)
                .round(1)
                .isReserve("enable")
                .gate(1)
                .line("A")
                .build();
    }
}