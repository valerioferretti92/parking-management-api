package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.exceptions.*;
import com.valerioferretti.parking.model.*;
import com.valerioferretti.parking.model.enums.CarType;
import com.valerioferretti.parking.model.enums.ParkingType;
import com.valerioferretti.parking.model.enums.PricingType;
import com.valerioferretti.parking.repository.ParkingDao;
import com.valerioferretti.parking.service.InvoiceService;
import com.valerioferretti.parking.service.PricingPolicyService;
import com.valerioferretti.parking.service.TicketService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static testutils.TestUtils.getTestCar;
import static testutils.TestUtils.getTestParking;

/**
 * Unit tests of methods in ParkingServiceImpl
 */
public class ParkingServiceImplTest {

    @Mock
    private ParkingDao parkingDaoMock;
    @Mock
    private TicketService ticketServiceMock;
    @Mock
    private InvoiceService invoiceServiceMock;
    @Mock
    private PricingPolicyFactory pricingPolicyFactoryMock;
    @Mock
    private PricingPolicyService pricingPolicyServiceMock;

    private ParkingServiceImpl parkingService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        parkingService = new ParkingServiceImpl(parkingDaoMock, ticketServiceMock,
                                                invoiceServiceMock, pricingPolicyFactoryMock);
    }

    /*********************** Testing ParkingService.insert() function ***********************/

    /**
     * Testing correct creation of a new Parking object.
     * @throws ParkingAlreadyExistsException
     * @throws BadFeesSpecificationException
     */
    @Test
    public void testInsert() throws ParkingAlreadyExistsException, BadFeesSpecificationException {
        Parking expectedParking;
        Parking retrievedParking;

        //Setting up mocks
        when(parkingDaoMock.insert(any(Parking.class))).thenAnswer(new Answer<Parking>() {
            public Parking answer(InvocationOnMock invocation) {
                return (Parking) invocation.getArguments()[0];
            }
        });

        //Creating and inserting parking
        expectedParking = getTestParking("parkingId");
        retrievedParking = parkingService.insert(expectedParking);

        //Checking results
        assertTrue(expectedParking.equals(retrievedParking));
        assertTrue(retrievedParking.getStatus().size() == 0);
    }

    /**
     * Testing creation of a parking when another one with same id already exists.
     * @throws ParkingAlreadyExistsException
     * @throws BadFeesSpecificationException
     */
    @Test(expected = ParkingAlreadyExistsException.class)
    public void testInsert_ParkingAlreadyExists() throws ParkingAlreadyExistsException, BadFeesSpecificationException {
        Parking expectedParking;

        //Setting up mocks
        when(parkingDaoMock.findById(any(String.class))).thenAnswer(new Answer<Parking>() {
            public Parking answer(InvocationOnMock invocation) {
                return getTestParking((String) invocation.getArguments()[0]);
            }
        });

        //Creating and inserting parking
        expectedParking = getTestParking("parkingId");
        parkingService.insert(expectedParking);
    }

    /**
     * Testing creation of a hourly billing parking when the parking object itself is missing hourly fee
     * information.
     * @throws ParkingAlreadyExistsException
     * @throws BadFeesSpecificationException
     */
    @Test(expected = BadFeesSpecificationException.class)
    public void testInsert_MissingHourlyPrice() throws ParkingAlreadyExistsException, BadFeesSpecificationException {
        Parking expectedParking;

        //Creating and inserting parking
        expectedParking = getTestParking("parkingId");
        expectedParking.setPricingType(PricingType.HOURLY_PRICING);
        expectedParking.getFees().setHourlyFee(null);
        parkingService.insert(expectedParking);
    }

    /**
     * Testing creation of a fixed/hourly billing parking when the parking object itself is missing fixed fee
     * information.
     * @throws ParkingAlreadyExistsException
     * @throws BadFeesSpecificationException
     */
    @Test(expected = BadFeesSpecificationException.class)
    public void testInsert_MissingFixedPrice() throws ParkingAlreadyExistsException, BadFeesSpecificationException {
        Parking expectedParking;

        //Creating and inserting parking
        expectedParking = getTestParking("parkingId");
        expectedParking.setPricingType(PricingType.HOURLY_FIXED_PRICING);
        expectedParking.getFees().setFixedFee(null);
        parkingService.insert(expectedParking);
    }

    /*********************** Testing ParkingService.addCar() function ***********************/

    /**
     * Testing the car park operation when given parking does not exists
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     */
    @Test(expected = ParkingNotFoundException.class)
    public void testAddCar_ParkingNotFound() throws ParkingNotAllowedException, CarAlreadyParkedException,
            ParkingNotFoundException, FullParkingException {
        Car car;

        car = getTestCar("carId");
        parkingService.addCar("parkingId", car);
    }

    /**
     * Mismatching car type and parking type
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     */
    @Test(expected = ParkingNotAllowedException.class)
    public void testAddCar_GasolineCarE20KWParking() throws ParkingNotAllowedException, CarAlreadyParkedException,
            ParkingNotFoundException, FullParkingException {
        testAddCar_ParkingNotAllowed(CarType.GASOLINE, ParkingType.E20KW_CAR_PARK);
    }

    /**
     * Mismatching car type and parking type
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     */
    @Test(expected = ParkingNotAllowedException.class)
    public void testAddCar_GasolineCarE50KWParking() throws ParkingNotAllowedException, CarAlreadyParkedException,
            ParkingNotFoundException, FullParkingException {
        testAddCar_ParkingNotAllowed(CarType.GASOLINE, ParkingType.E50KW_CAR_PARK);
    }

    /**
     * Mismatching car type and parking type
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     */
    @Test(expected = ParkingNotAllowedException.class)
    public void testAddCar_E20KWCarGasolineParking() throws ParkingNotAllowedException, CarAlreadyParkedException,
            ParkingNotFoundException, FullParkingException {
        testAddCar_ParkingNotAllowed(CarType.ELECTRIC_20KW, ParkingType.GASOLINE_CAR_PARK);
    }

    /**
     * Mismatching car type and parking type
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     */
    @Test(expected = ParkingNotAllowedException.class)
    public void testAddCar_E20KWCarE50KWParking() throws ParkingNotAllowedException, CarAlreadyParkedException,
            ParkingNotFoundException, FullParkingException {
        testAddCar_ParkingNotAllowed(CarType.ELECTRIC_20KW, ParkingType.E50KW_CAR_PARK);
    }

    /**
     * Mismatching car type and parking type
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     */
    @Test(expected = ParkingNotAllowedException.class)
    public void testAddCar_E50KWCarGasolineParking() throws ParkingNotAllowedException, CarAlreadyParkedException,
            ParkingNotFoundException, FullParkingException {
        testAddCar_ParkingNotAllowed(CarType.ELECTRIC_50KW, ParkingType.GASOLINE_CAR_PARK);
    }

    /**
     * Mismatching car type and parking type
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     */
    @Test(expected = ParkingNotAllowedException.class)
    public void testAddCar_E50KWCarE20KWParking() throws ParkingNotAllowedException, CarAlreadyParkedException,
            ParkingNotFoundException, FullParkingException {
        testAddCar_ParkingNotAllowed(CarType.ELECTRIC_50KW, ParkingType.E20KW_CAR_PARK);
    }

    /**
     * Generic method for testing mismatching car type and parking type
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     */
    private void testAddCar_ParkingNotAllowed(CarType carType, final ParkingType parkingType) throws ParkingNotAllowedException,
            CarAlreadyParkedException, ParkingNotFoundException, FullParkingException {
        Car car;

        when(parkingDaoMock.findById(any(String.class))).thenAnswer(new Answer<Parking>() {
            public Parking answer(InvocationOnMock invocation) {
                Parking parking = getTestParking((String) invocation.getArguments()[0]);
                parking.setParkingType(parkingType);
                return parking;
            }
        });

        car = getTestCar("carId");
        car.setCarType(carType);
        parkingService.addCar("parkingId", car);
    }

    /**
     * Test method behaviour when trying to park a car that is already parked in that parking.
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     */
    @Test(expected = CarAlreadyParkedException.class)
    public void testAddCar_CarAlreadyParked() throws ParkingNotAllowedException, CarAlreadyParkedException,
            ParkingNotFoundException, FullParkingException {
        Car car;
        final String carId = "carId";

        when(parkingDaoMock.findById(any(String.class))).thenAnswer(new Answer<Parking>() {
            public Parking answer(InvocationOnMock invocation) {
                Parking parking = getTestParking((String) invocation.getArguments()[0]);
                parking.getStatus().put(carId, Calendar.getInstance().getTime());
                return parking;
            }
        });

        car = getTestCar(carId);
        parkingService.addCar("parkingId", car);
    }

    /**
     * Test method behaviour when trying to park a car in a parking that is already full.
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     */
    @Test(expected = FullParkingException.class)
    public void testAddCar_ParkingFull() throws ParkingNotAllowedException, CarAlreadyParkedException,
            ParkingNotFoundException, FullParkingException {
        Car car;
        final String carId1 = "carId1";
        final String carId2 = "carId2";
        final String carId3 = "carId3";

        when(parkingDaoMock.findById(any(String.class))).thenAnswer(new Answer<Parking>() {
            public Parking answer(InvocationOnMock invocation) {
                Parking parking = getTestParking((String) invocation.getArguments()[0]);
                parking.getStatus().put(carId1, new Date(Calendar.getInstance().getTimeInMillis() - 100));
                parking.getStatus().put(carId2, new Date(Calendar.getInstance().getTimeInMillis() - 1000));
                parking.getStatus().put(carId3, new Date(Calendar.getInstance().getTimeInMillis() - 10000));
                parking.setCapacity(3);
                return parking;
            }
        });

        car = getTestCar("carId");
        parkingService.addCar("parkingId", car);
    }

    /**
     * Testing intended behaviour of method addCar()
     * @throws ParkingNotAllowedException
     * @throws CarAlreadyParkedException
     * @throws ParkingNotFoundException
     * @throws FullParkingException
     */
    @Test
    public void testAddCar() throws ParkingNotAllowedException, CarAlreadyParkedException,
            ParkingNotFoundException, FullParkingException {
        Car car;
        Ticket ticket;
        String carId = "carId";
        String parkingId = "parkingId";

        //Setting up mocks
        when(parkingDaoMock.findById(any(String.class))).thenAnswer(new Answer<Parking>() {
            public Parking answer(InvocationOnMock invocation) {
                Parking parking = getTestParking((String) invocation.getArguments()[0]);
                return parking;
            }
        });
        when(ticketServiceMock.insert(any(String.class), any(String.class), any(Date.class))).thenAnswer(new Answer<Ticket>() {
            public Ticket answer(InvocationOnMock invocation) {
                String parkingId = (String) invocation.getArguments()[0];
                String carId = (String) invocation.getArguments()[1];
                Date arrival = (Date) invocation.getArguments()[2];
                Ticket ticket = new Ticket();
                ticket.setCarId(carId);
                ticket.setParkingId(parkingId);
                ticket.setArrival(arrival);
                return ticket;
            }
        });

        //Parking car
        car = getTestCar(carId);
        ticket = parkingService.addCar(parkingId, car);

        //Checking result
        assertTrue(ticket.getCarId().equals(carId));
        assertTrue(ticket.getParkingId().equals(parkingId));
        assertNotNull(ticket.getArrival());
    }

    /*********************** Testing ParkingService.removeCar() function ***********************/

    /**
     * Testing the behaviour of the method when specified parking does not exist
     * @throws ParkingNotFoundException
     * @throws UnknownPricingPolicyException
     * @throws NotFoundCarException
     */
    @Test(expected = ParkingNotFoundException.class)
    public void testRemoveCar_ParkingNotFound() throws ParkingNotFoundException, UnknownPricingPolicyException,
            NotFoundCarException {
        String carId = "carId";
        String parkingId = "parkingId";

        parkingService.removeCar(parkingId, carId);
    }

    /**
     * Testing behaviour of the method when the specified car is not parked in given parking
     * @throws ParkingNotFoundException
     * @throws UnknownPricingPolicyException
     * @throws NotFoundCarException
     */
    @Test(expected = NotFoundCarException.class)
    public void testRemoveCar_CarNotFound() throws ParkingNotFoundException, UnknownPricingPolicyException,
            NotFoundCarException {
        String carId = "carId";
        final String carId1 = "carId1";
        final String carId2 = "carId2";
        final String carId3 = "carId3";
        String parkingId = "parkingId";

        when(parkingDaoMock.findById(any(String.class))).thenAnswer(new Answer<Parking>() {
            public Parking answer(InvocationOnMock invocation) {
                Parking parking = getTestParking((String) invocation.getArguments()[0]);
                parking.getStatus().put(carId1, new Date(Calendar.getInstance().getTimeInMillis() - 100));
                parking.getStatus().put(carId2, new Date(Calendar.getInstance().getTimeInMillis() - 1000));
                parking.getStatus().put(carId3, new Date(Calendar.getInstance().getTimeInMillis() - 10000));
                parking.setCapacity(3);
                return parking;
            }
        });
        parkingService.removeCar(parkingId, carId);
    }

    /**
     * Testing intended behaviour of the method removeCar()
     */
    @Test
    public void testRemoveCar() throws ParkingNotFoundException, UnknownPricingPolicyException,
            NotFoundCarException {
        String carId = "carId2";
        final String carId1 = "carId1";
        final String carId2 = "carId2";
        final String carId3 = "carId3";
        String parkingId = "parkingId";
        final String invoiceId = "invoiceId";
        final Double amount = 10.00;
        Invoice invoice;

        //Setting up mocks
        when(parkingDaoMock.findById(any(String.class))).thenAnswer(new Answer<Parking>() {
            public Parking answer(InvocationOnMock invocation) {
                Parking parking = getTestParking((String) invocation.getArguments()[0]);
                parking.getStatus().put(carId1, new Date(Calendar.getInstance().getTimeInMillis() - 100));
                parking.getStatus().put(carId2, new Date(Calendar.getInstance().getTimeInMillis() - 1000));
                parking.getStatus().put(carId3, new Date(Calendar.getInstance().getTimeInMillis() - 10000));
                parking.setCapacity(3);
                return parking;
            }
        });
        when(pricingPolicyServiceMock.getAmount(any(String.class), any(String.class), any(Date.class), any(Date.class),
                any(Fees.class))).thenReturn(amount);
        when(pricingPolicyFactoryMock.getPricingPolicy(any(PricingType.class))).thenReturn(pricingPolicyServiceMock);
        when(invoiceServiceMock.insert(any(String.class), any(String.class), any(Date.class), any(Date.class),
                any(Double.class))).thenAnswer(new Answer<Invoice>() {
            public Invoice answer(InvocationOnMock invocation) {
                Invoice invoice = new Invoice();
                invoice.setId(invoiceId);
                invoice.setParkingId((String) invocation.getArguments()[0]);
                invoice.setCarId((String) invocation.getArguments()[1]);
                invoice.setArrival((Date) invocation.getArguments()[2]);
                invoice.setDeparture((Date) invocation.getArguments()[3]);
                invoice.setAmount((Double) invocation.getArguments()[4]);
                return invoice;
            }
        });

        invoice = parkingService.removeCar(parkingId, carId);
        assertTrue(invoiceId.equals(invoice.getId()));
        assertTrue(parkingId.equals(invoice.getParkingId()));
        assertTrue(carId.equals(invoice.getCarId()));
        assertNotNull(invoice.getArrival());
        assertNotNull(invoice.getDeparture());
        assertTrue(invoice.getAmount().compareTo(amount) == 0);
    }
}