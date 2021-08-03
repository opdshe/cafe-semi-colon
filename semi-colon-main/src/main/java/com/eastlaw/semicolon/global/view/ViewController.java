package com.eastlaw.semicolon.global.view;

import com.eastlaw.semicolon.global.security.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class ViewController {
	private final SessionManager sessionManager;

	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		String currentPage = request.getHeader("Referer");
		sessionManager.setSessionAttribute(SessionManager.PREV_PAGE, currentPage);
		return "login";
	}
}
