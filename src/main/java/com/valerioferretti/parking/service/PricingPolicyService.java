package com.valerioferretti.parking.service;

import java.util.Date;

public interface PricingPolicyService {
    Double getAmount(String carId, String parkingId, Date arrival, Date departure);
}
