package com.valerioferretti.parking.controller;

import com.valerioferretti.parking.model.Invoice;
import com.valerioferretti.parking.model.Parking;
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
@RequestMapping("/api/v1/invoice")
public class InvoiceController {

    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);

    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /**
     * Get list of invoices
     * @return http response with a list of invoices in the body
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        List<Invoice> invoices;

        log.info("Retrieving all invoices...");
        invoices = invoiceService.getAll();
        log.info("List of invoices ready!");

        return new ResponseEntity<List<Invoice>>(invoices, HttpStatus.OK);
    }
}
