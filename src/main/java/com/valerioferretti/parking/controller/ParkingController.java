package com.valerioferretti.parking.controller;

import com.valerioferretti.parking.exceptions.*;
import com.valerioferretti.parking.model.Car;
import com.valerioferretti.parking.model.Invoice;
import com.valerioferretti.parking.model.Parking;
import com.valerioferretti.parking.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.valerioferretti.parking.service.ParkingService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private static final Logger log = LoggerFactory.getLogger(ParkingController.class);

    @Autowired
    private ParkingService parkingService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> insert(@RequestBody @Valid Parking parking)
            throws ParkingAlreadyExistsException, BadFeesSpecificationException {

        log.info("Creating parking {}...", parking.getParkingId());
        parking = parkingService.insert(parking);
        log.info("Parking {} created!", parking.getParkingId());

        return new ResponseEntity<Parking>(parking, HttpStatus.OK);
    }

    @RequestMapping(value = "/{parkingId}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable String parkingId){

        log.info("Removing parking {}...", parkingId);
        parkingService.delete(parkingId);
        log.info("Parking {} removed!", parkingId);

        return new ResponseEntity<Parking>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        List<Parking> parkings;

        log.info("Retrieving all parkings...");
        parkings = parkingService.getAll();
        log.info("List of parkings ready!");

        return new ResponseEntity<List<Parking>>(parkings, HttpStatus.OK);
    }

    @RequestMapping(value = "/checkin/{parkingId}", method = RequestMethod.PUT)
    public ResponseEntity<?> addCar(@PathVariable String parkingId,
                                    @RequestBody @Valid Car car)
            throws ParkingNotFoundException, CarAlreadyParkedException, FullParkingException, ParkingNotAllowedException {
        Ticket ticket;

        log.info("Adding car {} to parking {}...", car.getCarId(), parkingId);
        ticket = parkingService.addCar(parkingId, car);
        log.info("Car parked!");

        return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
    }

    @RequestMapping(value = "/checkout/{parkingId}", method = RequestMethod.PUT)
    public ResponseEntity<?> removeCar(@PathVariable String parkingId,
                                       @RequestBody Car car)
            throws ParkingNotFoundException, NotFoundCarException, UnknownPricingPolicyException {
        Invoice invoice;

        log.info("Removing car {} to parking {}...", car.getCarId(), parkingId);
        invoice = parkingService.removeCar(parkingId, car.getCarId());
        log.info("Car unparked!");

        return new ResponseEntity<Invoice>(invoice, HttpStatus.OK);
    }
}
