package com.example.rental.web;

import com.example.rental.payment.PaymentSimulationResult;
import com.example.rental.payment.PaymentSimulator;
import com.example.rental.reservation.Reservation;
import com.example.rental.reservation.ReservationRepository;
import com.example.rental.reservation.ReservationStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaymentController {

    // 예약 데이터와 결제 금액을 조회하는 저장소
    private final ReservationRepository reservationRepository;
    // 외부 결제 응답을 흉내 내는 시뮬레이터
    private final PaymentSimulator paymentSimulator;

    public PaymentController(ReservationRepository reservationRepository,
                             PaymentSimulator paymentSimulator) {
        this.reservationRepository = reservationRepository;
        this.paymentSimulator = paymentSimulator;
    }

    @PostMapping("/reservations/{reservationId}/simulate-payment")
    public String simulatePayment(@PathVariable("reservationId") Long reservationId,
                                  Model model) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없다."));

        if (reservation.getStatus() != ReservationStatus.PAYMENT_PENDING) {
            throw new IllegalStateException("결제 대기 중인 예약만 결제할 수 있다.");
        }

        PaymentSimulationResult result = paymentSimulator.simulate(reservation.getTotalAmount());

        model.addAttribute("reservationId", reservation.getId());
        model.addAttribute("result", result);
        return "payments/simulation-result";
    }
}