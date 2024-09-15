package com.wanted.preonboarding.model.seat;

import com.wanted.preonboarding.place.model.Place;
import com.wanted.preonboarding.place.repository.PlaceRepository;
import com.wanted.preonboarding.seat.dto.EnrollInfo;
import com.wanted.preonboarding.seat.dto.SeatInfo;
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
        EnrollInfo enrollInfo = new EnrollInfo();
        enrollInfo.setLines(5); // 5개의 행
        enrollInfo.setSeats(5); // 5개의 열

        // then
        List<SeatInfo> seatInfos = seatService.enrollSeats(savedPlace.getId(), enrollInfo);
        List<SeatInfo> byPlaceId = seatService.findByPlaceId(savedPlace.getId());

        Assertions.assertThat(seatInfos.size()).isEqualTo(byPlaceId.size());
        Assertions.assertThat(byPlaceId).allMatch(seat -> seat.getPlaceId().equals(savedPlace.getId()));

        /**
         * WebmvcTest로는 딱 단순하게 요청보내고 응답이 어떤지 확인용인듯 하다. (컨트롤러 단만 테스트할 용도)
         * Mock으로만 대체를 해서 그런가 안되는게 많고, 무엇보다 DB와 연결이 안된다는게 큰 단점같다.
         * 데이터까지 확인하려면 SpringbootTest가 맞는듯 하다.
         *
         * 확인해 보니 repository 테스트를 진행하고 싶으면 DataJpaTest어노테이션을 이용하면 되는거 같다.
         */
    }

    /**
     * 장소 Fixture 생성
     */
    public Place makeTestPlace() {
        /* return new Place(9999L, "옥천", "옥천", "010-0000-1234"); */
        /* builder 패턴을 이용해서 가독성을 높임 */
        return Place.builder()
                .id(Long.MAX_VALUE)
                .name("옥천")
                .address("옥천")
                .phoneNumber("010-0000-1234")
                .build();
    }
}
