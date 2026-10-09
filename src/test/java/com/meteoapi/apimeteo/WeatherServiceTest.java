package com.meteoapi.apimeteo;

import com.meteoapi.apimeteo.dtos.GeocodingResponse;
import com.meteoapi.apimeteo.exceptions.CityMandatoryException;
import com.meteoapi.apimeteo.exceptions.CityNotFoundException;
import com.meteoapi.apimeteo.services.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void shouldThrowExceptionWhenCityIsEmpty() {
        weatherService = new WeatherService(restClient);
        CityMandatoryException cityMandatoryException=assertThrows(CityMandatoryException.class, () ->
            weatherService.getWeather("")
        );
        assertEquals("city doit etre renseigne", cityMandatoryException.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenCityIsNotFound() {
        weatherService = new WeatherService(restClient);
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
}
