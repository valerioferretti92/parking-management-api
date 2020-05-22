package com.valerioferretti.parking.exceptions;

import lombok.Data;

@Data
public class BadConfigurationException extends Exception {
    private String message;

    public BadConfigurationException(String message) {
        super();
        this.message = message;
    }
}
