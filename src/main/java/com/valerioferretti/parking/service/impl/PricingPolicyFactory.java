package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.exceptions.UnknownPricingPolicyException;
import com.valerioferretti.parking.model.enums.PricingType;
import com.valerioferretti.parking.service.PricingPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;

@Service
public class PricingPolicyFactory {

    @Autowired
    private ApplicationContext applicationContext;

    public PricingPolicyService getPricingPolicy(PricingType pricingType) throws UnknownPricingPolicyException {
        AutowireCapableBeanFactory beanFactory;
        PricingPolicyService pricingPolicyService = null;

        beanFactory = applicationContext.getAutowireCapableBeanFactory();

        if (pricingType.equals(PricingType.HOURLY_PRICING)) {
            pricingPolicyService = beanFactory.getBean(HourlyPricingImpl.class);
        }
        if (pricingType.equals(PricingType.HOURLY_FIXED_PRICING)) {
            pricingPolicyService = beanFactory.getBean(HourlyAndFixedPricingImpl.class);
        }
        if (pricingPolicyService == null) {
            throw new UnknownPricingPolicyException(pricingType);
        }
        return pricingPolicyService;
    }
}
