package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.model.Fees;
import com.valerioferretti.parking.service.PricingPolicyService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HourlyAndFixedPricingImpl implements PricingPolicyService {
    public Double getAmount(String carId, String parkingId, Date arrival, Date departure, Fees fees) {
        double duration, amount;

        if(departure.getTime() < arrival.getTime()) {
            return 0.00;
        }

        duration = (((double)(departure.getTime() - arrival.getTime()) / 1000) / 3600);
        amount = duration * fees.getHourlyFee() + fees.getFixedFee();
        return  Math.floor(amount * 100) / 100;
    }
}
