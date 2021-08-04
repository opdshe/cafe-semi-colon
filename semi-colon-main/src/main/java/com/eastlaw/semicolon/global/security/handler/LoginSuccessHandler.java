package com.eastlaw.semicolon.global.security.handler;

import com.eastlaw.semicolon.global.security.SessionManager;
import com.eastlaw.semicolon.user.domian.User;
import com.eastlaw.semicolon.user.domian.UserRepositoryCustom;
import com.eastlaw.semicolon.user.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private final UserRepositoryCustom userRepository;
	private final SessionManager sessionManager;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (Objects.nonNull(session)) {
			setLoginUserInfo(authentication);
			Optional<Object> redirectUrl = sessionManager.getSessionAttribute(SessionManager.PREV_PAGE);
			//세션에 이전 페이지 정보 있으면 해당 페이지로 redirect
			if (redirectUrl.isPresent()) {
				sessionManager.removeAttribute(SessionManager.PREV_PAGE);
				getRedirectStrategy().sendRedirect(request, response, (String) redirectUrl.get());
			} else {
				super.onAuthenticationSuccess(request, response, authentication);
			}
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

	private void setLoginUserInfo(Authentication authentication) {
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		User user = userRepository.findByEmail(principal.getUsername());
		UserResponseDto userResponseDto = UserResponseDto.of(user);
		sessionManager.setSessionAttribute(SessionManager.LOGIN_USER, userResponseDto);
	}
}
