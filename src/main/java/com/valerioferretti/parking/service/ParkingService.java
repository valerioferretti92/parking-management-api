package com.valerioferretti.parking.service;

import com.valerioferretti.parking.exceptions.CarAlreadyParkedException;
import com.valerioferretti.parking.exceptions.FullParkingException;
import com.valerioferretti.parking.exceptions.NotFoundCarException;
import com.valerioferretti.parking.exceptions.ParkingNotFoundException;
import com.valerioferretti.parking.model.Invoice;
import com.valerioferretti.parking.model.Parking;
import com.valerioferretti.parking.model.Ticket;

import java.util.List;

public interface ParkingService {
    Parking insert(Parking parking);
    void delete(String parkingId);
    List<Parking> getAll();
    Ticket addCar(String parkingId, String carId) throws ParkingNotFoundException, CarAlreadyParkedException, FullParkingException;
    Invoice removeCar(String parkingId, String cardId) throws ParkingNotFoundException, NotFoundCarException;
}
