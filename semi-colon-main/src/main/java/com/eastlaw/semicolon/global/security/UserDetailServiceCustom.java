package com.eastlaw.semicolon.global.security;

import com.eastlaw.semicolon.user.domian.User;
import com.eastlaw.semicolon.user.domian.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceCustom implements UserDetailsService {
	private final UserRepositoryCustom userRepositoryCustom;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepositoryCustom.findByEmail(email);
		return new UserSecurityEntity(user);
	}
}
