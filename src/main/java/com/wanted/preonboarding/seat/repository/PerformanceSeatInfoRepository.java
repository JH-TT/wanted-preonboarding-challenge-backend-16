package com.wanted.preonboarding.seat.repository;

import com.wanted.preonboarding.seat.model.Seat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceSeatInfoRepository extends JpaRepository<Seat, Integer> {

    @Query("SELECT s FROM Seat s WHERE s.placeId.id = :placeId")
    List<Seat> findByPlaceId(@Param("placeId") Long placeId);
}
