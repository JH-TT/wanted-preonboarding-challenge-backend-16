package com.wanted.preonboarding.service.performance;

import com.wanted.preonboarding.dto.performance.PerformanceInfoRequest;
import com.wanted.preonboarding.dto.seat.PerformanceSeat;
import java.util.List;
import java.util.UUID;

public interface PerformanceService {
    List<PerformanceSeat> getSeats(PerformanceInfoRequest request);

    void performanceCancel(UUID id);
}
