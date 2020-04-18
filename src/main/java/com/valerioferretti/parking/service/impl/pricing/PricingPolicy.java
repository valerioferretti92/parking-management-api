package com.valerioferretti.parking.service.impl.pricing;

public interface PricingPolicy {
    double getAmount(String carId, String parkingId, long arrival, long departure);
}
