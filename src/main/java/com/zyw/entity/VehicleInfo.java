package com.zyw.entity;

import lombok.Data;

@Data
public class VehicleInfo {
    private String vid;
    private Long vin;
    private String batteryType;
    private Double totalMileage;
    private Double batteryHealth;
}