package com.valerioferretti.parking.repository;

import com.valerioferretti.parking.model.Invoice;
import com.valerioferretti.parking.model.Ticket;

import java.util.List;

public interface InvoiceDao {
    Invoice insert(Invoice invoice);
    List<Invoice> findAll();
}