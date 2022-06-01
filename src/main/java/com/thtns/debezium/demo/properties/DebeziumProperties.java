package com.thtns.debezium.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author thtns
 * @description debezium 配置
 * @date 2022/4/20
 */
@Data
@ConfigurationProperties(prefix = "debezium")
@Component
public class DebeziumProperties {

	/**
	 * # 偏移量文件路径
	 */
	private String offsetFileName;
	/**
	 * # 是否启动时清除偏移量文件
	 */
	private Boolean offsetFileDelete;
	/**
	 * # 偏移量提交时间 单位ms
	 */
	private String offsetTime;
	/**
	 * 读取历史记录文件
	 */
	private String historyFileName;
	/**
	 * 保证每个数据库读取的 instance-name  logic-name 不能相同
	 * 实例名
	 */
	private String instanceName;

	/**
	 * # 逻辑名
	 */
	private String logicName;

	private String ip;
	private String port;
	private String username;
	private String password;
	/**
	 * 读取的表
	 */
	private String includeTable;

	/**
	 * 读取的库
	 */
	private String includeDb;

}
