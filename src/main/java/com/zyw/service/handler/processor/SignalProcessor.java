package com.zyw.service.handler.processor;

import com.alibaba.fastjson.JSONObject;
import com.zyw.entity.WarningResponse;
import com.zyw.entity.WarningSignal;
import com.zyw.service.handler.SignalAnalyseContext;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 信号处理器抽象类
 */
public abstract class SignalProcessor {

    public abstract void processSignal(SignalAnalyseContext context) throws MQBrokerException, RemotingException, InterruptedException, MQClientException;

    protected static void createResponse(SignalAnalyseContext context){
        Long carId = context.getRequest().getCarId();
        String batteryType = context.getBatteryType();
        WarningResponse response = new WarningResponse();
        response.setVin(carId);
        response.setBatteryType(batteryType);
        String warnName = context.getWarnName();
        response.setWarnName(context.getWarnName());
        response.setWarnLevel(context.getWarnLevel());
        context.getResponseList().add(response);
    }

    protected static String createWarningSignalStr(SignalAnalyseContext context){
        WarningSignal warningSignal=new WarningSignal();
        warningSignal.setVin(context.getRequest().getCarId());
        warningSignal.setWarnName(context.getWarnName());
        warningSignal.setWarnLevel(context.getWarnLevel());
        return JSONObject.toJSONString(warningSignal);
    }
}
