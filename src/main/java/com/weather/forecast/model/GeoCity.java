package com.weather.forecast.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the city data
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoCity {
    private String name;
    private Double lat;
    private Double lon;
    private String country;
    private String state;

}
