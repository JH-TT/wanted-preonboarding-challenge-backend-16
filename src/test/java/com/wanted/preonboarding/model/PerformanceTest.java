//package com.wanted.preonboarding.model;
//
//import com.wanted.preonboarding.Enum.PerformanceStatus;
//import com.wanted.preonboarding.reservation.dto.ReservationRequest;
//import com.wanted.preonboarding.seat.dto.PerformanceSeat;
//import com.wanted.preonboarding.performance.model.Performance;
//import com.wanted.preonboarding.seat.model.PerformanceSeatInfo;
//import com.wanted.preonboarding.reservation.model.Reservation;
//import com.wanted.preonboarding.performance.repository.PerformanceRepository;
//import com.wanted.preonboarding.seat.repository.PerformanceSeatInfoRepository;
//import com.wanted.preonboarding.reservation.repository.ReservationRepository;
//import com.wanted.preonboarding.performance.service.PerformanceService;
//import com.wanted.preonboarding.reservation.service.ReservationService;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//class PerformanceTest {
//
//    @Autowired
//    private PerformanceRepository performanceRepository;
//    @Autowired
//    private ReservationRepository reservationRepository;
//    @Autowired
//    private ReservationService reservationService;
//    @Autowired
//    private PerformanceService performanceService;
//    @Autowired
//    private PerformanceSeatInfoRepository performanceSeatInfoRepository;
//    @PersistenceContext
//    private EntityManager em;
//    private static final String PERFORMANCE_NAME = "게르테나 미술관";
//    private static final String USER_NAME = "이종호";
//    private static final String PHONE_NUMBER = "010-1234-5678";
//    @Test
//    @DisplayName("공연 성공 테스트")
//    public void 공연_테스트() throws Exception {
//        // given
//        Performance performance = getSamplePerformance(PERFORMANCE_NAME, 20000, 1, 0, LocalDateTime.now(), PerformanceStatus.ACTIVE); // 게르테나 미술관 전시회 정보
//        Performance savedPerformance = performanceRepository.save(performance);
//
//        Optional<Performance> byId = performanceRepository.findById(savedPerformance.getId());
//        System.out.println(byId.get());
//    }
//
//    @Test
//    @DisplayName("공연 취소로 인한 예약환불")
//    public void 공연_취소로_인한_예약환불() throws Exception {
//        // given
//        Performance performance = getSamplePerformance(PERFORMANCE_NAME, 20000, 1, 0, LocalDateTime.now().plusDays(1), PerformanceStatus.ACTIVE); // 게르테나 미술관 전시회 정보
//        Performance savedPerformance = performanceRepository.save(performance);
//
//        for (int i = 1; i < 5; i++) {
//            PerformanceSeatInfo seatInfo = getSampleSeat(savedPerformance, i);
//            performanceSeatInfoRepository.save(seatInfo);
//            performance.registerSeat(seatInfo);
//        }
//
//        // 2개의 예약이 있다고 가정
//        for (int i = 1; i < 3; i++) {
//            ReservationRequest request = getSpecificReservationRequest(savedPerformance.getId(),
//                    savedPerformance.getName(), 100000, 1, "A", i);
//
//            reservationService.reserve(request);// 물론 여기서도 오류가 나오면 안됨
//        }
//
//        List<Reservation> reservations = reservationRepository.findByPerformanceId(savedPerformance.getId());
//        Reservation reservation1 = reservations.get(0);
//        Reservation reservation2 = reservations.get(1);
//
//        System.out.println("==============공연 취소 전====================");
//        System.out.println("reservation1 : " + reservation1);
//        System.out.println();
//        System.out.println("reservation2 : " + reservation2);
//        System.out.println("==============공연 취소 후====================");
//
//        // when
//        performanceService.performanceCancel(savedPerformance.getId());
//
//        Reservation recallReservation1 = reservationRepository.findById(reservation1.getId()).get();
//        Reservation recallReservation2 = reservationRepository.findById(reservation2.getId()).get();
//        System.out.println("recallReservation1 : " + recallReservation1);
//        System.out.println();
//        System.out.println("recallReservation2 : " + recallReservation2);
//
//        // then
//        Performance performance1 = performanceRepository.findByIdNotNull(performance.getId())
//                .orElseThrow(IllegalArgumentException::new);
//        System.out.println(performance1);
//        Assertions.assertThat(performance1.getDeletedAt()).isNotNull();
//        Assertions.assertThat(recallReservation1.getRefundSeats().length()).isGreaterThan(0);
//        Assertions.assertThat(recallReservation2.getRefundSeats().length()).isGreaterThan(0);
//    }
//
//    /**
//     * 공연 Fixture 생성
//     * @param performanceName 게르테나 미술관
//     * @param price 20000
//     * @param round 1
//     * @param type 0
//     * @param startDate 현재시간
//     * @param isReserve enable
//     * @return Performance
//     */
//    private Performance getSamplePerformance(String performanceName, int price, int round, int type, LocalDateTime startDate, PerformanceStatus isReserve) {
//        return Performance.builder()
//                .name(performanceName)
//                .price(price)
//                .round(round) // 1회차
//                .type(type)  // 0: 전시회, 1: 공연
//                .startDate(startDate)
//                .status(isReserve)
//                .build();
//    }
//
//    /**
//     * 공연 좌석 Fixture 생성
//     * @param performance 게르테나 미술관
//     * @param seat 좌석 열
//     * @return PerformanceSeatInfo
//     */
//    private PerformanceSeatInfo getSampleSeat(Performance performance, int seat) {
//        return PerformanceSeatInfo.builder()
//                .seat(seat)
//                .performance(performance)
//                .round(1)
//                .isReserve("enable")
//                .gate(1)
//                .line("A")
//                .build();
//    }
//
//    /**
//     * 예약 Fixture 생성
//     */
//    private ReservationRequest getReservationRequest(Long performanceId, String performanceName, long balance, boolean more) {
//        ArrayList<PerformanceSeat> seatList = new ArrayList<>();
//        PerformanceSeat performanceSeat = new PerformanceSeat(1, "A", 1, "enable");
//        seatList.add(performanceSeat);
//        if (more) {
//            PerformanceSeat performanceSeat2 = new PerformanceSeat(1, "A", 2, "enable");
//            seatList.add(performanceSeat2);
//
//            PerformanceSeat performanceSeat3 = new PerformanceSeat(1, "A", 3, "enable");
//            seatList.add(performanceSeat3);
//        }
//
//        ReservationRequest request = new ReservationRequest();
//        request.setPerformId(performanceId);
//        request.setPerformName(performanceName);
//        request.setSeatList(seatList);
//        request.setRound(1);
//        request.setBalance(balance);
//        request.setUserName(USER_NAME);
//        request.setPhoneNumber(PHONE_NUMBER);
//
//        return request;
//    }
//
//    /**
//     * 특정 좌석 예약 Fixture (1개의 좌석만 예약하는 경우)
//     */
//    private ReservationRequest getSpecificReservationRequest(Long performanceId, String performanceName, long balance, int gate, String line, int seat) {
//        ArrayList<PerformanceSeat> seatList = new ArrayList<>();
//        PerformanceSeat performanceSeat = new PerformanceSeat(gate, line, seat, "enable");
//        seatList.add(performanceSeat);
//
//        ReservationRequest request = new ReservationRequest();
//        request.setPerformId(performanceId);
//        request.setPerformName(performanceName);
//        request.setSeatList(seatList);
//        request.setRound(1);
//        request.setBalance(balance);
//        request.setUserName(USER_NAME);
//        request.setPhoneNumber(PHONE_NUMBER);
//
//        return request;
//    }
//}