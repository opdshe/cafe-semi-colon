package com.eastlaw.semicolon.error;

import com.eastlaw.semicolon.api.ApiResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {
	/**
	 * 앱 실행 도중 RuntimeException 발생 시 ApiResult.fail() 반환
	 **/
	@ExceptionHandler(value = RuntimeException.class)
	public <T> ApiResult<T> handleException(RuntimeException exception) {
		String message = exception.getMessage();
		return ApiResult.fail(message);
	}
}
