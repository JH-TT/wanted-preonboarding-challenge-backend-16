package com.wanted.preonboarding.service.performance;

import com.wanted.preonboarding.dto.performance.PerformanceInfoRequest;
import com.wanted.preonboarding.dto.seat.PerformanceSeat;
import com.wanted.preonboarding.model.performance.Performance;
import com.wanted.preonboarding.repository.PerformanceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceServiceImpl implements PerformanceService {
    private final PerformanceRepository performanceRepository;

    @Override
    public List<PerformanceSeat> getSeats(PerformanceInfoRequest request) {
        Optional<Performance> performance = performanceRepository.getPerformanceByNameAndRound(request.getName(),
                request.getRound());
        return performance.map(value -> value.getSeats().stream()
                        .map(PerformanceSeat::of)
                        .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }
}
