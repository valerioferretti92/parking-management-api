package com.valerioferretti.parking.controller;

import com.valerioferretti.parking.model.Ticket;
import com.valerioferretti.parking.model.enums.RoleType;
import com.valerioferretti.parking.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Get list of tickets
     * @return http response with a list of tickets in the body
     */
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("@authorizationManager.hasRole('" + RoleType.RoleTypeValues.ADMIN + "')")
    public ResponseEntity<?> getAll(){
        List<Ticket> tickets;

        log.info("Retrieving all tickets...");
        tickets = ticketService.getAll();
        log.info("List of tickets ready!");

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
}
