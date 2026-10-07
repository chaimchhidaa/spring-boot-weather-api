package com.meteoapi.apimeteo.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeatherResponse {

    @JsonProperty("current")
    private CurrentWeather  currentWeather;



}
