package com.zyw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfo {
    private String vid;
    private Long vin;
    private String batteryType;
    private Double totalMileage;
    private Double batteryHealth;
}