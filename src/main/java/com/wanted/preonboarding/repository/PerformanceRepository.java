package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.model.Performance;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, UUID> {

}
