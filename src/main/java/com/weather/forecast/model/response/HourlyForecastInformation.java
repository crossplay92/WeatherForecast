package com.weather.forecast.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HourlyForecastInformation {
    private Long temp;
    private Long feelsLike;
    private Long humidity;
}
