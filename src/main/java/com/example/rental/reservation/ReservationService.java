package com.example.rental.reservation;

import com.example.rental.car.Car;
import com.example.rental.car.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class ReservationService {

    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(CarRepository carRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Long createReservation(Long carId, LocalDate startDate, LocalDate endDate,
                                  String renterName, String renterPhone) {
        validateInput(startDate, endDate, renterName, renterPhone);

        Car car = carRepository.findById(carId)
                .filter(Car::isActive)
                .orElseThrow(() -> new IllegalStateException("예약할 수 없는 차량이다."));

        long overlappingCount = reservationRepository.countOverlappingReservations(
                carId, startDate, endDate, ReservationStatus.occupyingCarStatuses());

        if (overlappingCount > 0) {
            throw new IllegalStateException("선택한 기간에 이미 예약된 차량이다.");
        }

        long rentalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        BigDecimal totalAmount = car.getDailyPrice()
                .multiply(BigDecimal.valueOf(rentalDays));

        Reservation reservation = new Reservation(car, renterName.trim(), renterPhone.trim(),
                startDate, endDate, totalAmount);

        return reservationRepository.save(reservation).getId();
    }

    private void validateInput(LocalDate startDate, LocalDate endDate, String renterName, String renterPhone) {
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("대여 기간을 확인해라.");
        }
        if (renterName == null || renterName.isBlank() || renterName.length() > 50) {
            throw new IllegalArgumentException("예약자 이름을 확인해라.");
        }
        if (renterPhone == null || renterPhone.isBlank() || renterPhone.length() > 20) {
            throw new IllegalArgumentException("연락처를 확인해라.");
        }
    }
}