package com.weather.forecast.service.Impl;

import com.weather.forecast.model.GeoCity;
import com.weather.forecast.model.response.ForecastResponse;
import com.weather.forecast.model.response.HourlyForecastInformation;
import com.weather.forecast.service.ForecastService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.properties")
@Import(Properties.class)
class ForecastServiceImplTest {

    private static final String GEO_URI = "http://api.openweathermap.org/geo/1.0/direct?q=Milano&limit=1&appid=XXX";
    private static final String FORECAST_URI = "https://api.openweathermap.org/data/3.0/onecall?lat=17.5&lon=17.5&units=metric&appid=XXX&exclude=current,minutely,daily,alerts";

    private RestTemplate restTemplate;
    private ForecastService forecastService;

    @BeforeEach
    void init() {
        restTemplate = mock(RestTemplate.class);
        forecastService = new ForecastServiceImpl(restTemplate);
        ReflectionTestUtils.setField(forecastService, "apiKey", "XXX");
        ReflectionTestUtils.setField(forecastService, "geoDataUrl", "http://api.openweathermap.org/geo/1.0/direct?q={city}&limit=1&appid={apikey}");
        ReflectionTestUtils.setField(forecastService, "forecastWeatherUrl", "https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&units=metric&appid={apikey}&exclude=current,minutely,daily,alerts");
    }

    @Test
    void retrieveWeatherInformationOk() {
        GeoCity[] geoCities = {
                new GeoCity("Milano", 2.2, 17.5, "Lombardia", "Italia")
        };
        when(restTemplate.getForObject(GEO_URI, GeoCity[].class)).thenReturn(geoCities);
        when(restTemplate.getForObject(FORECAST_URI, ForecastResponse.class)).thenReturn(ForecastResponse.builder()
                .hourly(List.of(HourlyForecastInformation.builder()
                                .temp(12L)
                                .feelsLike(50.5)
                                .humidity(55L)
                                .build(),
                        HourlyForecastInformation.builder()
                                .temp(15L)
                                .feelsLike(50.5)
                                .humidity(55L)
                                .build(),
                        HourlyForecastInformation.builder()
                                .temp(2L)
                                .feelsLike(50.5)
                                .humidity(55L)
                                .build())
                )
                .build());

        var result = forecastService.retrieveForecast("Milano");
        assertNotNull(result);
        assertEquals(15L, result.getMaximum());
    }

    @Test
    void retrieveWeatherInformationKoGeoCity() {
        GeoCity[] geoCities = new GeoCity[0];

        when(restTemplate.getForObject(GEO_URI, GeoCity[].class)).thenReturn(geoCities);

        Throwable exception = assertThrows(NoSuchElementException.class, () -> forecastService.retrieveForecast("Milano"));
        assertEquals("Unable to retrieve Milano city geo data", exception.getMessage());
    }

    @Test
    void retrieveWeatherInformationEmptyForecast() {
        GeoCity[] geoCities = {
                new GeoCity("Milano", 2.2, 17.5, "Lombardia", "Italia")
        };
        when(restTemplate.getForObject(GEO_URI, GeoCity[].class)).thenReturn(geoCities);
        when(restTemplate.getForObject(FORECAST_URI, ForecastResponse.class)).thenReturn(ForecastResponse.builder().build());

        var result = forecastService.retrieveForecast("Milano");
        assertNull(result);
    }

}