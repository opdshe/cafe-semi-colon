package com.eastlaw.semicolon.user.domian;

import com.eastlaw.semicolon.error.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryCustom {
	private final UserRepository userRepository;

	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new InvalidRequestException("해당 id는 등록되지 않은 id 입니다. id=" + id));
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new InvalidRequestException("해당 email은 등록되지 않은 email 입니다. email=" + email));
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public void delete(User user) {
		userRepository.delete(user);
	}

	public boolean isAlreadyRegisteredEmail(String email) {
		return userRepository.findByEmail(email).isPresent();
	}
}
