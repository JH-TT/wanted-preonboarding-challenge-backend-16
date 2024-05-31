package com.wanted.preonboarding.performance.controller;

import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import com.wanted.preonboarding.performance.dto.PerformanceInfoRequest;
import com.wanted.preonboarding.seat.dto.PerformanceSeat;
import com.wanted.preonboarding.performance.model.Performance;
import com.wanted.preonboarding.performance.repository.PerformanceRepository;
import com.wanted.preonboarding.performance.service.PerformanceService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("performance")
public class PerformanceController {

    private final PerformanceRepository performanceRepository;
    private final PerformanceService performanceService;

    // 현재 공연, 전시회 목록을 리턴한다.
    @GetMapping()
    public ResponseEntity<List<PerformanceInfo>> getList() {
        log.info("start method : getList()");
        List<Performance> all = performanceRepository.findAll();
        return ResponseEntity.ok(
                all.stream().map(PerformanceInfo::of).collect(Collectors.toList())
        );
    }

    // 특정 공연과 회차를 입력 받으면 좌석 정보를 보여준다.
    @GetMapping("/seats")
    public List<PerformanceSeat> getSeatList(@RequestBody PerformanceInfoRequest request) {
        log.info("start method : getSeatList");
        log.info("request : {}", request);
        // 해당 정보를 Service로 넘기고 좌석정보를 받는다.
        return performanceService.getSeats(request);
    }

    // 공연이 취소되는 경우
    @DeleteMapping("/{id}")
    public void performanceCancel(@PathVariable("id") Long performanceId) {
        log.info("start method : performanceCancel");
        log.info("performance Id : " + performanceId);
        performanceService.performanceCancel(performanceId);
    }

    // 공연 추가
    @PostMapping()
    public void addPerformance(@RequestBody PerformanceInfo performanceInfo) {
        performanceService.add(performanceInfo);
    }
}
