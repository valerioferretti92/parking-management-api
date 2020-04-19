package com.valerioferretti.parking.exceptions;

import lombok.Data;

@Data
public class ParkingNotFoundException extends Exception {
    private String parkingId;

    public ParkingNotFoundException(String parkingId) {
        this.parkingId = parkingId;
    }
}
