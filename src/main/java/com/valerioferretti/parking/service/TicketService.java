package com.valerioferretti.parking.service;

import com.valerioferretti.parking.model.Ticket;

import java.util.List;

public interface TicketService {
    Ticket insert(String parkingId, String carId, long arrival);
    List<Ticket> getAll();
}
