package com.zyw.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zyw.entity.BatterySignal;
import com.zyw.entity.WarningRequest;
import com.zyw.entity.WarningResponse;
import com.zyw.exception.SignalException;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public interface BatterySignalService {

    List<WarningResponse> submitBatterySignal(List<WarningRequest> requests) throws JsonProcessingException, SignalException, MQBrokerException, RemotingException, InterruptedException, MQClientException;

    BatterySignal getBatterySignalByVin(Long vin) throws JsonProcessingException;

}
