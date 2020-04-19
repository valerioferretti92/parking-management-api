package com.valerioferretti.parking.exceptions.handler;

import lombok.Data;

import java.util.Date;

@Data
public class ApiError {
    private String errorMessage;
    private Date timestamp;
    private String details;

    public ApiError(String errorMessage, String details, Date timestamp) {
        this.errorMessage = errorMessage;
        this.details = details;
        this.timestamp = timestamp;
    }
}
