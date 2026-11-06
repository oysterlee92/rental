package com.example.rental.car;

import com.example.rental.reservation.ReservationRepository;
import com.example.rental.reservation.ReservationStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class JpaCarSearchService implements CarSearchService {

    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public JpaCarSearchService(CarRepository carRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<CarSummary> searchAvailable(LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);

        return carRepository.findAvailableCars(startDate, endDate,
                        ReservationStatus.occupyingCarStatuses())
                .stream()
                .map(this::toSummary)
                .toList();
    }

    @Override
    public Optional<CarSummary> findAvailableCar(Long carId, LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);

        return carRepository.findById(carId)
                .filter(Car::isActive)
                .filter(car -> reservationRepository.countOverlappingReservations(
                        carId, startDate, endDate,
                        ReservationStatus.occupyingCarStatuses()) == 0)
                .map(this::toSummary);
    }

    private CarSummary toSummary(Car car) {
        return new CarSummary(
                car.getId(),
                car.getModelName(),
                car.getCarNumber(),
                car.getDailyPrice());
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("대여 기간을 확인해라.");
        }
    }
}