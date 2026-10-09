package com.meteoapi.apimeteo.services;

import com.meteoapi.apimeteo.dtos.*;
import com.meteoapi.apimeteo.exceptions.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Data
public class WeatherService {
    private final RestClient restClient;
    @Value("${weather.api-url}")
    private String weatherApiUri;
    @Value("${weather.geocoding-url}")
    private String geocodingApiUri;
    //@Value("${weather.api-token}")
    //private String weatherApiToken;

    public WeatherService(RestClient restClient) {
        this.restClient = restClient;
    }


    public WeatherDto getWeather(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new CityMandatoryException("city doit etre renseigne");
        }
        GeocodingResponse geocodingresponse = getCoordinates(city);
        if (geocodingresponse.getResults() == null || geocodingresponse.getResults().isEmpty())
            throw new CityNotFoundException(city);
        GeocodingResult geocodingResult = geocodingresponse.getResults().get(0);
        String url = UriComponentsBuilder
                .fromUriString(weatherApiUri)
                .queryParam("latitude", geocodingResult.getLatitude())
                .queryParam("longitude", geocodingResult.getLongitude())
                .queryParam("current", "temperature_2m,relative_humidity_2m")
                .toUriString();
        WeatherResponse response;
        try {
            response = restClient.get()
                    .uri(url)
              //      .header("Authorization", "Bearer " + weatherApiToken)
                    .retrieve()
                    .body(WeatherResponse.class);
        } catch (RestClientException e) {
            throw new WeatherServiceDown(e.getMessage());
        }

        if (response == null || response.getCurrentWeather() == null)
            throw new WeatherServiceInvalidResponseException("Réponse invalide du service météo");
        return new WeatherDto(geocodingResult.getName(), response.getCurrentWeather().getTemperature_2m(), response.getCurrentWeather().getRelative_humidity_2m());
    }

    public GeocodingResponse getCoordinates(String city) {
        String url = UriComponentsBuilder
                .fromUriString(geocodingApiUri)
                .queryParam("name", city)
                .toUriString();
        GeocodingResponse coordinatesresponse = new GeocodingResponse();
        try {
            coordinatesresponse = restClient.get()
                    .uri(url)
                    .retrieve().body(GeocodingResponse.class);
        } catch (RestClientException e) {
            throw new GeoCodingServiceDownException(e.getMessage());
        }
        return coordinatesresponse;

    }
}
