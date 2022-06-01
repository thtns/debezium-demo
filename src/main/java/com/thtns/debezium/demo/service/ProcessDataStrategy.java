package com.thtns.debezium.demo.service;

import com.thtns.debezium.demo.model.DebeziumListenerModel;

/**
 * @author thtns
 * @description 处理数据
 * @date 2022/4/20
 */
public interface ProcessDataStrategy {

	/**
	 * 处理数据
	 *
	 * @param debeziumListenerModel 变动数据
	 */
	void process(DebeziumListenerModel debeziumListenerModel);

}
