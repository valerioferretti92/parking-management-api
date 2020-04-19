package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.model.Fees;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class HourlyPricingImplTest {

    private HourlyPricingImpl hourlyPricing = new HourlyPricingImpl();

    @Test
    public void testGetAmount_DepartureSmallerThenArrival() {
        String parkingId = "parkingId";
        String cardId = "carId";
        Date arrival = Calendar.getInstance().getTime();
        Date departure = new Date(arrival.getTime() - 10);
        Double amount = hourlyPricing.getAmount(cardId, parkingId, arrival, departure, new Fees());
        assertTrue(amount.compareTo(0.00) == 0);
    }

    @Test
    public void testGetAmount_DepartureEqualToArrival() {
        String parkingId = "parkingId";
        String cardId = "carId";
        Date arrival = Calendar.getInstance().getTime();
        Date departure = new Date(arrival.getTime());
        Double hourlyFee = 10.0;
        Fees fees = new Fees();
        fees.setHourlyFee(hourlyFee);
        Double amount = hourlyPricing.getAmount(cardId, parkingId, arrival, departure, fees);
        assertTrue(amount.compareTo(0.00) == 0);
    }

    @Test
    public void testGetAmount() {
        String parkingId = "parkingId";
        String cardId = "carId";
        long duration = 8634566;
        Date arrival = Calendar.getInstance().getTime();
        Date departure = new Date(arrival.getTime() + duration);
        Double hourlyFee = 7.74;
        Fees fees = new Fees();
        fees.setHourlyFee(hourlyFee);
        Double amount = hourlyPricing.getAmount(cardId, parkingId, arrival, departure, fees);
        assertTrue(amount.compareTo(18.56) == 0);
    }
}