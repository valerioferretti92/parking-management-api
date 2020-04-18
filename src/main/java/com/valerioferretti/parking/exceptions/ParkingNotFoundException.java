package com.valerioferretti.parking.exceptions;

import lombok.Data;

@Data
public class ParkingNotFoundException extends Exception {
    private String parkingId;
}
