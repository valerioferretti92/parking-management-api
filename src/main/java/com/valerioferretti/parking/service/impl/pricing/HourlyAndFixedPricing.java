package com.valerioferretti.parking.service.impl.pricing;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service
public class HourlyAndFixedPricing implements PricingPolicy {
    public Double getAmount(String carId, String parkingId, Date arrival, Date departure) {
        Double duration;

        duration = BigDecimal.valueOf(( (double) (departure.getTime() - arrival.getTime()) / 1000) / 3600)
                .setScale(2, RoundingMode.HALF_DOWN)
                .doubleValue();
        return duration * 5.00 + 10.00;
    }
}
