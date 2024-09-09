package com.wanted.preonboarding.model.seat;

import com.wanted.preonboarding.place.model.Place;
import com.wanted.preonboarding.place.repository.PlaceRepository;
import com.wanted.preonboarding.seat.dto.EnrollInfo;
import com.wanted.preonboarding.seat.model.Seat;
import com.wanted.preonboarding.seat.service.SeatService;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class SeatBasicTest {

    @Autowired
    private SeatService seatService;
    @Autowired
    private PlaceRepository placeRepository;

    @Test
    @DisplayName("좌석을 등록하는 테스트")
    public void 좌석을_등록하는_테스트() throws Exception {
        // given
        Place savedPlace = placeRepository.save(makeTestPlace());

        // when
        EnrollInfo info = new EnrollInfo();
        info.setSeats(5);
        info.setLines(5);
        seatService.enrollSeats(savedPlace.getId(), info);

        // then
        List<Seat> byPlaceId = seatService.findByPlaceId(savedPlace.getId());
        Assertions.assertThat(byPlaceId.size()).isEqualTo(25);
    }

    /**
     * 장소 Fixture 생성
     */
    public Place makeTestPlace() {
        return new Place(9999L, "옥천", "옥천", "010-0000-1234");
    }
}
