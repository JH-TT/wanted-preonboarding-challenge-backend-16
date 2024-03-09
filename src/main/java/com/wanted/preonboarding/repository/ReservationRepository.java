package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.model.reservation.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("select r from Reservation r where r.name = :userName and r.phoneNumber = :phoneNumber")
    List<Reservation> findAllByUserNameAndPhoneNumber(@Param("userName") String userName,
                                                      @Param("phoneNumber") String phoneNumber);
}
