package com.example.rental.car;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DemoCarSearchService implements CarSearchService{

    // 화면 흐름을 확인하기 위한 임시 차량 목록
    private final List<CarSummary> cars = List.of(
            new CarSummary(1L, "아반떼", "12가 3456", new BigDecimal("55000")),
            new CarSummary(2L, "쏘나타", "23나 4567", new BigDecimal("75000")),
            new CarSummary(3L, "그랜저", "34다 5678", new BigDecimal("110000"))
    );

    @Override
    public List<CarSummary> searchAvailable(LocalDate startDate, LocalDate endDate) {
        return cars;
    }

    @Override
    public Optional<CarSummary> findAvailableCar(
            Long carId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return cars.stream()
                .filter(car -> car.getId().equals(carId))
                .findFirst();
    }
}
