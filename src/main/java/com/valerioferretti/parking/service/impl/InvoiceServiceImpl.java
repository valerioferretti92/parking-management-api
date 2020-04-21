package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.model.Invoice;
import com.valerioferretti.parking.repository.InvoiceDao;
import com.valerioferretti.parking.repository.TicketDao;
import com.valerioferretti.parking.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceDao invoiceDao;

    @Autowired
    public InvoiceServiceImpl(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    /**
     * Insert an invoice object.
     * @param parkingId id of parking the car has been removed from
     * @param carId id of the car that has left the parking
     * @param arrival car arrival date
     * @param departure car departure date
     * @param amount amount that should be paid for the stay
     * @return invoice object created
     */
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

    /**
     * Get list of emitted invoices
     * @return list of emitted invoices
     */
    public List<Invoice> getAll() {
        return invoiceDao.findAll();
    }
}
