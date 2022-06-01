package com.thtns.debezium.demo.enums;

import lombok.Getter;

public enum EventTypeEnum {
	/**
	 * 增
	 */
	CREATE(1),
	/**
	 * 删
	 */
	UPDATE(2),
	/**
	 * 改
	 */
	DELETE(3),

	/**
	 * 全量 读
	 */
	READ(4),
	;
	@Getter
	private final int type;

	EventTypeEnum(int type) {
		this.type = type;
	}
}
