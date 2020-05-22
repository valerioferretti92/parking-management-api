package com.valerioferretti.parking.exceptions;

import lombok.Data;

@Data
public class AccountAlreadyExistsException extends Exception {

    private String email;

    public AccountAlreadyExistsException(String email){
        this.email = email;
    }
}
