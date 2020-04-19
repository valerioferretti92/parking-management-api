package com.valerioferretti.parking.exceptions;

import lombok.Data;

@Data
public class ParkingAlreadyExistsException extends Exception {
    private String parkingId;

    public ParkingAlreadyExistsException(String parkingId) {
        super();
        this.parkingId = parkingId;
    }
}
