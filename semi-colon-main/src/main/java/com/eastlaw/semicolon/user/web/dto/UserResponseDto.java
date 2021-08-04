package com.eastlaw.semicolon.user.web.dto;

import com.eastlaw.semicolon.user.domian.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static org.springframework.beans.BeanUtils.copyProperties;

@Setter
@Getter
@NoArgsConstructor
public class UserResponseDto implements Serializable {
	private Long id;

	private String name;

	private String email;

	public static UserResponseDto of(User user) {
		UserResponseDto dto = new UserResponseDto();
		copyProperties(user, dto);
		return dto;
	}
}
