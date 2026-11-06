package com.example.rental.reservation;

import com.example.rental.car.Car;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    // 예약을 구분하는 기본 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 여러 예약이 하나의 차량을 참조하는 관계
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    // 예약자 이름
    @Column(name = "renter_name", nullable = false, length = 50)
    private String renterName;

    // 예약자 연락처
    @Column(name = "renter_phone", nullable = false, length = 20)
    private String renterPhone;

    // 대여 시작일
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    // 반납 예정일
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    // 예약 시점에 계산해 저장한 총 대여료
    @Column(name = "total_amount", nullable = false, precision = 12, scale = 0)
    private BigDecimal totalAmount;

    // 결제와 대여 진행 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ReservationStatus status;

    // 예약이 생성된 시각
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Reservation() {
    }

    public Reservation(Car car, String renterName, String renterPhone,
                       LocalDate startDate, LocalDate endDate, BigDecimal totalAmount) {
        this.car = car;
        this.renterName = renterName;
        this.renterPhone = renterPhone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
        this.status = ReservationStatus.PAYMENT_PENDING;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public ReservationStatus getStatus() {
        return status;
    }
}