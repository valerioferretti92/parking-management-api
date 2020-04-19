package com.valerioferretti.parking.exceptions;

import com.valerioferretti.parking.model.enums.PricingType;
import lombok.Data;

@Data
public class UnknownPricingPolicyException extends Exception {
    private PricingType pricingType;

    public UnknownPricingPolicyException(PricingType pricingType) {
        super();
        this.pricingType = pricingType;
    }
}
