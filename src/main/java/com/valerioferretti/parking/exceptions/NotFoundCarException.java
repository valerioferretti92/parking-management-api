package com.valerioferretti.parking.exceptions;

import lombok.Data;

@Data
public class NotFoundCarException extends Exception {
    private String parkingId;
    private String carId;

    public NotFoundCarException(String parkingId, String carId) {
        this.parkingId = parkingId;
        this.carId = carId;
    }
}
