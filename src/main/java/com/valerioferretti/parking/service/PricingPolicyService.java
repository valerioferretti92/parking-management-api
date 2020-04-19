package com.valerioferretti.parking.service;

import com.valerioferretti.parking.model.Fees;

import java.util.Date;

public interface PricingPolicyService {
    Double getAmount(String carId, String parkingId, Date arrival, Date departure, Fees fees);
}
