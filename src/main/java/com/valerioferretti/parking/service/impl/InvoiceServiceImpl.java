package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.model.Invoice;
import com.valerioferretti.parking.repository.InvoiceDao;
import com.valerioferretti.parking.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;

    public Invoice insert(String parkingId, String carId, Date arrival, Date departure, Double amount) {
        Invoice invoice;

        invoice = new Invoice();
        invoice.setParkingId(parkingId);
        invoice.setCarId(carId);
        invoice.setArrival(arrival);
        invoice.setDeparture(departure);
        invoice.setAmount(amount);
        return invoiceDao.insert(invoice);
    }

    public List<Invoice> getAll() {
        return invoiceDao.findAll();
    }
}
