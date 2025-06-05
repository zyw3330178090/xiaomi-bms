package com.zyw.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyw.entity.BatterySignal;
import com.zyw.exception.SignalException;
import com.zyw.mapper.BatterySignalMapper;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 1.电池预警信号请求解析处理器
 */
@Component(value = "requestValidateHandler")
public class RequestValidateHandler implements SignalAnalyseHandler {

    private SignalAnalyseHandler next;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    BatterySignalMapper batterySignalMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void handle(SignalAnalyseContext context) throws JsonProcessingException, SignalException, MQBrokerException, RemotingException, InterruptedException, MQClientException {
        // 1.获取电池信号字符串
        String signalStr = context.getRequest().getSignal();
        // 2.映射为电池信号对象
        BatterySignal batterySignal = mapper.readValue(signalStr, BatterySignal.class);
        // 3.保存到上下文
        context.setBatterySignal(batterySignal);
        // 4.保存到数据库,删除 Redis 缓存
        redisTemplate.delete(String.valueOf(context.getRequest().getCarId()));
        batterySignal.setVin(context.getRequest().getCarId());
        batterySignalMapper.insert(batterySignal);
        redisTemplate.delete(String.valueOf(context.getRequest().getCarId()));
        // 5.执行下一步
        if (next != null) {
            next.handle(context);
        }
    }

    @Override
    public void setNext(SignalAnalyseHandler next) {
        this.next = next;
    }

}
