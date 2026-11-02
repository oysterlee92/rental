package com.example.rental.web;

import java.time.LocalDate;

public class ReservationForm {

    // 예약 대상 차량의 ID
    private Long carId;
    // 예약할 대여 시작일
    private LocalDate startDate;
    // 예약할 반납 예정일
    private LocalDate endDate;
    // 예약자 이름
    private String renterName;
    // 예약자 연락처
    private String renterPhone;

    public ReservationForm() {
    }

    public ReservationForm(Long carId, LocalDate startDate, LocalDate endDate) {
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getRenterPhone() {
        return renterPhone;
    }

    public void setRenterPhone(String renterPhone) {
        this.renterPhone = renterPhone;
    }
}
