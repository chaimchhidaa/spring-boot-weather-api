package com.meteoapi.apimeteo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeocodingResponse {

    private List<GeocodingResult> results;


}
