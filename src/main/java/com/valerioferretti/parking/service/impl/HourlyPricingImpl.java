package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.model.Fees;
import com.valerioferretti.parking.service.PricingPolicyService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service
public class HourlyPricingImpl implements PricingPolicyService {
    public Double getAmount(String carId, String parkingId, Date arrival, Date departure, Fees fees) {
        Double duration;

        duration = BigDecimal.valueOf(( (double) (departure.getTime() - arrival.getTime()) / 1000) / 3600)
                .setScale(2, RoundingMode.HALF_DOWN)
                .doubleValue();
        return duration * fees.getHourlyFee();
    }
}
