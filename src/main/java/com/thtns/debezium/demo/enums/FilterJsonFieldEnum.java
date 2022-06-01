package com.thtns.debezium.demo.enums;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum FilterJsonFieldEnum {
	/**
	 * 表
	 */
	table,
	/**
	 * 库
	 */
	db,
	/**
	 * 操作时间
	 */
	ts_ms,
	;

	public static Boolean filterJsonField(String fieldName) {
		return Stream.of(values()).map(Enum::name).collect(Collectors.toSet()).contains(fieldName);
	}
}
