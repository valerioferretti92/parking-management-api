package com.valerioferretti.parking.exceptions.handler;

import com.valerioferretti.parking.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class ParkingManagementExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiError> handleGenericException(Exception ex, WebRequest request) {
        String errorMessage;

        ex.printStackTrace();
        errorMessage = "Internal server error occurred.";
        return buildResponse(errorMessage, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ParkingNotFoundException.class)
    public final ResponseEntity<ApiError> handleParkingNotFoundException(ParkingNotFoundException ex, WebRequest request) {
        String errorMessage;

        ex.printStackTrace();
        errorMessage = "Parking " + ex.getParkingId() + " does not exist.";
        return buildResponse(errorMessage, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ParkingAlreadyExistsException.class)
    public final ResponseEntity<ApiError> handleParkingAlreadyExistsException(ParkingAlreadyExistsException ex, WebRequest request) {
        String errorMessage;

        ex.printStackTrace();
        errorMessage = "Parking " + ex.getParkingId() + " already exists.";
        return buildResponse(errorMessage, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarAlreadyParkedException.class)
    public final ResponseEntity<ApiError> handleCarAlreadyParkedException(CarAlreadyParkedException ex, WebRequest request) {
        String errorMessage;

        ex.printStackTrace();
        errorMessage = "Car " + ex.getCarId() + " is already parked in parking " + ex.getParkingId() + ".";
        return buildResponse(errorMessage, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FullParkingException.class)
    public final ResponseEntity<ApiError> handleFullParkingException(FullParkingException ex, WebRequest request) {
        String errorMessage;

        errorMessage = "Parking " + ex.getParkingId() + " is already full.";
        return buildResponse(errorMessage, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundCarException.class)
    public final ResponseEntity<ApiError> handleNotFoundCarException(NotFoundCarException ex, WebRequest request) {
        String errorMessage;

        errorMessage = "Car " + ex.getCarId() + " is not parked in parking" + ex.getParkingId() + ".";
        return buildResponse(errorMessage, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ParkingNotAllowedException.class)
    public final ResponseEntity<ApiError> handleParkingNotAllowedException(ParkingNotAllowedException ex, WebRequest request) {
        String errorMessage;

        errorMessage = "Car " + ex.getCarId() + " is not allowed to park in parking " + ex.getParkingId() + ".";
        return buildResponse(errorMessage, request, HttpStatus.BAD_REQUEST);
    }

    private final ResponseEntity<ApiError> buildResponse(String errorMessage, WebRequest request, HttpStatus httpStatus) {
        ApiError apiError;
        String details;

        details = (request != null) ? request.getDescription(false) : null;
        apiError = new ApiError(errorMessage, details, new Date());
        return new ResponseEntity<ApiError>(apiError, httpStatus);
    }
}
