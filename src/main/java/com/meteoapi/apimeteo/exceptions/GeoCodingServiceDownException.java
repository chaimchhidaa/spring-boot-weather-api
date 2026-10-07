package com.meteoapi.apimeteo.exceptions;

public class GeoCodingServiceDownException extends RuntimeException {


    public GeoCodingServiceDownException(String message) {

        super("GeoCoding service down: " + message);
    }
}
