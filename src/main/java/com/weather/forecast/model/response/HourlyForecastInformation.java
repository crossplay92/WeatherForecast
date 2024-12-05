package com.weather.forecast.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class HourlyForecastInformation {

    private Long temp;
    @JsonProperty("feels_like")
    private Double feelsLike;
    private Long humidity;

}
