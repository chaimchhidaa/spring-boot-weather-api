package com.meteoapi.apimeteo.exceptions;


import com.meteoapi.apimeteo.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCityNotFound(CityNotFoundException cityNotFoundException) {
        return buildErrorResponse(HttpStatus.NOT_FOUND,cityNotFoundException.getMessage());
    }

    @ExceptionHandler(WeatherServiceDown.class)
    public ResponseEntity<ErrorResponse> handleWeatherServiceDownException(WeatherServiceDown weatherServiceDown) {
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE,weatherServiceDown.getMessage());

    }
    @ExceptionHandler(CityMandatoryException.class)
    public ResponseEntity<ErrorResponse> handleException(CityMandatoryException  cityMandatoryException) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST,cityMandatoryException.getMessage());
    }

    @ExceptionHandler(GeoCodingServiceDownException.class)
    public ResponseEntity<ErrorResponse> handleGeoCodingServiceDownException(GeoCodingServiceDownException geoCodingServiceDownException) {
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE,geoCodingServiceDownException.getMessage());
    }

    public ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {

        ErrorResponse errorResponse=new ErrorResponse(message,status.value()) ;

        return ResponseEntity.status(status).body(errorResponse);

    }


    @ExceptionHandler(WeatherServiceInvalidResponseException.class)
    public ResponseEntity<ErrorResponse> handleWeatherServiceInvalidResponseException(WeatherServiceInvalidResponseException weatherServiceInvalidResponseException) {
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE,weatherServiceInvalidResponseException.getMessage());
    }
}
