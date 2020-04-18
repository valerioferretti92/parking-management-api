package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.exceptions.CarAlreadyParkedException;
import com.valerioferretti.parking.exceptions.FullParkingException;
import com.valerioferretti.parking.exceptions.NotFoundCarException;
import com.valerioferretti.parking.exceptions.ParkingNotFoundException;
import com.valerioferretti.parking.model.Invoice;
import com.valerioferretti.parking.model.Parking;
import com.valerioferretti.parking.model.Ticket;
import com.valerioferretti.parking.service.InvoiceService;
import com.valerioferretti.parking.service.TicketService;
import com.valerioferretti.parking.service.impl.pricing.PricingPolicy;
import com.valerioferretti.parking.service.impl.pricing.PricingPolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valerioferretti.parking.repository.ParkingDao;
import com.valerioferretti.parking.service.ParkingService;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingDao parkingDao;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private PricingPolicyFactory pricingPolicyFactory;

    public Parking insert(Parking parking) {
        return parkingDao.insert(parking);
    }

    public void delete(String parkingId) {
        parkingDao.delete(parkingId);
    }

    public List<Parking> getAll(){
        return parkingDao.findAll();
    }

    public Ticket addCar(String parkingId, String carId)
            throws ParkingNotFoundException, CarAlreadyParkedException, FullParkingException {
        Parking parking;
        Set<String> parkedCarIds;

        parking = parkingDao.findById(parkingId);

        //Check that the parking exists
        if (parking == null) {
            throw new ParkingNotFoundException();
        }
        //Check that car 'carId' is not already parked in parking 'parkingId'
        parkedCarIds = parking.getStatus().keySet();
        if (parkedCarIds.contains(carId)) {
            throw new CarAlreadyParkedException();
        }
        //Check that parking is not full
        if(parking.getStatus().size() == parking.getCapacity()) {
            throw new FullParkingException();
        }

        //Check in car and return a ticket
        parking.getStatus().put(carId, Calendar.getInstance().getTimeInMillis());
        parkingDao.update(parking);
        return ticketService.insert(parkingId, carId, parking.getStatus().get(carId));
    }

    public Invoice removeCar(String parkingId, String cardId) throws ParkingNotFoundException, NotFoundCarException {
        PricingPolicy pricingPolicy;
        Parking parking;
        long departure, arrival;
        double amount;

        parking = parkingDao.findById(parkingId);

        //Check that the parking exists
        if (parking == null) {
            throw new ParkingNotFoundException();
        }
        //Check that car 'carId' is parked in parking 'parkingId'
        if (parking.getStatus().get(cardId) == null) {
            throw new NotFoundCarException();
        }

        //Check out car and return invoice
        arrival = parking.getStatus().get(cardId);
        departure = Calendar.getInstance().getTimeInMillis();
        parking.getStatus().remove(cardId);
        parkingDao.update(parking);
        pricingPolicy = pricingPolicyFactory.getPricingPolicy(parking.getPricingType());
        amount = pricingPolicy.getAmount(cardId, parkingId, arrival, departure);
        return invoiceService.insert(parkingId, cardId, arrival, departure, amount);
    }
}
