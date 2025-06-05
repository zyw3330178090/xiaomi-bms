package com.zyw;

import com.alibaba.fastjson.JSONObject;
import com.zyw.entity.WarningSignal;
import com.zyw.mapper.WarningSignalMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

@SpringBootApplication
public class ConsumerApplication implements CommandLineRunner {

    @Autowired
    private WarningSignalMapper warningSignalMapper;

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(ConsumerApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
    }

    public void startConsumer() throws Exception {
        // 1.构建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_4");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.subscribe("Warning", "*");

        // 2.注册消息监听器
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            msgs.forEach(messageExt -> {
                WarningSignal warningSignal = null;
                try {
                    System.out.println(new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET));
                    warningSignal = JSONObject.parseObject(
                            new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET),
                            WarningSignal.class);
                    System.out.println(warningSignal);
                    handleWarningSignal(warningSignal);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            });
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        // 3.启动消费者
        consumer.start();
        System.out.println("Consumer Started");
    }

    @Override
    public void run(String... args) throws Exception {
        startConsumer();
    }

    private void handleWarningSignal(WarningSignal warningSignal) {
        warningSignalMapper.insert(warningSignal);
    }
}