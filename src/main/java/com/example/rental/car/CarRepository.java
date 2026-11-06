package com.example.rental.car;

import com.example.rental.reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("""
            select c
            from Car c
            where c.active = true
              and not exists (
                  select r.id
                  from Reservation r
                  where r.car = c
                    and r.status in :statuses
                    and r.startDate <= :endDate
                    and r.endDate >= :startDate
              )
            """)
    List<Car> findAvailableCars(@Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate,
                                @Param("statuses") List<ReservationStatus> statuses);
}