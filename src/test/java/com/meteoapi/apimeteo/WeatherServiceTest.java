package com.meteoapi.apimeteo;

import com.meteoapi.apimeteo.dtos.*;
import com.meteoapi.apimeteo.exceptions.CityMandatoryException;
import com.meteoapi.apimeteo.exceptions.CityNotFoundException;
import com.meteoapi.apimeteo.services.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {
    @Mock
    private RestClient restClient;
    private WeatherService weatherService;
    @Mock
    private RestClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;
    @Mock
    private RestClient.RequestHeadersSpec<?> requestHeadersSpec;
    @Mock
    private RestClient.ResponseSpec responseSpec;
    @BeforeEach void setUp(){
        weatherService = new WeatherService(restClient);

    }

    @Test
    void shouldThrowExceptionWhenCityIsEmpty() {
        CityMandatoryException cityMandatoryException = assertThrows(CityMandatoryException.class, () ->
                weatherService.getWeather("")
        );
        assertEquals("city doit etre renseigne", cityMandatoryException.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenCityIsNotFound() {
        weatherService.setGeocodingApiUri("http://test");
        GeocodingResponse response = new GeocodingResponse();
        response.setResults(Collections.emptyList());
        doReturn(requestHeadersUriSpec)
                .when(restClient)
                .get();

        doReturn(requestHeadersSpec)
                .when(requestHeadersUriSpec)
                .uri(anyString());

        when(requestHeadersSpec.retrieve())
                .thenReturn(responseSpec);

        when(responseSpec.body(GeocodingResponse.class))
                .thenReturn(response);
        CityNotFoundException cityNotFoundException = assertThrows(
                CityNotFoundException.class,
                () -> weatherService.getWeather("sjfhnsh")
        );
        assertEquals("Ville inexistante : sjfhnsh", cityNotFoundException.getMessage());

    }

    @Test
    void shouldReturnWeatherWhenCityIsFound() {
        weatherService.setGeocodingApiUri("http://testgecoding");
        weatherService.setWeatherApiUri("http://test");
        GeocodingResult geocodingResult = new GeocodingResult();
        geocodingResult.setName("Tunis");
        geocodingResult.setLatitude(10.2);
        geocodingResult.setLongitude(22.3);
        GeocodingResponse response = new GeocodingResponse();
        response.setResults(List.of(geocodingResult));
        doReturn(requestHeadersUriSpec)
                .when(restClient)
                .get();

        doReturn(requestHeadersSpec)
                .when(requestHeadersUriSpec)
                .uri(anyString());

        when(requestHeadersSpec.retrieve())
                .thenReturn(responseSpec);

        when(responseSpec.body(GeocodingResponse.class))
                .thenReturn(response);
        WeatherResponse weatherResponse = new WeatherResponse();
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setRelative_humidity_2m(12.5);
        currentWeather.setTemperature_2m(45.0);
        weatherResponse.setCurrentWeather(currentWeather);
        when(responseSpec.body(WeatherResponse.class))
                .thenReturn(weatherResponse);
        WeatherDto response2 = weatherService.getWeather("Tunis");
        assertNotNull(response2);
        assertEquals("Tunis",response2.getCity());
        assertEquals(12.5,response2.getHumidity());
        assertEquals(45.0,response2.getTemperature());


    }
}
