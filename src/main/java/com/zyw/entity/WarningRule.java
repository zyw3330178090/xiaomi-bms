package com.zyw.entity;

import lombok.Data;

@Data
public class WarningRule {
    private Long id;
    private Integer ruleId;
    private String ruleName;
    private String batteryType;
    private Integer warnLevel;
}
