package com.thtns.debezium.demo.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thtns.debezium.demo.mapper.BizCardMapper;
import com.thtns.debezium.demo.model.BizCard;
import com.thtns.debezium.demo.model.DebeziumListenerModel;
import com.thtns.debezium.demo.service.ProcessDataStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author thtns
 * @description 业务实现类
 * @date 2022/4/20
 */
@Service("biz_card")
@Slf4j
public class BizCardStrategyImpl implements ProcessDataStrategy {

    private BizCardMapper bizCardMapper;

    private ObjectMapper objectMapper;


    @Override
    @SneakyThrows
    public void process(DebeziumListenerModel debeziumListenerModel) {
        log.info("debezium biz_card 增量数据,开始处理 ~~~~~~~~~~~~");
        log.info("json data :{}", JSONUtil.toJsonStr(debeziumListenerModel));

        String data = debeziumListenerModel.getData();
        BizCard bizCard = objectMapper.readValue(objectMapper.createParser(data), BizCard.class);

        // 增量处理
        handle(Collections.singletonList(bizCard));

        log.info("debezium biz_card 增量数据处理完成");

    }


    /**
     * 全量同步
     */
    public void fullSync() {

        boolean flag = false;
        Long id = 0L;

        do {
            List<BizCard> bizCards = bizCardMapper.listAllById(id, 20000);

            if (CollUtil.isEmpty(bizCards)) {
                flag = true;
            } else {
                id = bizCards.get(bizCards.size() - 1).getId();
                handle(bizCards);
            }

        } while (!flag);

        log.info("debezium biz_card 全量同步完成,当前数据记录id为：{}", id);


    }

    public void handle(List<BizCard> cardList) {

        // 这里就是针对数据做处理了
        log.info("cardList : {}", JSONUtil.toJsonStr(cardList));

    }


    @Autowired
    public void setBizCardMapper(BizCardMapper bizCardMapper) {
        this.bizCardMapper = bizCardMapper;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
