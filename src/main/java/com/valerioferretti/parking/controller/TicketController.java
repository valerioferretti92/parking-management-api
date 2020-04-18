package com.valerioferretti.parking.controller;

import com.valerioferretti.parking.model.Invoice;
import com.valerioferretti.parking.model.Ticket;
import com.valerioferretti.parking.service.InvoiceService;
import com.valerioferretti.parking.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private TicketService ticketService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        List<Ticket> tickets;

        log.info("Retrieving all tickets...");
        tickets = ticketService.getAll();
        log.info("List of tickets ready!");

        return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
    }
}
