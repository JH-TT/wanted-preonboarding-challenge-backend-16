package com.wanted.preonboarding.place.service;

import com.wanted.preonboarding.place.dto.PlaceInfo;
import com.wanted.preonboarding.place.model.Place;
import com.wanted.preonboarding.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        PlaceInfo byPlaceId = placeRepository.findByPlaceId(id);
        if (byPlaceId == null) {
            throw new IllegalArgumentException("등록되지 않은 장소입니다. 장소아이디: " + id);
        }
        return byPlaceId;
    }
}
