package com.valerioferretti.parking.repository.impl;

import com.valerioferretti.parking.model.Ticket;
import com.valerioferretti.parking.repository.TicketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketDaoImpl implements TicketDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Ticket insert(Ticket ticket) {
        mongoTemplate.insert(ticket);
        return ticket;
    }

    public Ticket findById(String ticketId) {
        return mongoTemplate.findById(ticketId, Ticket.class);
    }

    public List<Ticket> findAll() {
        return mongoTemplate.findAll(Ticket.class);
    }
}
