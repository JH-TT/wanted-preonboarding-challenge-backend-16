package com.wanted.preonboarding.seat.repository;

import com.wanted.preonboarding.performance.model.Performance;
import com.wanted.preonboarding.seat.model.Seat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceSeatInfoRepository extends JpaRepository<Seat, Integer> {
}
