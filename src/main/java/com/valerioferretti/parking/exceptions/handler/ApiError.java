package com.valerioferretti.parking.exceptions.handler;

import lombok.Data;

import java.util.Date;

@Data
public class ApiError {
    private String message;
    private Date timestamp;
    private String details;

    public ApiError(String message, String details, Date timestamp) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }
}
