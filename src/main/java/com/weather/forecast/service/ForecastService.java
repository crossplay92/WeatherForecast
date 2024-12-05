package com.weather.forecast.service;

import com.weather.forecast.model.response.WeatherDataResponse;

/**
 * Service interface, here you will find the core methods
 */
public interface ForecastService {
    WeatherDataResponse retrieveForecast(String cityName);
}
