package com.valerioferretti.parking.service;

import com.valerioferretti.parking.model.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice insert(String parkingId, String carId, long arrival, long departure, double amount);
    List<Invoice> getAll();
}
