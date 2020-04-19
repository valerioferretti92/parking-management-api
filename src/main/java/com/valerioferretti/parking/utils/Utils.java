package com.valerioferretti.parking.utils;

import com.valerioferretti.parking.model.enums.CarType;
import com.valerioferretti.parking.model.enums.ParkingType;

import static com.valerioferretti.parking.model.enums.CarType.*;
import static com.valerioferretti.parking.model.enums.ParkingType.*;

public class Utils {

    public static boolean carMatchesParking(ParkingType parkingType, CarType carType) {
        if (carType.equals(GASOLINE) && parkingType.equals(GASOLINE_CAR_PARK)   ||
            carType.equals(ELECTRIC_20KW) && parkingType.equals(E20KW_CAR_PARK) ||
            carType.equals(ELECTRIC_50KW) && parkingType.equals(E50WK_CAR_PARK)) {
            return true;
        } else {
            return false;
        }


    }

}
