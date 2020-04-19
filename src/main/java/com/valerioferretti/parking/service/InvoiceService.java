package com.valerioferretti.parking.service;

import com.valerioferretti.parking.model.Invoice;

import java.util.Date;
import java.util.List;

public interface InvoiceService {
    Invoice insert(String parkingId, String carId, Date arrival, Date departure, Double amount);
    List<Invoice> getAll();
}
