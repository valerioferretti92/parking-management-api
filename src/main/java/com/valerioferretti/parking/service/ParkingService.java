package com.valerioferretti.parking.service;

import com.valerioferretti.parking.exceptions.*;
import com.valerioferretti.parking.model.Car;
import com.valerioferretti.parking.model.Invoice;
import com.valerioferretti.parking.model.Parking;
import com.valerioferretti.parking.model.Ticket;

import java.util.List;

public interface ParkingService {
    Parking insert(Parking parking) throws ParkingAlreadyExistsException;
    void delete(String parkingId);
    List<Parking> getAll();
    Ticket addCar(String parkingId, Car car) throws ParkingNotFoundException, CarAlreadyParkedException, FullParkingException, ParkingNotAllowedException;
    Invoice removeCar(String parkingId, String cardId) throws ParkingNotFoundException, NotFoundCarException, UnknownPricingPolicyException;
}
