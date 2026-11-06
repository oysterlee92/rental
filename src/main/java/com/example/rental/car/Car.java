package com.example.rental.car;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "cars")
public class Car {

    // 차량을 구분하는 기본 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 차량 모델명
    @Column(name = "model_name", nullable = false, length = 100)
    private String modelName;

    // 중복 등록을 허용하지 않는 차량 번호
    @Column(name = "car_number", nullable = false, unique = true, length = 20)
    private String carNumber;

    // 하루 대여료
    @Column(name = "daily_price", nullable = false, precision = 12, scale = 0)
    private BigDecimal dailyPrice;

    // 검색 가능한 차량인지 나타내는 값
    @Column(nullable = false)
    private boolean active;

    protected Car() {
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

    public boolean isActive() {
        return active;
    }
}