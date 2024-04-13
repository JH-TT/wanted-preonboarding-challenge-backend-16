package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.model.performance.Performance;
import com.wanted.preonboarding.model.performance.PerformanceSeatInfo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {

    @Query(value = "select s from PerformanceSeatInfo s where s.performance = :id and s.gate = :gate and s.line = :line and s.seat = :seat")
    Optional<PerformanceSeatInfo> findPerformanceSeatInfo(@Param("id") Performance id, @Param("gate") int gate, @Param("line") String line, @Param("seat") int seat);

    @Modifying
    @Query(value = "UPDATE PerformanceSeatInfo s SET s.isReserve = 'enable', s.reservationId = null WHERE s.reservationId = :id")
    void deleteByReservationId(@Param("id") int id);
}
