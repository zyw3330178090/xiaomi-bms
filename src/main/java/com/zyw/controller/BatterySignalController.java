package com.zyw.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zyw.entity.*;
import com.zyw.exception.SignalException;
import com.zyw.service.BatterySignalService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BatterySignalController {

    @Autowired
    BatterySignalService batterySignalService;

    @PostMapping("/warn")
    public Result<List<WarningResponse>> submitBatterySignal(
            @RequestBody List<WarningRequest> requests) throws JsonProcessingException, MQBrokerException, RemotingException, InterruptedException, MQClientException {
        List<WarningResponse> responseList;
        try {
            responseList = batterySignalService.submitBatterySignal(requests);
        } catch (SignalException exception) {
            return Result.error(exception.getMessage());
        }
        return Result.success(responseList);
    }

    @GetMapping("/{vin}")
    public Result<BatterySignal> getBatterySignalByVin(@PathVariable Long vin) throws JsonProcessingException {
        BatterySignal batterySignal = batterySignalService.getBatterySignalByVin(vin);
        return Result.success(batterySignal);
    }


}
