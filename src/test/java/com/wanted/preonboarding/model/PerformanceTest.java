package com.wanted.preonboarding.model;

import com.wanted.preonboarding.model.performance.Performance;
import com.wanted.preonboarding.model.performance.PerformanceSeatInfo;
import com.wanted.preonboarding.repository.PerformanceRepository;
import com.wanted.preonboarding.repository.PerformanceSeatInfoRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PerformanceTest {

    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @Autowired
    PerformanceTest(PerformanceRepository performanceRepository,
                    PerformanceSeatInfoRepository performanceSeatInfoRepository) {
        this.performanceRepository = performanceRepository;
        this.performanceSeatInfoRepository = performanceSeatInfoRepository;
    }

    @Test
    @Transactional
    public void 공연좌석_테스트() throws Exception {
        // given
        Performance performance = Performance.builder()
                .isReserve("a")
                .name("하이")
                .price(1000)
                .round(1)
                .type(1)
                .startDate(LocalDateTime.now())
                .build();

        // 공연좌석 정보를 저장한다.
        PerformanceSeatInfo seat = new PerformanceSeatInfo(1, performance, 1, 2, "a", 3);
        performance.reserveSeat(seat);

        UUID uuid = performanceRepository.save(performance).getId();
        performanceSeatInfoRepository.save(seat);

        Performance performance1 = performanceRepository.findById(uuid).get();
        System.out.println(performance1.getReserveSeat());
    }
}