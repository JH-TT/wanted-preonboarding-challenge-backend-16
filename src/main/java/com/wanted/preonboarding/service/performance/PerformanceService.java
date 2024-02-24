package com.wanted.preonboarding.service.performance;

import com.wanted.preonboarding.dto.performance.PerformanceInfoRequest;
import com.wanted.preonboarding.dto.seat.PerformanceSeat;
import java.util.List;

public interface PerformanceService {
    List<PerformanceSeat> getSeats(PerformanceInfoRequest request);
}
