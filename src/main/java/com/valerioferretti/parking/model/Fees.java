package com.valerioferretti.parking.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Fees {
    @Field
    private Double hourlyFee;
    @Field
    private Double fixedFee;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Fees)) {
            return false;
        }

        Fees fees = (Fees) o;
        return hourlyFee.equals(fees.getHourlyFee()) && fixedFee.equals(fees.getFixedFee());
    }
}
