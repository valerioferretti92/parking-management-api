package com.valerioferretti.parking.controller;

import com.valerioferretti.parking.ParkingApplication;
import com.valerioferretti.parking.exceptions.*;
import com.valerioferretti.parking.model.Car;
import com.valerioferretti.parking.model.Invoice;
import com.valerioferretti.parking.model.Parking;
import com.valerioferretti.parking.model.Ticket;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static testutils.TestUtils.getTestCar;
import static testutils.TestUtils.getTestParking;

/**
 * Integration tests from controller down to data storage.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = ParkingApplication.class)
public class ParkingControllerTest {

    @Autowired
    private ParkingController parkingController;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * This method tests the intended behaviour of insert() from controller point of view. It cleans up the DB
     * afterwards.
     * @throws ParkingAlreadyExistsException
     * @throws BadFeesSpecificationException
     */
    @Test
    public void testInsert() throws ParkingAlreadyExistsException, BadFeesSpecificationException {
        Parking parking;
        Parking parkingDb = null;
        String parkingId = RandomStringUtils.randomAlphanumeric(10);;

        try {
            parking = getTestParking(parkingId);
            parking = (Parking) parkingController.insert(parking).getBody();
            parkingDb = mongoTemplate.findById(parkingId, Parking.class);

            assertTrue(parking.equals(parkingDb));
            assertTrue(parkingDb.getStatus().size() == 0);
        } finally {
            //Cleaning up parking collection
            if(parkingDb != null) {
                mongoTemplate.remove(parkingDb);
            }
        }
    }

    /**
     * This method tests the intended behaviour of addCar() from controller point of view. It adds a car, checks
     * that the parking object is updated properly in DB and it verifies that the returned ticket is correct.
     * It cleans up the DB afterwards.
     * @throws ParkingAlreadyExistsException
     * @throws BadFeesSpecificationException
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     */
    @Test
    public void testAddCar() throws ParkingAlreadyExistsException, BadFeesSpecificationException,
            ParkingNotAllowedException, CarAlreadyParkedException, ParkingNotFoundException, FullParkingException {
        Parking parking, parkingDb = null;
        Ticket ticket = null;
        Car car;
        String parkingId = RandomStringUtils.randomAlphanumeric(10);;
        String carId = RandomStringUtils.randomAlphanumeric(10);;

        try{
            parking = getTestParking(parkingId);
            parkingController.insert(parking);
            car = getTestCar(carId);
            ticket = (Ticket) parkingController.addCar(parkingId, car).getBody();
            parkingDb = mongoTemplate.findById(parkingId, Parking.class);

            assertTrue(parking.equals(parkingDb));
            assertTrue(parkingDb.getStatus().size() == 1);
            assertNotNull(parkingDb.getStatus().get(car.getCarId()));
            assertNotNull(ticket.getId());
            assertTrue(ticket.getParkingId().equals(parkingId));
            assertTrue(ticket.getCarId().equals(car.getCarId()));
            assertNotNull(ticket.getArrival());
        } finally {
            //Cleaning up ticket collection
            if(ticket != null) {
                mongoTemplate.remove(ticket);
            }
            //Cleaning up parking collection
            if(parkingDb != null) {
                mongoTemplate.remove(parkingDb);
            }
        }
    }

    /**
     * This method tests the intended behaviour of addCar() from controller point of view. It adds two cars and
     * removes one of them. It checks that the parking object is updated properly in DB and that invoice and
     * ticket objects are created correctly. It cleans up the DB afterwards.
     * @throws ParkingAlreadyExistsException
     * @throws BadFeesSpecificationException
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     * @throws UnknownPricingPolicyException
     * @throws NotFoundCarException
     */
    @Test
    public void removeCar() throws ParkingAlreadyExistsException, BadFeesSpecificationException,
            ParkingNotAllowedException, CarAlreadyParkedException, ParkingNotFoundException, FullParkingException,
            UnknownPricingPolicyException, NotFoundCarException {
        Ticket ticket1 = null, ticket2 = null;
        Invoice invoice = null;
        String carId1 = RandomStringUtils.randomAlphanumeric(10);
        String carId2 = RandomStringUtils.randomAlphanumeric(10);
        Car car1, car2;
        Parking parking, parkingDb = null;
        String parkingId = RandomStringUtils.randomAlphanumeric(10);;

        try{
            parking = getTestParking(parkingId);
            parkingController.insert(parking);
            car1 = getTestCar(carId1);
            car2 = getTestCar(carId2);
            ticket1 = (Ticket) parkingController.addCar(parkingId, car1).getBody();
            ticket2 = (Ticket) parkingController.addCar(parkingId, car2).getBody();

            parkingDb = mongoTemplate.findById(parkingId, Parking.class);
            assertTrue(parking.equals(parkingDb));
            assertTrue(parkingDb.getStatus().size() == 2);
            assertNotNull(parkingDb.getStatus().get(car1.getCarId()));
            assertNotNull(parkingDb.getStatus().get(car2.getCarId()));
            assertNotNull(ticket1.getId());
            assertTrue(ticket1.getParkingId().equals(parkingId));
            assertTrue(ticket1.getCarId().equals(car1.getCarId()));
            assertNotNull(ticket1.getArrival());
            assertNotNull(ticket2.getId());
            assertTrue(ticket2.getParkingId().equals(parkingId));
            assertTrue(ticket2.getCarId().equals(car2.getCarId()));
            assertNotNull(ticket2.getArrival());

            invoice = (Invoice) parkingController.removeCar(parkingId, car1).getBody();

            parkingDb = mongoTemplate.findById(parkingId, Parking.class);
            assertTrue(parking.equals(parkingDb));
            assertTrue(parkingDb.getStatus().size() == 1);
            assertNotNull(parkingDb.getStatus().get(car2.getCarId()));
            assertNotNull(invoice.getId());
            assertTrue(invoice.getParkingId().equals(parkingId));
            assertTrue(invoice.getCarId().equals(car1.getCarId()));
            assertNotNull(invoice.getArrival());
            assertNotNull(invoice.getDeparture());
            assertTrue(invoice.getDeparture().getTime() - invoice.getArrival().getTime() >= 0);
        } finally {
            //Cleaning up ticket collection
            if(ticket1 != null) {
                mongoTemplate.remove(ticket1);
            }
            if(ticket2 != null) {
                mongoTemplate.remove(ticket2);
            }
            //Cleaning up invoice collection
            if(invoice != null) {
                mongoTemplate.remove(invoice);
            }
            //Cleaning up parking collection
            if(parkingDb != null) {
                mongoTemplate.remove(parkingDb);
            }
        }
    }
}