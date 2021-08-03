package com.eastlaw.semicolon.global.security.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
	private static final String BAD_CREDENTIAL_MESSAGE = "아이디 혹은 비밀번호가 맞지 않습니다.";

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		if (exception instanceof BadCredentialsException) {
			throw new RuntimeException(BAD_CREDENTIAL_MESSAGE);
		}
	}
}
