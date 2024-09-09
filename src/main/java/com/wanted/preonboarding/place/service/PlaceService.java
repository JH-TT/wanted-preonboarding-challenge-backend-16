package com.wanted.preonboarding.place.service;

import com.wanted.preonboarding.exception.DetailedException;
import com.wanted.preonboarding.exception.DuplicateDataException;
import com.wanted.preonboarding.place.dto.PlaceInfo;
import com.wanted.preonboarding.place.model.Place;
import com.wanted.preonboarding.place.repository.PlaceRepository;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
    public void create(PlaceInfo placeInfo) {
        placeRepository.save(Place.toEntity(placeInfo));
    }

    public PlaceInfo getPlace(Long id) {
        return placeRepository.findByPlaceId(id);
    }
}
