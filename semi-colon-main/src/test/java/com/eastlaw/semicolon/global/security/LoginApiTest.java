package com.eastlaw.semicolon.global.security;

import com.eastlaw.semicolon.user.domian.UserRepository;
import com.eastlaw.semicolon.user.web.dto.UserRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * 로그인 관련 기능은 중요하니까 WebMvcTest 가 아니라 SpringBootTest로 진행
 **/

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginApiTest {
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserRepository userRepository;

	private MockMvc mockMvc;

	@BeforeAll
	private void init() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@AfterEach
	private void cleanUp() {
		userRepository.deleteAll();
	}

	@Test
	void 등록되지_않은_아이디는_에러_발생() throws Exception {
		//given
		String email = "notRegisteredEmail";
		String password = "notRegisteredPassword";

		//when & then
		assertThrows(RuntimeException.class, () -> {
			mockMvc.perform(formLogin()
					.user(email)
					.password(password))
					.andDo(print());
		});
	}

	@WithMockUser
	@Test
	void 등록된_아이디는_인증_통과() throws Exception {
		//given
		String email = "rightEmail@naver.com";
		String password = "rightPassword";

		UserRequestDto requestDto = new UserRequestDto();
		requestDto.setName("이동헌");
		requestDto.setPassword(password);
		requestDto.setEmail(email);
		userRepository.save(requestDto.toEntity());

		//when & then
		mockMvc.perform(formLogin()
				.user("email", email)
				.password("password", password))
				.andDo(print())
				.andExpect(authenticated());
	}
}
