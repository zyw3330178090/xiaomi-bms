package com.zyw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarningRequest {
    private Long carId;             // 车架编号
    private Integer warnId;         // 规则编号
    private String signal;          // 电池信号字符串
}
