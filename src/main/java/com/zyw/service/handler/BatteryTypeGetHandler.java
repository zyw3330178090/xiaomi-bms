package com.zyw.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zyw.exception.SignalException;
import com.zyw.mapper.VehicleInfoMapper;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 2.获取电池类别处理器
 */
@Component(value = "batteryTypeGetHandler")
public class BatteryTypeGetHandler implements SignalAnalyseHandler{

    private SignalAnalyseHandler next;

    @Autowired
    VehicleInfoMapper vehicleInfoMapper;

    @Override
    public void handle(SignalAnalyseContext context) throws JsonProcessingException, SignalException, MQBrokerException, RemotingException, InterruptedException, MQClientException {
        // 1.数据库查找电池类型
        String batteryType=vehicleInfoMapper.getBatteryTypeByVin(context.getRequest().getCarId());
        // 2.保存到上下文
        context.setBatteryType(batteryType);
        // 3.执行下一步
        if(next!=null){
            next.handle(context);
        }
    }

    @Override
    public void setNext(SignalAnalyseHandler next) {
        this.next=next;
    }

}
