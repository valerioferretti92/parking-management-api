package com.valerioferretti.parking.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Fees {
    @Field
    private Double hourlyFee;
    @Field
    private Double fixedFee;
}
