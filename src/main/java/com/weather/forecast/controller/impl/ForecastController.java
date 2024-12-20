package com.weather.forecast.controller.impl;

import com.weather.forecast.controller.ForecastProvider;
import com.weather.forecast.model.response.WeatherDataResponse;
import com.weather.forecast.service.ForecastService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * This is the Controller, here you will find all the implementation of the exposed API
 */
@RestController
@RequestMapping("/weather")
public class ForecastController implements ForecastProvider {

    private final ForecastService forecastService;

    @Autowired
    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }


    @Override
    @GetMapping(value = "/forecast/{cityName}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Operation(description = "This API retrieves weather forecast information given a city name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forecast information retrieved"),
    })
    public ResponseEntity<WeatherDataResponse> retrieveForecast(@PathVariable("cityName") String cityName) {
        WeatherDataResponse forecastResponse = forecastService.retrieveForecast(cityName);

        return (Objects.nonNull(forecastResponse))
                ? ResponseEntity.status(HttpStatus.OK).body(forecastResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
