package com.example.rental.web;

import java.time.LocalDate;

public class CarSearchForm {

    // 검색할 대여 시작일
    private LocalDate startDate;
    // 검색할 반납 예정일
    private LocalDate endDate;

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
}
