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

    private ApplicationContext applicationContext;

    @Autowired
    public PricingPolicyFactory(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    /**
     * Dispatcher class that matches a pricing type value to the class implementing that pricing policy
     * @param pricingType pricing policy specified for a parking
     * @return instance of PricingPolicyService that implements the specified pricing policy
     * @throws UnknownPricingPolicyException
     */
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
