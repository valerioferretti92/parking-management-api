package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.model.Ticket;
import com.valerioferretti.parking.repository.TicketDao;
import com.valerioferretti.parking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketDao ticketDao;

    @Autowired
    public TicketServiceImpl(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    /**
     * Insert a ticket object
     * @param parkingId id of the parking where the car has been parked
     * @param carId id of the car that has been parked
     * @param arrival date of car arrival
     * @return ticket object
     */
    public Ticket insert(String parkingId, String carId, Date arrival) {
        Ticket ticket;

        ticket = new Ticket();
        ticket.setParkingId(parkingId);
        ticket.setCarId(carId);
        ticket.setArrival(arrival);
        return ticketDao.insert(ticket);
    }

    /**
     * Get list of emitted tickets
     * @return list of emitted tickets
     */
    public List<Ticket> getAll() {
        return ticketDao.findAll();
    }
}
