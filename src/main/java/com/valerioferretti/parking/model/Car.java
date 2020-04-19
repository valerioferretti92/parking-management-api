package com.valerioferretti.parking.model;

import com.valerioferretti.parking.model.enums.CarType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Data
public class Car {
    @Id
    @NotNull
    private String carId;
    @Field
    @NotNull
    private CarType carType;
}
