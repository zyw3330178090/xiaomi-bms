package com.zyw.service.handler;

import com.zyw.entity.WarningRequest;
import com.zyw.entity.WarningResponse;
import com.zyw.entity.BatterySignal;
import lombok.Data;

import java.util.List;

@Data
public class SignalAnalyseContext {
    private WarningRequest request;
    private BatterySignal batterySignal;
    private String batteryType;
    private String warnName;
    private Integer warnLevel;
    private List<WarningResponse> responseList;
}
