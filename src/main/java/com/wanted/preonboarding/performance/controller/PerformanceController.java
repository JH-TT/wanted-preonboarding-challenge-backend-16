package com.wanted.preonboarding.performance.controller;

import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import com.wanted.preonboarding.performance.model.Performance;
import com.wanted.preonboarding.performance.service.PerformanceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping()
    public ResponseEntity<List<PerformanceInfo>> getList() {
        return ResponseEntity.ok(performanceService.getList());
    }
}
