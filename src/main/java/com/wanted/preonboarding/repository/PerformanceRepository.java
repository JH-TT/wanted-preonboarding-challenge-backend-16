package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.model.performance.Performance;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, UUID> {

    @Query(value = "select p from Performance p where p.name = :name and p.round = :round")
    Optional<Performance> getPerformanceByNameAndRound(@Param("name") String name, @Param("round") int round);

    // time-low와 time-high를 바꿔서 조회한다.
    @Query(value = "select * from performance where id = uuid_to_bin(:id, 1)", nativeQuery = true)
    Optional<Performance> findById(@Param("id") String id);
}
