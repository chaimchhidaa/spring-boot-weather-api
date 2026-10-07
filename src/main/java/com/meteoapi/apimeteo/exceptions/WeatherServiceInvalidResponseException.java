package com.meteoapi.apimeteo.exceptions;

public class WeatherServiceInvalidResponseException extends RuntimeException {
  public WeatherServiceInvalidResponseException(String message) {
    super(message);
  }
}

