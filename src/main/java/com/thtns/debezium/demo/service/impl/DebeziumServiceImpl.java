package com.thtns.debezium.demo.service.impl;

import com.thtns.debezium.demo.service.DebeziumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @author thtns
 * @description
 * @date 2022/4/26
 */
@Slf4j
@Service
public class DebeziumServiceImpl implements DebeziumService {

    private BizCardStrategyImpl bizCardStrategy;

    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void bizCardFullSync() {
        log.info("biz_card  full sync  start     ----------");
        taskExecutor.execute(() -> bizCardStrategy.fullSync());
    }


    @Autowired
    public void setNsrBqStrategy(BizCardStrategyImpl bizCardStrategy) {
        this.bizCardStrategy = bizCardStrategy;
    }

    @Autowired
    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
}
