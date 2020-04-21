package testutils;

import com.valerioferretti.parking.model.Car;
import com.valerioferretti.parking.model.Fees;
import com.valerioferretti.parking.model.Parking;
import com.valerioferretti.parking.model.enums.CarType;
import com.valerioferretti.parking.model.enums.ParkingType;
import com.valerioferretti.parking.model.enums.PricingType;

public class TestUtils {

    public static Car getTestCar(String carId){
        Car car;

        car = new Car();
        car.setCarId(carId);
        car.setCarType(CarType.GASOLINE);
        return car;
    }

    public static Parking getTestParking(String parkingId){
        Parking parking = new Parking();
        parking.setParkingId(parkingId);
        parking.setParkingType(ParkingType.GASOLINE_CAR_PARK);
        parking.setPricingType(PricingType.HOURLY_PRICING);
        parking.setCapacity(5);
        parking.setFees(new Fees());
        parking.getFees().setHourlyFee(5.0);
        parking.getFees().setFixedFee(10.0);
        return parking;
    }

}
