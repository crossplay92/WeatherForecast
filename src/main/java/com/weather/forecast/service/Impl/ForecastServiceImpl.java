package com.weather.forecast.service.Impl;

import com.weather.forecast.model.GeoCity;
import com.weather.forecast.model.response.ForecastResponse;
import com.weather.forecast.model.response.HourlyForecastInformation;
import com.weather.forecast.model.response.WeatherDataResponse;
import com.weather.forecast.service.ForecastService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.weather.forecast.utility.Constants.*;


/**
 * Here you will find the core methods implementation
 */
@Service
@Slf4j
public class ForecastServiceImpl implements ForecastService {

    @Value("${forecast.apikey}")
    private String apiKey;

    @Value("${forecast.url.geo}")
    private String geoDataUrl;

    @Value("${forecast.url.weather}")
    private String forecastWeatherUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public ForecastServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public WeatherDataResponse retrieveForecast(String cityName) {
        log.info("Retrieving forecast for {} city", cityName);
        WeatherDataResponse response;

        GeoCity geoCityData = findGeoCityData(cityName);

        String uri = forecastWeatherUrl.replace(LONGITUDE_PLACEHOLDER, String.valueOf(geoCityData.getLon()))
                .replace(LATITUDE_PLACEHOLDER, String.valueOf(geoCityData.getLon()))
                .replace(API_KEY_PLACEHOLDER, apiKey);

        ForecastResponse information = restTemplate.getForObject(uri, ForecastResponse.class);
        if (information != null && information.getHourly() != null) {

            response = WeatherDataResponse.builder()
                    .maximum(information.getHourly().stream()
                            .max(Comparator.comparing(HourlyForecastInformation::getTemp))
                            .map(HourlyForecastInformation::getTemp).orElse(0L))
                    .feelsLike(information.getHourly().stream()
                            .map(HourlyForecastInformation::getFeelsLike)
                            .toList())
                    .humidity(information.getHourly().stream()
                            .map(HourlyForecastInformation::getHumidity)
                            .toList())
                    .build();
        } else {
            response = WeatherDataResponse.builder().build();
        }

        return response;
    }

    /**
     * Method used to retrieved city geo data given name
     *
     * @param cityName The input city name
     * @return Geo Data of the given city
     */
    private GeoCity findGeoCityData(String cityName) {
        String uri = geoDataUrl.replace(CITY_PLACEHOLDER, cityName)
                .replace(API_KEY_PLACEHOLDER, apiKey);

        List<GeoCity> list = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(uri, GeoCity[].class))).toList();
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        throw new NoSuchElementException(String.format("Unable to retrieve %s city geo data", cityName));
    }
}
