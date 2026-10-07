package com.meteoapi.apimeteo.controllers;

import com.meteoapi.apimeteo.dtos.CurrentWeather;
import com.meteoapi.apimeteo.dtos.GeocodingResponse;
import com.meteoapi.apimeteo.dtos.WeatherDto;
import com.meteoapi.apimeteo.services.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@AllArgsConstructor


public class WeatherController {
    private final WeatherService weatherService;


    @GetMapping
    public WeatherDto getWeather(@RequestParam(defaultValue = " ") String city) {
        return weatherService.getWeather(city);
    }

    @GetMapping("/coordinates")
    public GeocodingResponse getCoordinate(@RequestParam String city) {
        return weatherService.getCoordinates(city);
    }


}
