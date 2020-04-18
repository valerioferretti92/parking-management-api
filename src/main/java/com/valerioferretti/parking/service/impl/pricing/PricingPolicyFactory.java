package com.valerioferretti.parking.service.impl.pricing;

import com.valerioferretti.parking.model.enums.PricingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PricingPolicyFactory {

    @Autowired
    private HourlyPricing hourlyPricing;
    @Autowired
    private HourlyAndFixedPricing hourlyAndFixedPricing;

    public PricingPolicy getPricingPolicy(PricingType pricingType) {
        PricingPolicy pricingPolicy = null;

        if (pricingType.equals(PricingType.HOURLY_PRICING)) {
            pricingPolicy = hourlyPricing;
        }
        if (pricingType.equals(PricingType.HOURLY_FIXED_PRICING)) {
            pricingPolicy = hourlyAndFixedPricing;
        }
        return pricingPolicy;
    }
}
