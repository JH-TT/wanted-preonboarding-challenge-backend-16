package com.wanted.preonboarding.dto.performance;

import lombok.Data;

/**
 * 회차랑 이름만 받는다.
 */
@Data
public class PerformanceInfoRequest {

    private String name;
    private int round;
}
