package com.wanted.preonboarding.place.repository;

import com.wanted.preonboarding.place.dto.PlaceInfo;
import com.wanted.preonboarding.place.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query("SELECT new com.wanted.preonboarding.place.dto.PlaceInfo(p.name, p.address, p.phoneNumber) from Place p where p.id = :id")
    PlaceInfo findByPlaceId(@Param("id") Long id);
}
