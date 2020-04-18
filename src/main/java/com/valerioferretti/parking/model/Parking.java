package com.valerioferretti.parking.model;

import com.valerioferretti.parking.model.enums.ParkingType;
import com.valerioferretti.parking.model.enums.PricingType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashMap;
import java.util.Map;

@Data
public class Parking {

    public Parking () {
        status = new HashMap<String, Long>();
    }

    @Id
    private String parkingId;
    @Field
    private ParkingType parkingType;
    @Field
    private PricingType pricingType;
    @Field
    private Integer capacity;
    @Field
    private Map<String, Long> status;
}
