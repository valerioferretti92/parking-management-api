package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.exceptions.*;
import com.valerioferretti.parking.model.*;
import com.valerioferretti.parking.service.InvoiceService;
import com.valerioferretti.parking.service.TicketService;
import com.valerioferretti.parking.service.PricingPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valerioferretti.parking.repository.ParkingDao;
import com.valerioferretti.parking.service.ParkingService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.valerioferretti.parking.utils.Utils.carMatchesParking;
import static com.valerioferretti.parking.utils.Utils.checkFees;

@Service
public class ParkingServiceImpl implements ParkingService {

    private ParkingDao parkingDao;
    private TicketService ticketService;
    private InvoiceService invoiceService;
    private PricingPolicyFactory pricingPolicyFactory;

    @Autowired
    public ParkingServiceImpl(ParkingDao parkingDao,
                              TicketService ticketService,
                              InvoiceService invoiceService,
                              PricingPolicyFactory pricingPolicyFactory) {
        this.parkingDao = parkingDao;
        this.ticketService = ticketService;
        this.invoiceService = invoiceService;
        this.pricingPolicyFactory = pricingPolicyFactory;
    }

    /**
     * Insert new parking. The function checks that:
     *   no parking with the same ID already exists
     *   fee values specified match pricing policy assign to the parking
     * @param parking parking to be inserted
     * @return parking object
     * @throws ParkingAlreadyExistsException
     * @throws BadFeesSpecificationException
     */
    public Parking insert(Parking parking) throws ParkingAlreadyExistsException, BadFeesSpecificationException {
        Parking parkingDb;

        parkingDb = parkingDao.findById(parking.getParkingId());
        if (parkingDb != null) {
            throw new ParkingAlreadyExistsException(parking.getParkingId());
        }
        if(!checkFees(parking.getPricingType(), parking.getFees())) {
            throw new BadFeesSpecificationException();
        }
        return parkingDao.insert(parking);
    }

    /**
     * Delete a parking
     * @param parkingId id of the parking to be deleted
     */
    public void delete(String parkingId) {
        parkingDao.delete(parkingId);
    }

    /**
     * Get the list of parkings previously registered
     * @return list of parkings previously registered
     */
    public List<Parking> getAll(){
        return parkingDao.findAll();
    }

    /**
     * Park a car into a parking. It checks that:
     *   the given parking exists
     *   the car is allowed to park in given parking (car type matches park type)
     *   the given car is not already parked in the given parking
     *   the given parking is not full
     * @param parkingId id of the parking where to park our car
     * @param car car to be parked
     * @return ticket object
     * @throws ParkingNotFoundException
     * @throws CarAlreadyParkedException
     * @throws FullParkingException
     * @throws ParkingNotAllowedException
     */
    public synchronized Ticket addCar(String parkingId, Car car) throws ParkingNotFoundException, CarAlreadyParkedException,
            FullParkingException, ParkingNotAllowedException {
        Parking parking;

        parking = parkingDao.findById(parkingId);

        //Check that the parking exists
        if (parking == null) {
            throw new ParkingNotFoundException(parkingId);
        }
        //Check that the car type matches parking type
        if(!carMatchesParking(parking.getParkingType(), car.getCarType())) {
            throw new ParkingNotAllowedException(parking, car);
        }
        //Check that car 'carId' is not already parked in parking 'parkingId'
        if (parking.getStatus().keySet().contains(car.getCarId())) {
            throw new CarAlreadyParkedException(parkingId, car.getCarId());
        }
        //Check that parking is not full
        if(parking.getStatus().size() == parking.getCapacity()) {
            throw new FullParkingException(parkingId);
        }

        //Check in car and return a ticket
        parking.getStatus().put(car.getCarId(), Calendar.getInstance().getTime());
        parkingDao.update(parking);
        return ticketService.insert(parkingId, car.getCarId(), parking.getStatus().get(car.getCarId()));
    }

    /**
     * Remove a car from a parking. It checks that:
     *   the given parking exists
     *   the car is actually parked in the given parking
     * @param parkingId id of the parked where to lok for the given car
     * @param cardId car to be removed
     * @return invoice object, containing the amount to be paid
     * @throws ParkingNotFoundException
     * @throws NotFoundCarException
     * @throws UnknownPricingPolicyException
     */
    public synchronized Invoice removeCar(String parkingId, String cardId) throws ParkingNotFoundException, NotFoundCarException, UnknownPricingPolicyException {
        PricingPolicyService pricingPolicyService;
        Parking parking;
        Date departure, arrival;
        Fees fees;
        double amount;

        parking = parkingDao.findById(parkingId);

        //Check that the parking exists
        if (parking == null) {
            throw new ParkingNotFoundException(parkingId);
        }
        //Check that car 'carId' is parked in parking 'parkingId'
        if (parking.getStatus().get(cardId) == null) {
            throw new NotFoundCarException(parkingId, cardId);
        }

        //Check out car and return invoice
        arrival = parking.getStatus().get(cardId);
        departure = Calendar.getInstance().getTime();
        fees = parking.getFees();
        parking.getStatus().remove(cardId);
        parkingDao.update(parking);
        pricingPolicyService = pricingPolicyFactory.getPricingPolicy(parking.getPricingType());
        amount = pricingPolicyService.getAmount(cardId, parkingId, arrival, departure, fees);
        return invoiceService.insert(parkingId, cardId, arrival, departure, amount);
    }
}
