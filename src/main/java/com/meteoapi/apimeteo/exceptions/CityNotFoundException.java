package com.meteoapi.apimeteo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Data
public class CityNotFoundException extends RuntimeException {


    public CityNotFoundException(String cityName) {

        super("Ville inexistante : " + cityName);
    }
}
