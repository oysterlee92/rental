package com.example.rental.payment;

import java.math.BigDecimal;

public class PaymentSimulationResult {

    // 시뮬레이션에 사용한 결제 금액
    private final BigDecimal amount;
    // 결제 시뮬레이션 성공 여부
    private final boolean successful;
    // 외부 결제 응답을 흉내 내며 대기한 시간
    private final long delayMillis;
    // 화면에 표시할 처리 결과
    private final String message;

    public PaymentSimulationResult(BigDecimal amount, boolean successful,
                                   long delayMillis, String message) {
        this.amount = amount;
        this.successful = successful;
        this.delayMillis = delayMillis;
        this.message = message;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public long getDelayMillis() {
        return delayMillis;
    }

    public String getMessage() {
        return message;
    }
}