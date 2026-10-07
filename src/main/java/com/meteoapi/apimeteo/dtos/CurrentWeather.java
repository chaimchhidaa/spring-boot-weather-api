package com.meteoapi.apimeteo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentWeather {

    private Double temperature_2m;
    private Double relative_humidity_2m;
}
