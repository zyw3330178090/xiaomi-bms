package com.zyw.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zyw.exception.SignalException;
import com.zyw.mapper.RuleMapper;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 3.获取预警名称处理器
 */
@Component(value = "warnTypeGetHandler")
public class WarnTypeGetHandler implements SignalAnalyseHandler{

    private SignalAnalyseHandler next;

    @Autowired
    RuleMapper ruleMapper;

    @Override
    public void handle(SignalAnalyseContext context) throws JsonProcessingException, SignalException, MQBrokerException, RemotingException, InterruptedException, MQClientException {
        // 1.获取预警规则名称
        String warnName=ruleMapper.getWarnNameByWarnId(context.getRequest().getWarnId());
        // 2.保存到上下文
        context.setWarnName(warnName);
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
