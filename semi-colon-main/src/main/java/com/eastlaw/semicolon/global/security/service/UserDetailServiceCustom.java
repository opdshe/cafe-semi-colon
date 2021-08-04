package com.eastlaw.semicolon.global.security.service;

import com.eastlaw.semicolon.global.security.UserSecurityEntity;
import com.eastlaw.semicolon.user.domian.User;
import com.eastlaw.semicolon.user.domian.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceCustom implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 email 입니다. email=" + email));
		return new UserSecurityEntity(user);
	}
}
