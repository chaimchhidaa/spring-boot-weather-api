package com.meteoapi.apimeteo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeocodingResult {
   private String name;
   private double latitude;
   private double longitude;

}
