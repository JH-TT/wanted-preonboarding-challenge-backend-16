package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.model.performance.Performance;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, UUID> {

    @Query(value = "select p from Performance p where p.name = :name and p.round = :round")
    Optional<Performance> getPerformanceByNameAndRound(@Param("name") String name, @Param("round") int round);

    @Query(value = "select p from Performance p where p.id = :id and p.deletedAt is null")
    Optional<Performance> findById(@Param("id") UUID id);

    @Query(value = "select p from Performance p where p.id = :id")
    Optional<Performance> findByIdNotNull(@Param("id") UUID id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update Performance p set p.deletedAt = :now, p.isReserve = 'disable' where p.id = :id")
    void performanceCancel(@Param("id") UUID id, @Param("now") LocalDateTime now);

    // time-low와 time-high를 바꿔서 조회한다.
//    @Query(value = "select * from performance where id = uuid_to_bin(:id, 1)", nativeQuery = true)
//    Optional<Performance> findById(@Param("id") String id);
}
