package com.zyw.entity;

import lombok.Data;

@Data
public class WarningSignal {
    private Long id;
    private Long vin;
    private String warnName;
    private Integer warnLevel;
}
