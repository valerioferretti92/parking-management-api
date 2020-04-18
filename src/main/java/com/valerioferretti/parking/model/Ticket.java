package com.valerioferretti.parking.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
public class Ticket {
    @Id
    private String id;
    @Field
    private String parkingId;
    @Field
    private String carId;
    @Field
    private Date arrival;
}
