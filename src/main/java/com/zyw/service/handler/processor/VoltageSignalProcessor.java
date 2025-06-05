package com.zyw.service.handler.processor;

import com.zyw.entity.BatterySignal;
import com.zyw.entity.WarningResponse;
import com.zyw.exception.SignalException;
import com.zyw.mapper.RuleMapper;
import com.zyw.service.handler.SignalAnalyseContext;
import com.zyw.service.handler.processor.mq.Producer;
import com.zyw.service.handler.processor.parser.VoltageAlarmRuleParser;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 电压信号处理器
 */
@Component(value = "voltageSignalProcessor")
public class VoltageSignalProcessor extends SignalProcessor {

    @Autowired
    RuleMapper ruleMapper;

    @Override
    public void processSignal(SignalAnalyseContext context) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        // 1.判断是否需要电压预警
        if (context.getWarnName() != null && !context.getWarnName().equals("电压差报警")) {
            return;
        }
        // 2.获取电压参数
        BatterySignal batterySignal = context.getBatterySignal();
        if (batterySignal.getMi() == null || batterySignal.getMx() == null) {
            throw new SignalException("400", "电压参数不完整！");
        }
        // 3.判断预警等级
        String ruleStr = ruleMapper.getRule(1, context.getBatteryType());
        if(ruleStr==null){
            throw new SignalException("400", "电池信号不存在");
        }
        VoltageAlarmRuleParser parser = new VoltageAlarmRuleParser(ruleStr);
        boolean flag=context.getWarnName()==null;
        context.setWarnName("电压差报警");
        Integer warnLevel = parser.calculateAlarmLevel(batterySignal);
        context.setWarnLevel(warnLevel);
        // 4.构造响应对象，填充属性，将响应对象保存到上下文的 responseList 中
        createResponse(context);
        // 5.发送预警消息到 MQ
        String warningSignalStr = createWarningSignalStr(context);
        Producer.sendMessage(warningSignalStr);
        if(flag){
            context.setWarnName(null);
        }
    }

}
