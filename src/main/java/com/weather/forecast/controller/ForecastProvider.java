package com.weather.forecast.controller;


import com.weather.forecast.model.response.WeatherDataResponse;
import org.springframework.http.ResponseEntity;

/**
 * Interface for the controller, here you will find the exposed api
 */
public interface ForecastProvider {

    ResponseEntity<WeatherDataResponse> retrieveForecast(String cityName);
}
