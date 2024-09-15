package com.wanted.preonboarding.seat.service;

import com.wanted.preonboarding.place.model.Place;
import com.wanted.preonboarding.place.repository.PlaceRepository;
import com.wanted.preonboarding.seat.dto.EnrollInfo;
import com.wanted.preonboarding.seat.dto.SeatInfo;
import com.wanted.preonboarding.seat.model.Seat;
import com.wanted.preonboarding.seat.repository.PerformanceSeatInfoRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeatService {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public List<SeatInfo> enrollSeats(Long placeId, EnrollInfo info) {
        Place place = placeRepository.findById(placeId).orElseThrow(
                () -> new IllegalArgumentException("Place not found\n Place Id=" + placeId)
        );
        List<Seat> seatList = new ArrayList<>();
        for (int i = 0; i < info.getLines(); i++) {
            String line = String.valueOf(ALPHABET.charAt(i));
            for (int j = 1; j <= info.getSeats(); j++) {
                Seat seat = Seat.of(line, j);
                seat.enrollPlace(place);
                seatList.add(seat);
            }
        }
        return performanceSeatInfoRepository.saveAll(seatList).stream()
                .map(SeatInfo::of).toList();
    }

    public List<SeatInfo> findByPlaceId(Long placeId) {
        return performanceSeatInfoRepository.findByPlaceId(placeId).stream()
                .map(SeatInfo::of).toList();
    }
}
