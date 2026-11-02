package com.example.rental.web;

import com.example.rental.car.CarSearchService;
import com.example.rental.car.CarSummary;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class CarController {

    // 차량 검색과 조회를 담당하는 서비스
    private final CarSearchService carSearchService;

    public CarController(CarSearchService carSearchService) {
        this.carSearchService = carSearchService;
    }

    @GetMapping("/cars")
    public String search(@ModelAttribute("searchForm") CarSearchForm form, Model model) {
        List<CarSummary> cars = List.of();
        boolean searched = form.getStartDate() != null || form.getEndDate() != null;

        if (searched) {
            if (form.getStartDate() == null || form.getEndDate() == null) {
                model.addAttribute("dateError", "대여 시작일과 반납 예정일을 모두 입력해야 한다.");
            } else if (form.getEndDate().isBefore(form.getStartDate())) {
                model.addAttribute("dateError", "반납 예정일은 대여 시작일보다 빠를 수 없다.");
            } else {
                cars = carSearchService.searchAvailable(form.getStartDate(), form.getEndDate());
            }
        }

        model.addAttribute("cars", cars);
        model.addAttribute("searched", searched);
        return "cars/search";
    }

    @GetMapping("/cars/{carId}/reservation")
    public String reservationForm(
            @PathVariable Long carId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model
    ) {
        if (endDate.isBefore(startDate)) {
            return "redirect:/cars";
        }

        Optional<CarSummary> car = carSearchService.findAvailableCar(carId, startDate, endDate);
        if (car.isEmpty()) {
            return "redirect:/cars";
        }

        model.addAttribute("car", car.get());
        model.addAttribute("reservationForm", new ReservationForm(carId, startDate, endDate));

        return "cars/reservation-form";
    }

    @PostMapping("/reservations")
    public String submitReservation(
            @ModelAttribute("reservationForm") ReservationForm form,
            Model model
    ) {
        if (form.getCarId() == null
                || form.getStartDate() == null
                || form.getEndDate() == null
                || form.getRenterName() == null
                || form.getRenterName().isBlank()
                || form.getRenterPhone() == null
                || form.getRenterPhone().isBlank()
                || form.getEndDate().isBefore(form.getStartDate())) {
            return "redirect:/cars";
        }

        Optional<CarSummary> car = carSearchService.findAvailableCar(
                form.getCarId(), form.getStartDate(), form.getEndDate());
        if (car.isEmpty()) {
            return "redirect:/cars";
        }

        model.addAttribute("car", car.get());
        model.addAttribute("reservationForm", form);
        return "cars/reservation-complete";
    }
}
