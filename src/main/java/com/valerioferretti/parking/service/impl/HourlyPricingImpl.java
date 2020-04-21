package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.model.Fees;
import com.valerioferretti.parking.service.PricingPolicyService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HourlyPricingImpl implements PricingPolicyService {

    /**
     * Calculate the amount to be paid if policy applied is HOURLY_PRICING
     * @param carId id of the car to be billed
     * @param parkingId id of the parking where the car has been parked
     * @param arrival car arrival date
     * @param departure car departure date
     * @param fees fee values applied by the parking
     * @return amount to be paid for the stay
     */
    public Double getAmount(String carId, String parkingId, Date arrival, Date departure, Fees fees) {
        double duration, amount;

        if(departure.getTime() < arrival.getTime()) {
            return 0.00;
        }

        duration = (((double)(departure.getTime() - arrival.getTime()) / 1000) / 3600);
        amount = duration * fees.getHourlyFee();
        return  Math.floor(amount * 100) / 100;
    }
}
