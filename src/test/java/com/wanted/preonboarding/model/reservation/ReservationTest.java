package com.wanted.preonboarding.model.reservation;


import static org.junit.jupiter.api.Assertions.*;

import com.wanted.preonboarding.dto.reservation.ReservationRequest;
import com.wanted.preonboarding.dto.reservation.ReservationResponse;
import com.wanted.preonboarding.dto.reservation.ReservationUserInfo;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    void 예약_성공_테스트() {
        // given
        Performance performance = getSamplePerformance(PERFORMANCE_NAME, 20000, 1, 0, LocalDateTime.now(), "enable"); // 게르테나 미술관 전시회 정보
        Performance savedPerformance = performanceRepository.save(performance);

        for (int i = 1; i < 5; i++) {
            PerformanceSeatInfo seatInfo = getSampleSeat(savedPerformance, i);
            performanceSeatInfoRepository.save(seatInfo);
            performance.reserveSeat(seatInfo);
        }

        // when
        ReservationRequest request = getReservationRequest(savedPerformance.getId(), savedPerformance.getName(),
                100000);

        // then
        //  예약을 성공했으니 해당 유저의 예약좌석 개수는 1개일 것이고, 해당 좌석은 예약된 자리로 표시되어야 한다.
        List<Reservation> reservationList = reservationRepository.findAllByUserNameAndPhoneNumber(USER_NAME,
                PHONE_NUMBER);
        PerformanceSeatInfo seatInfo = performanceSeatInfoRepository.findPerformanceSeatInfo(savedPerformance, 1, "A", 1)
                .orElseThrow(() -> new IllegalArgumentException("좌석이 없습니다."));

        Assertions.assertThat(reservationList.size()).isEqualTo(1);
        Assertions.assertThat(seatInfo.getIsReserve()).isEqualTo("disable");
    }

    @Test
    @DisplayName("공연이 없는 경우")
    public void 공연이_없는_경우() {
        // given
        Performance performance = getSamplePerformance(PERFORMANCE_NAME, 20000, 1, 0, LocalDateTime.now(), "enable"); // 게르테나 미술관 전시회 정보
        Performance savedPerformance = performanceRepository.save(performance);

        // when
        ReservationRequest request = getReservationRequest(UUID.randomUUID(), savedPerformance.getName(), 100000); // 공연ID를 잘못 보낸다.

        // then
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.reserve(request));
        Assertions.assertThat(exception.getMessage()).isEqualTo("공연이 없습니다.");
    }

    @Test
    @DisplayName("좌석 자체를 잘못 예약하는 경우")
    public void 좌석_자체를_잘못_예약하는_경우() {
        // given
        // 좌석이 없는 오류만 찾으면 되니까 공연에 대한 좌석은 저장하지 않는다!
        Performance performance = getSamplePerformance(PERFORMANCE_NAME, 20000, 1, 0, LocalDateTime.now(), "enable"); // 게르테나 미술관 전시회 정보
        Performance savedPerformance = performanceRepository.save(performance);

        // when
        ReservationRequest request = getReservationRequest(savedPerformance.getId(), savedPerformance.getName(), 100000);

        // then
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.reserve(request));
        Assertions.assertThat(exception.getMessage()).isEqualTo("해당 좌석이 없습니다.");
    }

    @Test
    @DisplayName("예약 실패 테스트(이미 예약된 자리를 예약하려는 상황)")
    public void 예약_실패_테스트_이미_예약된_자리를_예약하려는_상황() {
        // given
        Performance performance = getSamplePerformance(PERFORMANCE_NAME, 20000, 1, 0, LocalDateTime.now(), "enable"); // 게르테나 미술관 전시회 정보
        Performance savedPerformance = performanceRepository.save(performance);

        for (int i = 1; i < 5; i++) {
            PerformanceSeatInfo seatInfo = getSampleSeat(savedPerformance, i);
            performanceSeatInfoRepository.save(seatInfo);
            performance.reserveSeat(seatInfo);
        }

        // when
        ReservationRequest request = getReservationRequest(savedPerformance.getId(), savedPerformance.getName(), 100000);
        reservationService.reserve(request); // 첫 예약

        // then
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserve(request));// 이미 예약된 자리니까 예외처리가 나와야 한다.
        Assertions.assertThat(exception.getMessage()).isEqualTo("해당 좌석은 이미 예약 완료되었습니다");
    }

    @Test
    @DisplayName("잔고가 부족한 경우")
    public void 잔고가_부족한_경우() {
        // given
        Performance performance = getSamplePerformance(PERFORMANCE_NAME, 20000, 1, 0, LocalDateTime.now(), "enable"); // 게르테나 미술관 전시회 정보
        Performance savedPerformance = performanceRepository.save(performance);

        for (int i = 1; i < 5; i++) {
            PerformanceSeatInfo seatInfo = getSampleSeat(savedPerformance, i);
            performanceSeatInfoRepository.save(seatInfo);
            performance.reserveSeat(seatInfo);
        }

        // when
        ReservationRequest request = getReservationRequest(savedPerformance.getId(), savedPerformance.getName(), 10);

        // then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> reservationService.reserve(request));
        Assertions.assertThat(exception.getMessage()).isEqualTo("잔액이 부족합니다.");
    }

    @Test
    @DisplayName("예약 정보를 가져온다.")
    public void 예약_정보를_가져온다() throws Exception {
        // given
        Performance performance = getSamplePerformance(PERFORMANCE_NAME, 20000, 1, 0, LocalDateTime.now(), "enable"); // 게르테나 미술관 전시회 정보
        Performance savedPerformance = performanceRepository.save(performance);

        for (int i = 1; i < 5; i++) {
            PerformanceSeatInfo seatInfo = getSampleSeat(savedPerformance, i);
            performanceSeatInfoRepository.save(seatInfo);
            performance.reserveSeat(seatInfo);
        }

        ReservationRequest request = getReservationRequest(savedPerformance.getId(), savedPerformance.getName(), 100000);
        reservationService.reserve(request);

        // when
        ReservationUserInfo usrInfo = new ReservationUserInfo();
        usrInfo.setPhoneNumber(PHONE_NUMBER);
        usrInfo.setUserName(USER_NAME);

        List<ReservationResponse> reservationResponses = reservationService.reservationList(usrInfo);

        // then
        Assertions.assertThat(reservationResponses.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("첫 결제 할인 테스트")
    public void 첫_결제_할인_테스트() {
        // given
        Performance performance = getSamplePerformance(PERFORMANCE_NAME, 20000, 1, 0, LocalDateTime.now(), "enable"); // 게르테나 미술관 전시회 정보
        Performance savedPerformance = performanceRepository.save(performance);

        for (int i = 1; i < 5; i++) {
            PerformanceSeatInfo seatInfo = getSampleSeat(savedPerformance, i);
            performanceSeatInfoRepository.save(seatInfo);
            performance.reserveSeat(seatInfo);
        }

        // when
        ReservationRequest request = getReservationRequest(savedPerformance.getId(), savedPerformance.getName(), 100000);
        reservationService.reserve(request);

        // then
        // 첫 결제라 10퍼 할인 -> 18000원만 결제
        Assertions.assertThat(request.getBalance()).isEqualTo(82000);
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

    /**
     * 예약 Fixture 생성
     */
    private ReservationRequest getReservationRequest(UUID performanceId, String performanceName, long balance) {
        ReservationRequest request = new ReservationRequest();
        request.setPerformId(performanceId);
        request.setPerformName(performanceName);
        request.setGate(1);
        request.setLine("A");
        request.setSeat(1);
        request.setRound(1);
        request.setBalance(balance);
        request.setUserName(USER_NAME);
        request.setPhoneNumber(PHONE_NUMBER);

        return request;
    }
}