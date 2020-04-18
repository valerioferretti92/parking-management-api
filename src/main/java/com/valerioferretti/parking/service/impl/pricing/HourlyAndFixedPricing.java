package com.valerioferretti.parking.service.impl.pricing;

import org.springframework.stereotype.Service;

@Service
public class HourlyAndFixedPricing implements PricingPolicy {
    public double getAmount(String carId, String parkingId, long arrival, long departure) {
        double roundedDuration;
        double duration;

        duration = (double) ((departure - arrival) / 1000) / 3600;
        roundedDuration = Math.round(duration * 100.0) / 100.0;
        return roundedDuration * 5.00 + 10.00;
    }
}
