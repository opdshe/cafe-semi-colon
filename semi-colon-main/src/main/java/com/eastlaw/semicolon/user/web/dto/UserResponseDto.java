package com.eastlaw.semicolon.user.web.dto;

import com.eastlaw.semicolon.user.domian.User;
import lombok.Getter;
import lombok.Setter;

import static org.springframework.beans.BeanUtils.copyProperties;

@Setter
@Getter
public class UserResponseDto {
	private Long id;

	private String name;

	private String email;

	private String password;

	public static UserResponseDto of(User user) {
		UserResponseDto dto = new UserResponseDto();
		copyProperties(user, dto);
		return dto;
	}
}
