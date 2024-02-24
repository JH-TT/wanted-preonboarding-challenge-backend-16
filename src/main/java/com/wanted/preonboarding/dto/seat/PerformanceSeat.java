package com.wanted.preonboarding.dto.seat;


import com.wanted.preonboarding.model.performance.PerformanceSeatInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 보통 좌석은 각 영화의 각 회차별로 나오기 때문에
 * 여기서는 gate, line, seat만 가져온다. (어차피 고객 입장에서는 영화와 회차까지 알고 있기 때문)
 */
@Getter
@Setter
@Builder
public class PerformanceSeat {

    private int gate;
    private String line;
    private int seat;
    private String isReserve;

    public static PerformanceSeat of(PerformanceSeatInfo performanceSeatInfo) {
        return PerformanceSeat.builder()
                .gate(performanceSeatInfo.getGate())
                .line(performanceSeatInfo.getLine())
                .seat(performanceSeatInfo.getSeat())
                .isReserve(performanceSeatInfo.getIsReserve())
                .build();
    }
}
