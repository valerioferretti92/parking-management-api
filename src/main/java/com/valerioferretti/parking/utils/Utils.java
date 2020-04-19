package com.valerioferretti.parking.utils;

import com.valerioferretti.parking.model.Fees;
import com.valerioferretti.parking.model.enums.CarType;
import com.valerioferretti.parking.model.enums.ParkingType;
import com.valerioferretti.parking.model.enums.PricingType;

import static com.valerioferretti.parking.model.enums.CarType.*;
import static com.valerioferretti.parking.model.enums.ParkingType.*;

public class Utils {

    public static boolean carMatchesParking(ParkingType parkingType, CarType carType) {
        if (carType.equals(GASOLINE) && parkingType.equals(GASOLINE_CAR_PARK)   ||
            carType.equals(ELECTRIC_20KW) && parkingType.equals(E20KW_CAR_PARK) ||
            carType.equals(ELECTRIC_50KW) && parkingType.equals(E50KW_CAR_PARK)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkFees(PricingType pricingType, Fees fees) {
        if (pricingType.equals(PricingType.HOURLY_PRICING)) {
            return checkHourlyPricingFees(fees);
        } else {
            return checkHourlyFixedPricingFees(fees);
        }
    }

    public static boolean checkHourlyPricingFees(Fees fees) {
        if(fees.getHourlyFee() == null || fees.getHourlyFee() < 0) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkHourlyFixedPricingFees(Fees fees) {
        if(fees.getHourlyFee() == null  ||
           fees.getFixedFee() == null   ||
           fees.getHourlyFee() < 0      ||
           fees.getFixedFee() < 0) {
            return false;
        } else {
            return true;
        }
    }
}
