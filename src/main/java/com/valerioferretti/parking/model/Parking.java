package com.valerioferretti.parking.model;

import com.valerioferretti.parking.model.enums.ParkingType;
import com.valerioferretti.parking.model.enums.PricingType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class Parking {

    public Parking () {
        status = new HashMap<String, java.util.Date>();
        fees = new Fees();
    }

    @Id
    @NotNull
    private String parkingId;
    @Field
    @NotNull
    private ParkingType parkingType;
    @Field
    @NotNull
    private PricingType pricingType;
    @Field
    @NotNull
    private Integer capacity;
    @Field
    private Map<String, Date> status;
    @Field
    @NotNull
    private Fees fees;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Parking)) {
            return false;
        }

        Parking parking = (Parking) o;
        return parkingId.equals(parking.getParkingId())         &&
                parkingType.equals(parking.getParkingType())    &&
                pricingType.equals(parking.getPricingType())    &&
                capacity.equals(parking.getCapacity())          &&
                fees.equals(parking.getFees());
    }
}
