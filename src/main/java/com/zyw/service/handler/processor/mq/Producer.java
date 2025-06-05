package com.zyw.service.handler.processor.mq;

import jakarta.annotation.PostConstruct;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 */
@Component
public class Producer {

    private static DefaultMQProducer producer;

    @PostConstruct
    public void init() throws MQClientException {
        // 1.初始化消息生产者
        producer = new DefaultMQProducer("defaultProducer");
        // 2.指定 nameserver 地址
        producer.setNamesrvAddr("localhost:9876");
        // 3.启动消息生产者
        producer.start();
    }

    public static void sendMessage(String warningSignalStr) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 4.创建消息
        Message msg = new Message("Warning", "TagA",
                warningSignalStr.getBytes());
        // 5.发送消息
        producer.send(msg);

    }

    public void shutDown(){
        // 6.消息发送完后，停止消息生产者服务
        producer.shutdown();
    }

}
