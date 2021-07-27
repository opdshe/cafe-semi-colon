package com.eastlaw.semicolon.api;

import com.eastlaw.semicolon.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResult<T> {
	private boolean success;
	private T data;
	private String message;

	public ApiResult(boolean success, T data, String message) {
		this.success = success;
		this.data = data;
		this.message = message;
	}

	public static <T> ApiResult<T> success(T data) {
		return new ApiResult<>(true, data, null);
	}

	public static <T> ApiResult<T> fail(String message) {
		return new ApiResult<>(false, null, message);
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
