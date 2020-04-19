package com.valerioferretti.parking.service.impl.pricing;

import java.util.Date;

public interface PricingPolicy {
    Double getAmount(String carId, String parkingId, Date arrival, Date departure);
}
