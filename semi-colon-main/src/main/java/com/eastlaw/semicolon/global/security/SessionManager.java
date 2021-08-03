package com.eastlaw.semicolon.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SessionManager {
	public static final String PREV_PAGE = "prevPage";
	public static final String LOGIN_USER = "loginUser";

	private final HttpSession httpSession;

	public void setSessionAttribute(String attribute, Object target) {
		if (!hasAttribute(attribute)) {
			httpSession.setAttribute(attribute, target);
		}
	}

	public Object getSessionAttribute(String attribute) {
		return Optional.ofNullable(httpSession.getAttribute(attribute))
				.orElseThrow(() -> new RuntimeException("session attribute not found. attribute =" + attribute));
	}

	public void removeAttribute(String attribute) {
		httpSession.removeAttribute(attribute);
	}

	public boolean hasAttribute(String attribute) {
		return httpSession.getAttribute(attribute) != null;
	}
}
