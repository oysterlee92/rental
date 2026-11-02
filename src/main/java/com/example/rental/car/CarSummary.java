package com.example.rental.car;

import java.math.BigDecimal;

public class CarSummary {

    // 차량을 구분하는 ID
    private final Long id;
    // 화면에 표시할 차량 모델명
    private final String modelName;
    // 화면에 표시할 차량 번호
    private final String carNumber;
    // 차량의 1일 대여료
    private final BigDecimal dailyPrice;

    public CarSummary(Long id, String modelName, String carNumber, BigDecimal dailyPrice) {
        this.id = id;
        this.modelName = modelName;
        this.carNumber = carNumber;
        this.dailyPrice = dailyPrice;
    }

    public Long getId() {
        return id;
    }

    public String getModelName() {
        return modelName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }
}
