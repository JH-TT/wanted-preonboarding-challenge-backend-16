package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.model.performance.Performance;
import com.wanted.preonboarding.model.performance.PerformanceSeatInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {

    @Query(value = "select s from PerformanceSeatInfo s where s.performance = :id and s.gate = :gate and s.line = :line and s.seat = :seat and s.isReserve = 'enable'")
    Optional<PerformanceSeatInfo> findPerformanceSeatInfo(@Param("id") Performance id, @Param("gate") int gate, @Param("line") String line, @Param("seat") int seat);
}
