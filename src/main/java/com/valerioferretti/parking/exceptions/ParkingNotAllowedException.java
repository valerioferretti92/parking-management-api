package com.valerioferretti.parking.exceptions;

import com.valerioferretti.parking.model.Car;
import com.valerioferretti.parking.model.Parking;
import com.valerioferretti.parking.model.enums.CarType;
import com.valerioferretti.parking.model.enums.ParkingType;
import lombok.Data;

@Data
public class ParkingNotAllowedException extends Exception {
    private String carId;
    private CarType carType;
    private String parkingId;
    private ParkingType parkingType;

    public ParkingNotAllowedException(Parking parking, Car car){
        super();
        carId = car.getCarId();
        carType = car.getCarType();
        parkingId = parking.getParkingId();
        parkingType = parking.getParkingType();
    }
}
