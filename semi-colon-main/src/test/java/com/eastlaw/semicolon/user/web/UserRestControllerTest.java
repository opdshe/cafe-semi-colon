package com.eastlaw.semicolon.user.web;

import com.eastlaw.semicolon.api.ApiResult;
import com.eastlaw.semicolon.global.security.handler.LogOutSuccessHandler;
import com.eastlaw.semicolon.global.security.handler.LoginFailureHandler;
import com.eastlaw.semicolon.global.security.handler.LoginSuccessHandler;
import com.eastlaw.semicolon.global.security.service.UserDetailServiceCustom;
import com.eastlaw.semicolon.user.domian.UserRepository;
import com.eastlaw.semicolon.user.service.UserService;
import com.eastlaw.semicolon.user.web.dto.UserRequestDto;
import com.eastlaw.semicolon.user.web.dto.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static com.eastlaw.semicolon.user.service.UserServiceTest.getSampleUserRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserRestController.class, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
class UserRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private UserDetailServiceCustom userDetailServiceCustom;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private LoginSuccessHandler loginSuccessHandler;

	@MockBean
	private LoginFailureHandler loginFailureHandler;

	@MockBean
	private LogOutSuccessHandler logOutSuccessHandler;

	private static ObjectMapper objectMapper = new ObjectMapper();

	@WithMockUser
	@Test
	void 중복되지_않은_이메일이면_회원가입_성공한다() throws Exception {
		//given
		UserRequestDto requestDto = getSampleUserRequestDto();
		UserResponseDto expectedResult = UserResponseDto.of(requestDto.toEntity());

		//mocking
		when(userService.signUp(requestDto)).thenReturn(expectedResult);

		//when
		mockMvc.perform(post("/api/user/")
				.content(objectMapper.writeValueAsString(requestDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
	}

	@WithMockUser
	@Test
	void 이미_등록된_이메일이면_회원가입_실패한다() throws Exception {
		//given
		UserRequestDto requestDto = getSampleUserRequestDto();

		//mocking
		when(userService.signUp(requestDto)).thenThrow(new IllegalArgumentException("해당 이메일은 이미 등록된 이메일입니다."));

		//when
		String contentAsString = mockMvc.perform(post("/api/user/")
				.content(objectMapper.writeValueAsString(requestDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse().getContentAsString();
		System.out.println(contentAsString);
		Map resultMap = objectMapper.readValue(contentAsString, Map.class);

		//then
		assertThat(resultMap.get("success")).isEqualTo(false);
	}

	@WithMockUser
	@Test
	void update_요청시_user_정보가_변경되어야함() throws Exception {
		//given
		UserRequestDto origin = getSampleUserRequestDto();
		UserRequestDto newUser = getSampleUserRequestDto();
		long id = 1;
		String newName = "바뀐이름";
		newUser.setName(newName);

		UserResponseDto expectedData = UserResponseDto.of(newUser.toEntity());
		ApiResult<UserResponseDto> expectedResult = ApiResult.success(expectedData);

		//mocking
		when(userService.update(id, origin)).thenReturn(expectedData);

		//when
		mockMvc.perform(put("/api/user/" + id)
				.content(objectMapper.writeValueAsString(origin))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
	}
}