package com.valerioferretti.parking.exceptions;

import lombok.Data;

@Data
public class CarAlreadyParkedException extends Exception {
    private String carId;
    private String parkingId;

    public CarAlreadyParkedException(String parkingId, String carId) {
        super();
        this.carId = carId;
        this.parkingId = parkingId;
    }
}
