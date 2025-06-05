package com.zyw.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyw.service.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean 配置
 */
@Configuration
public class BeanConfig {

    /**
     * 信号处理器链
     */
    @Bean(value = "signalAnalyseHandler")
    public SignalAnalyseHandler signalAnalyseHandlerChain(
            RequestValidateHandler requestValidateHandler,
            BatteryTypeGetHandler batteryTypeGetHandler,
            WarnTypeGetHandler warnTypeGetHandler,
            WarnLevelGetHandler warnLevelGetHandler) {
        requestValidateHandler.setNext(batteryTypeGetHandler);
        batteryTypeGetHandler.setNext(warnTypeGetHandler);
        warnTypeGetHandler.setNext(warnLevelGetHandler);
        return requestValidateHandler;
    }

    /**
     * 对象映射器
     */
    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

}
