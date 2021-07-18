package com.eastlaw.semicolon.user.web;

import com.eastlaw.semicolon.user.service.UserService;
import com.eastlaw.semicolon.user.web.dto.UserRequestDto;
import com.eastlaw.semicolon.user.web.dto.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static com.eastlaw.semicolon.user.service.UserServiceTest.getSampleUserRequestDto;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
class UserRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private static ObjectMapper objectMapper = new ObjectMapper();

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

	@Test
	void 이미_등록된_이메일이면_회원가입_실패한다() {
		//given
		UserRequestDto requestDto = getSampleUserRequestDto();

		//mocking
		when(userService.signUp(requestDto)).thenThrow(new IllegalArgumentException("해당 이메일은 이미 등록된 이메일입니다."));

		//when & then
		assertThrows(NestedServletException.class, () -> mockMvc.perform(post("/api/user/")
				.content(objectMapper.writeValueAsString(requestDto))
				.contentType(MediaType.APPLICATION_JSON)));
	}

	@Test
	void update_요청시_user_정보가_변경되어야함() throws Exception {
		//given
		UserRequestDto origin = getSampleUserRequestDto();
		UserRequestDto newUser = getSampleUserRequestDto();
		String newName = "바뀐이름";
		newUser.setName(newName);

		//mocking
		when(userService.update((long) 1, origin)).thenReturn(UserResponseDto.of(newUser.toEntity()));

		//when
		mockMvc.perform(put("/api/user/1")
				.content(objectMapper.writeValueAsString(origin))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(newUser)));
	}
}