package com.eastlaw.semicolon.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static final ObjectMapper om = new ObjectMapper();

	public static <T> T fromJson(String json, Class<T> type) {
		try {
			return om.readValue(json, type);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String toJson(Object obj) {
		try {
			return om.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
