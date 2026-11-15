package com.example.rental.payment;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentSimulator {

    // 시뮬레이션 응답 지연의 최솟값
    private static final long MIN_DELAY_MILLIS = 500L;
    // 시뮬레이션 응답 지연의 최댓값
    private static final long MAX_DELAY_MILLIS = 2_000L;
    // 시뮬레이션 결제의 성공 확률
    private static final int SUCCESS_PERCENTAGE = 90;

    public PaymentSimulationResult simulate(BigDecimal amount) {
        if (amount == null || amount.signum() < 0) {
            throw new IllegalArgumentException("결제 금액을 확인해라.");
        }

        long delayMillis = ThreadLocalRandom.current()
                .nextLong(MIN_DELAY_MILLIS, MAX_DELAY_MILLIS + 1);

        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("결제 시뮬레이션이 중단됐다.", exception);
        }

        boolean successful = ThreadLocalRandom.current()
                .nextInt(100) < SUCCESS_PERCENTAGE;
        String message = successful ? "결제에 성공했다." : "결제에 실패했다.";

        return new PaymentSimulationResult(amount, successful, delayMillis, message);
    }
}