package com.weather.forecast.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherDataResponse {
    private Long maximum;
    private List<Double> feelsLike;
    private List<Long> humidity;
}
