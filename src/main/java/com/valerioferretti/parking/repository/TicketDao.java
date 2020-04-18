package com.valerioferretti.parking.repository;

import com.valerioferretti.parking.model.Ticket;

import java.util.List;

public interface TicketDao {
    Ticket insert(Ticket ticket);
    List<Ticket> findAll();
}
