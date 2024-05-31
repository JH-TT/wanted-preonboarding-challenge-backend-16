package com.wanted.preonboarding.performance.service;

import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import com.wanted.preonboarding.performance.dto.PerformanceInfoRequest;
import com.wanted.preonboarding.seat.dto.PerformanceSeat;
import java.util.List;

public interface PerformanceService {
    List<PerformanceSeat> getSeats(PerformanceInfoRequest request);

    void performanceCancel(Long id);

    void add(PerformanceInfo performanceInfo);
}
