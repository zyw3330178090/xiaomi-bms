package com.zyw.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 信号分析处理器接口
 */
public interface SignalAnalyseHandler {

    void handle(SignalAnalyseContext orderCreateContext) throws JsonProcessingException, MQBrokerException, RemotingException, InterruptedException, MQClientException;
    void setNext(SignalAnalyseHandler orderCreateHandler);

}
