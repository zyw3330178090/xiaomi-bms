package com.zyw.service.handler.processor;

import com.zyw.entity.BatterySignal;
import com.zyw.entity.WarningResponse;
import com.zyw.entity.WarningSignal;
import com.zyw.exception.SignalException;
import com.zyw.mapper.RuleMapper;
import com.zyw.service.handler.SignalAnalyseContext;
import com.zyw.service.handler.processor.mq.Producer;
import com.zyw.service.handler.processor.parser.CurrentAlarmRuleParser;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 电流信号处理器
 */
@Component(value = "currentSignalProcessor")
public class CurrentSignalProcessor extends SignalProcessor {

    @Autowired
    Producer producer;

    @Autowired
    RuleMapper ruleMapper;

    @Override
    public void processSignal(SignalAnalyseContext context) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        // 1.判断是否需要电流预警
        if (context.getWarnName() != null && !context.getWarnName().equals("电流差报警")) {
            return;
        }
        // 2.获取电流参数
        BatterySignal batterySignal = context.getBatterySignal();
        Double lx = batterySignal.getIx();
        Double li = batterySignal.getIi();
        if (lx == null || li == null) {
            throw new SignalException("400", "电流参数不完整！");
        }
        // 3.判断预警等级
        String ruleStr = ruleMapper.getRule(2, context.getBatteryType());
        CurrentAlarmRuleParser parser = new CurrentAlarmRuleParser(ruleStr);
        Integer warnLevel = parser.calculateAlarmLevel(batterySignal);
        context.setWarnName("电流差报警");
        context.setWarnLevel(warnLevel);
        // 4.构造响应对象，填充属性，将响应对象保存到上下文的 responseList 中
        createResponse(context);
        // 5.发送预警消息到 MQ
        String warningSignalStr = createWarningSignalStr(context);
        Producer.sendMessage(warningSignalStr);
    }

}
