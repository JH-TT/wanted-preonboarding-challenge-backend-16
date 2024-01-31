package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.model.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {
}
