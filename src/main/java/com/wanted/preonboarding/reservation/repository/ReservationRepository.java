package com.wanted.preonboarding.reservation.repository;

import com.wanted.preonboarding.reservation.model.Reservation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("select r from Reservation r where r.name = :userName and r.phoneNumber = :phoneNumber and r.deletedAt is null")
    List<Reservation> findAllByUserNameAndPhoneNumber(@Param("userName") String userName,
                                                      @Param("phoneNumber") String phoneNumber);

    @Query("select r from Reservation r where r.id = :id and r.deletedAt is null")
    Optional<Reservation> findByIdNotNull(@Param("id") Long id);

    @Query("select r from Reservation r where r.id = :id")
    Optional<Reservation> findById(@Param("id") Long id);

    @Query("select r from Reservation r where r.performanceId = :performanceId")
    List<Reservation> findByPerformanceId(@Param("performanceId") Long performanceId);

    // 첫 결제인지 알아보는 쿼리인데 신청했다가 취소한 경우는 첫결제 혜택을 다시 받지 못한다.
    @Query("select count(r) from Reservation r where r.name = :name and r.phoneNumber = :phoneNumber")
    long countByNameAndPhoneNumber(@Param("name") String name,
                                   @Param("phoneNumber") String phoneNumber);
}
