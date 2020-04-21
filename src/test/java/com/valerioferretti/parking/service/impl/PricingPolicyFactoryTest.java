package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.exceptions.UnknownPricingPolicyException;
import com.valerioferretti.parking.model.enums.PricingType;
import com.valerioferretti.parking.service.PricingPolicyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class PricingPolicyFactoryTest {
    @Mock
    private ApplicationContext applicationContextMock;
    @Mock
    private AutowireCapableBeanFactory autowireCapableBeanFactoryMock;

    private PricingPolicyFactory pricingPolicyFactory;

    /**
     * Testing the behaviour of PricingPolicyFactory when the given PricingType value is unknown
     * @throws UnknownPricingPolicyException
     */
    @Test(expected = UnknownPricingPolicyException.class)
    public void testGetPricingPolicy_Exception() throws UnknownPricingPolicyException {
        //Setting up mocks
        MockitoAnnotations.initMocks(this);
        when(autowireCapableBeanFactoryMock.getBean(ArgumentMatchers.any(Class.class))).thenReturn(null);
        when(applicationContextMock.getAutowireCapableBeanFactory()).thenReturn(autowireCapableBeanFactoryMock);
        pricingPolicyFactory = new PricingPolicyFactory(applicationContextMock);

        //Throwing exception
        pricingPolicyFactory.getPricingPolicy(PricingType.HOURLY_PRICING);
    }

    /**
     * testing intended behaviour of PricingPolicyFactory
     * @throws UnknownPricingPolicyException
     */
    @Test
    public void testGetPricingPolicy() throws UnknownPricingPolicyException {
        PricingPolicyService pricingPolicy;

        //Setting up mocks
        MockitoAnnotations.initMocks(this);
        when(autowireCapableBeanFactoryMock.getBean(ArgumentMatchers.any(Class.class))).thenAnswer(new Answer<PricingPolicyService>() {
            public PricingPolicyService answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Class requestedClass = (Class) args[0];

                if(requestedClass.equals(HourlyPricingImpl.class)){
                    return new HourlyPricingImpl();
                } else {
                    return new HourlyAndFixedPricingImpl();
                }
            }
        });
        when(applicationContextMock.getAutowireCapableBeanFactory()).thenReturn(autowireCapableBeanFactoryMock);
        pricingPolicyFactory = new PricingPolicyFactory(applicationContextMock);

        //Testing getPricingPolicy method
        pricingPolicy = pricingPolicyFactory.getPricingPolicy(PricingType.HOURLY_PRICING);
        assertTrue(pricingPolicy instanceof HourlyPricingImpl);
        pricingPolicy = pricingPolicyFactory.getPricingPolicy(PricingType.HOURLY_FIXED_PRICING);
        assertTrue(pricingPolicy instanceof HourlyAndFixedPricingImpl);
    }
}