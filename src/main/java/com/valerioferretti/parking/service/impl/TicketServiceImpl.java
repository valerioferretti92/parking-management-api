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

    public Ticket insert(String parkingId, String carId, Date arrival) {
        Ticket ticket;

        ticket = new Ticket();
        ticket.setParkingId(parkingId);
        ticket.setCarId(carId);
        ticket.setArrival(arrival);
        return ticketDao.insert(ticket);
    }

    public List<Ticket> getAll() {
        return ticketDao.findAll();
    }
}
