package com.example.rental.car;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarSearchService {

    List<CarSummary> searchAvailable(LocalDate startDate, LocalDate endDate);

    Optional<CarSummary> findAvailableCar(
            Long carId,
            LocalDate startDate,
            LocalDate endDate
    );
}
