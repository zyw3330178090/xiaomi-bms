package com.zyw.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zyw.service.handler.processor.SignalProcessor;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 4.获取预警等级处理器
 */
@Component(value = "warnLevelGetHandler")
public class WarnLevelGetHandler implements SignalAnalyseHandler{

    private SignalAnalyseHandler next;

    @Autowired
    private SignalProcessor voltageSignalProcessor;

    @Autowired
    private SignalProcessor currentSignalProcessor;

    @Override
    public void handle(SignalAnalyseContext context) throws JsonProcessingException, MQBrokerException, RemotingException, InterruptedException, MQClientException {
        voltageSignalProcessor.processSignal(context);
        currentSignalProcessor.processSignal(context);
        if(next!=null){
            next.handle(context);
        }
    }

    @Override
    public void setNext(SignalAnalyseHandler next) {
        this.next=next;
    }

}
