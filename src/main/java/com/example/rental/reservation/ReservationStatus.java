package com.example.rental.reservation;

import java.util.List;

public enum ReservationStatus {
    PAYMENT_PENDING,
    RESERVED,
    RENTING,
    COMPLETED,
    CANCELED;

    public static List<ReservationStatus> occupyingCarStatuses() {
        return List.of(PAYMENT_PENDING, RESERVED, RENTING);
    }
}
