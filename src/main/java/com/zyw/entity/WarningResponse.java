package com.zyw.entity;

import lombok.Data;

@Data
public class WarningResponse {
    private Long vin;
    private String batteryType;
    private String warnName;
    private Integer warnLevel;
}
