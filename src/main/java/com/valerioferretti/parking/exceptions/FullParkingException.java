package com.valerioferretti.parking.exceptions;

import lombok.Data;

@Data
public class FullParkingException extends Exception {
    private String parkingId;

    public FullParkingException(String parkingId) {
        this.parkingId = parkingId;
    }
}
