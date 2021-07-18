package com.eastlaw.semicolon.user.web.dto;

import com.eastlaw.semicolon.user.domian.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode
@Setter
@Getter
public class UserRequestDto {
	@NotBlank(message = "이름을 입력해 주세요.")
	private String name;

	@NotBlank(message = "이메일을 입력해 주세요.")
	@Email
	private String email;

	@NotBlank(message = "패스워드를 입력해 주세요.")
	private String password;

	public User toEntity() {
		return User.builder()
				.name(name)
				.email(email)
				.password(password)
				.build();
	}
}
