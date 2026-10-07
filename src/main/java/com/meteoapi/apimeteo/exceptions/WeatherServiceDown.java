package com.meteoapi.apimeteo.exceptions;

public class WeatherServiceDown extends RuntimeException {



    public WeatherServiceDown(String message) {

        super("Weather service down: " + message);
    }
}


