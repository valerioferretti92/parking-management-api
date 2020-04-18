package com.valerioferretti.parking.repository;

import com.valerioferretti.parking.model.Parking;

import java.util.List;

public interface ParkingDao {
    Parking insert(Parking parking);
    void delete(String parkingId);
    Parking findById(String parkingId);
    List<Parking> findAll();
    Parking update(Parking parking);
}
