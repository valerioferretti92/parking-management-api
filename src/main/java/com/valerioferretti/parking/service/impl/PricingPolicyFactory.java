package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.exceptions.UnknownPricingPolicyException;
import com.valerioferretti.parking.model.enums.PricingType;
import com.valerioferretti.parking.service.impl.pricing.HourlyAndFixedPricing;
import com.valerioferretti.parking.service.impl.pricing.HourlyPricing;
import com.valerioferretti.parking.service.impl.pricing.PricingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;

@Service
public class PricingPolicyFactory {

    @Autowired
    private ApplicationContext applicationContext;

    public PricingPolicy getPricingPolicy(PricingType pricingType) throws UnknownPricingPolicyException {
        AutowireCapableBeanFactory beanFactory;
        PricingPolicy pricingPolicy = null;

        beanFactory = applicationContext.getAutowireCapableBeanFactory();

        if (pricingType.equals(PricingType.HOURLY_PRICING)) {
            pricingPolicy = beanFactory.getBean(HourlyPricing.class);
        }
        if (pricingType.equals(PricingType.HOURLY_FIXED_PRICING)) {
            pricingPolicy = beanFactory.getBean(HourlyAndFixedPricing.class);
        }
        if (pricingPolicy == null) {
            throw new UnknownPricingPolicyException(pricingType);
        }
        return pricingPolicy;
    }
}
