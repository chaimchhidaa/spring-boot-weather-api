package com.meteoapi.apimeteo.exceptions;

public class CityMandatoryException extends RuntimeException {
    public CityMandatoryException(String message) {

        super( message);
    }
}
