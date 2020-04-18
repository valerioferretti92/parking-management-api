package com.valerioferretti.parking.exceptions;

import lombok.Data;

@Data
public class NotFoundCarException extends Exception {
    private String carId;
    private String parkingId;
}
