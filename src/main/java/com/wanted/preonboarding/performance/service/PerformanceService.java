package com.wanted.preonboarding.performance.service;

import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import com.wanted.preonboarding.performance.model.Performance;
import com.wanted.preonboarding.performance.repository.PerformanceRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    public List<PerformanceInfo> getList() {
        return performanceRepository.findAll().stream().map(PerformanceInfo::of)
                .collect(Collectors.toList());
    }
}
