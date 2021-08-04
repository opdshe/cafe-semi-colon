package com.eastlaw.semicolon.user.service;

import com.eastlaw.semicolon.error.InvalidRequestException;
import com.eastlaw.semicolon.user.domian.UserRepositoryCustom;
import com.eastlaw.semicolon.user.web.dto.UserRequestDto;
import com.eastlaw.semicolon.user.web.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepositoryCustom userRepository;

	@Test
	void 중복되지_않은_이메일이면_회원가입_성공한다() {
		//given
		UserRequestDto signUpRequestDto = getSampleUserRequestDto();

		//mocking
		when(userRepository.save(any())).thenReturn(signUpRequestDto.toEntity());
		when(userRepository.isAlreadyRegisteredEmail(signUpRequestDto.getEmail())).thenReturn(false);

		//when
		UserResponseDto response = userService.signUp(signUpRequestDto);

		//then
		assertThat(response.getEmail()).isEqualTo(signUpRequestDto.getEmail());
		assertThat(response.getName()).isEqualTo(signUpRequestDto.getName());
	}

	@Test
	void 중복되는_이메일이면_회원가입시_예외_발생해야함() {
		//given
		UserRequestDto singUpRequestDto = getSampleUserRequestDto();

		//mocking
		when(userRepository.isAlreadyRegisteredEmail(singUpRequestDto.getEmail())).thenReturn(true);

		//when & then
		assertThrows(InvalidRequestException.class, () -> userService.signUp(singUpRequestDto));
	}

	@Test
	void update시_user정보가_변경되어야함() {
		//given
		UserRequestDto origin = getSampleUserRequestDto();
		UserRequestDto newUser = getSampleUserRequestDto();
		String newName = "바뀐이름";
		newUser.setName(newName);

		//mocking
		when(userRepository.findById((long) 1)).thenReturn(origin.toEntity());

		//when
		UserResponseDto updatedUserDto = userService.update((long) 1, newUser);

		//then
		assertThat(updatedUserDto.getName()).isEqualTo(newName);
	}

	public static UserRequestDto getSampleUserRequestDto() {
		UserRequestDto requestDto = new UserRequestDto();
		requestDto.setEmail("test@gmail.com");
		requestDto.setName("이동헌");
		requestDto.setPassword("비밀번호");
		return requestDto;
	}
}