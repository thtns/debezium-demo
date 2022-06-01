package com.thtns.debezium.demo.controller;

import com.google.common.collect.Maps;
import com.thtns.debezium.demo.service.DebeziumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author thtns
 * @description 手动全量同步
 * @date 2022/4/26
 */
@RestController
@RequestMapping(value = "debezium/full/sync")
public class DebeziumFullSyncController {

    private DebeziumService debeziumService;

    @RequestMapping("bizCard")
    public Map<String, Object> bizCard() {
        debeziumService.bizCardFullSync();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("code", "200");
        map.put("msg", "bizCard 全量同步正在异步处理中，同步结果请查询日志");
        return map;
    }

    @Autowired
    public void setDebeziumService(DebeziumService debeziumService) {
        this.debeziumService = debeziumService;
    }
}
