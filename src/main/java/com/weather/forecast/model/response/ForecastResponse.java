package com.weather.forecast.model.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForecastResponse {

    private List<HourlyForecastInformation> hourly;
}
