package com.weather.forecast.controller.impl;

import com.weather.forecast.controller.ForecastProvider;
import com.weather.forecast.model.response.WeatherDataResponse;
import com.weather.forecast.service.ForecastService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
class ForecastControllerTest {

    private ForecastProvider forecastProvider;
    private ForecastService forecastService;

    @BeforeEach
    void init() {
        forecastService = mock(ForecastService.class);
        forecastProvider = new ForecastController(forecastService);
    }

    @Test
    void retrieveForecastOK() {
        WeatherDataResponse response = WeatherDataResponse.builder()
                .maximum(15L)
                .feelsLike(List.of(2.2, 3.4))
                .humidity(List.of(2L, 5L))
                .build();

        when(forecastService.retrieveForecast(any())).thenReturn(response);
        var result = forecastProvider.retrieveForecast("Milano");
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void retrieveForecastKO() {

        when(forecastService.retrieveForecast(any())).thenReturn(null);
        var result = forecastProvider.retrieveForecast("Milano");
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}