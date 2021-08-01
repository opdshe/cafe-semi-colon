package com.eastlaw.semicolon.user.web.dto;

import com.eastlaw.semicolon.user.domian.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode
@Setter
@Getter
@RequiredArgsConstructor
public class UserRequestDto {
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
				.password(passwordEncoder.encode(password))
				.build();
	}
}
