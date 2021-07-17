package com.eastlaw.semicolon.user.service;

import com.eastlaw.semicolon.user.domian.User;
import com.eastlaw.semicolon.user.domian.UserRepositoryCustom;
import com.eastlaw.semicolon.user.web.dto.UserRequestDto;
import com.eastlaw.semicolon.user.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepositoryCustom userRepository;

	@Transactional
	public UserResponseDto sinUp(UserRequestDto requestDto) {
		if (userRepository.isAlreadyRegisteredEmail(requestDto.getEmail())) {
			throw new IllegalArgumentException("해당 이메일은 이미 등록된 이메일입니다. 이메일=" + requestDto.getEmail());
		}
		User user = userRepository.save(requestDto.toEntity());
		return UserResponseDto.of(user);
	}

	@Transactional(readOnly = true)
	public UserResponseDto findById(Long id) {
		return UserResponseDto.of(userRepository.findById(id));
	}

	@Transactional
	public UserResponseDto delete(Long id) {
		User user = userRepository.findById(id);
		userRepository.delete(user);
		return UserResponseDto.of(user);
	}

	@Transactional
	public UserResponseDto update(Long id, UserRequestDto requestDto) {
		User user = userRepository.findById(id);
		user.update(requestDto);
		return UserResponseDto.of(user);
	}
}
