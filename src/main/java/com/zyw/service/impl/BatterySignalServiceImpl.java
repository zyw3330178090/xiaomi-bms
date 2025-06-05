package com.zyw.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyw.entity.BatterySignal;
import com.zyw.entity.WarningRequest;
import com.zyw.entity.WarningResponse;
import com.zyw.exception.SignalException;
import com.zyw.mapper.BatterySignalMapper;
import com.zyw.mapper.RuleMapper;
import com.zyw.mapper.VehicleInfoMapper;
import com.zyw.service.BatterySignalService;
import com.zyw.service.handler.SignalAnalyseContext;
import com.zyw.service.handler.SignalAnalyseHandler;
import jakarta.annotation.PostConstruct;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BatterySignalServiceImpl implements BatterySignalService {

    @Autowired
    SignalAnalyseHandler signalAnalyseHandler;

    @Autowired
    BatterySignalMapper batterySignalMapper;

    @Autowired
    VehicleInfoMapper vehicleInfoMapper;

    @Autowired
    RuleMapper ruleMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @PostConstruct
    public void flushRedis(){
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Override
    public List<WarningResponse> submitBatterySignal(List<WarningRequest> requests) throws JsonProcessingException, SignalException, MQBrokerException, RemotingException, InterruptedException, MQClientException {
        // 1.创建 responseList，用于返回数据
        List<WarningResponse> responseList = new ArrayList<>();
        // 2.依次处理每个请求（处理过程采用责任链模式）
        for (WarningRequest request : requests) {
            SignalAnalyseContext context = new SignalAnalyseContext();
            context.setRequest(request);
            context.setResponseList(responseList);
            signalAnalyseHandler.handle(context);
        }
        // 3.返回结果
        return responseList;
    }

    @Override
    public BatterySignal getBatterySignalByVin(Long vin) throws JsonProcessingException {
        String key = String.valueOf(vin);
        // 1.查询 Redis
        String batterySignalJson = redisTemplate.opsForValue().get(key);
        if (batterySignalJson != null && !batterySignalJson.isEmpty()) {
            // 将 JSON 反序列化为 BatterySignal 对象
            return objectMapper.readValue(batterySignalJson, BatterySignal.class);
        }
        // 2.查询数据库
        BatterySignal batterySignal = batterySignalMapper.getBatterySignalByVin(vin);
        if (batterySignal != null) {
            // 将查询结果存入 Redis
            String json = objectMapper.writeValueAsString(batterySignal);
            redisTemplate.opsForValue().set(key, json);
            return batterySignal;
        }
        // 3.查询失败，缓存空值
        redisTemplate.opsForValue().set(key, "", 10, TimeUnit.MINUTES);
        return null;
    }

}
