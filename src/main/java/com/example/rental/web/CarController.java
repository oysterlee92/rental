package com.example.rental.web;

import com.example.rental.car.CarSearchService;
import com.example.rental.car.CarSummary;
import com.example.rental.reservation.ReservationService;
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

    // 기간별 차량 검색을 담당하는 서비스
    private final CarSearchService carSearchService;
    // 예약 검증과 저장을 담당하는 서비스
    private final ReservationService reservationService;

    public CarController(CarSearchService carSearchService, ReservationService reservationService) {
        this.carSearchService = carSearchService;
        this.reservationService = reservationService;
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
    public String reservationForm(@PathVariable("carId") Long carId,
                                  @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                  @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                  Model model) {
        if (endDate.isBefore(startDate)) {
            return "redirect:/cars";
        }

        Optional<CarSummary> car = carSearchService.findAvailableCar(carId, startDate, endDate);
        if (car.isEmpty()) {
            return "redirect:/cars";
        }

        model.addAttribute("car", car.get());
        model.addAttribute("reservationForm",
                new ReservationForm(carId, startDate, endDate));
        return "cars/reservation-form";
    }

    @PostMapping("/reservations")
    public String submitReservation(@ModelAttribute("reservationForm") ReservationForm form, Model model) {
        try {
            Long reservationId = reservationService.createReservation(
                    form.getCarId(), form.getStartDate(), form.getEndDate(),
                    form.getRenterName(), form.getRenterPhone());

            model.addAttribute("reservationId", reservationId);
            return "cars/reservation-complete";
        } catch (IllegalArgumentException | IllegalStateException exception) {
            return "redirect:/cars";
        }
    }
}