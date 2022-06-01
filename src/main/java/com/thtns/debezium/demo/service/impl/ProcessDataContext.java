package com.thtns.debezium.demo.service.impl;

import com.thtns.debezium.demo.model.DebeziumListenerModel;
import com.thtns.debezium.demo.service.ProcessDataStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author thtns
 * @description 策略中转
 * @date 2022/4/20
 */
@Service
public class ProcessDataContext {

    private final Map<String, ProcessDataStrategy> map;

    //通过Autowired获取所有实现ProcessDataStrategy的类
    public ProcessDataContext(Map<String, ProcessDataStrategy> map) {
        this.map = map;
    }

    public void getDataStrategy(DebeziumListenerModel debeziumListenerModel) {
        map.get(debeziumListenerModel.getTable()).process(debeziumListenerModel);
    }

}
