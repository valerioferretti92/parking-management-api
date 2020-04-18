package com.valerioferretti.parking.model;

import com.valerioferretti.parking.model.enums.CarType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Car {
    @Id
    private String carId;
    @Field
    private CarType carType;
}
