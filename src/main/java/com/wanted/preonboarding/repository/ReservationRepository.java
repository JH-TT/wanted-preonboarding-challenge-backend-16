package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.Enum.ReservationStatus;
import com.wanted.preonboarding.model.reservation.Reservation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("select r from Reservation r where r.name = :userName and r.phoneNumber = :phoneNumber")
    List<Reservation> findAllByUserNameAndPhoneNumber(@Param("userName") String userName,
                                                      @Param("phoneNumber") String phoneNumber);
    Optional<Reservation> findById(Long id);
    long countByNameAndPhoneNumber(String name, String phoneNumber);

    @Modifying
    @Query("UPDATE Reservation r SET r.status = :status, r.deletedAt = :deleteTime WHERE r.id = :id")
    void softDeleteById(@Param("status") ReservationStatus status,
                        @Param("deleteTime") LocalDateTime deleteTime,
                        @Param("id") Long id);
}
