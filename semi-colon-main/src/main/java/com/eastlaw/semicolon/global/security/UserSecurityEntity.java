package com.eastlaw.semicolon.global.security;

import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;

public class UserSecurityEntity extends User {
	public UserSecurityEntity(com.eastlaw.semicolon.user.domian.User user) {
		super(user.getEmail(), user.getPassword(), new ArrayList<>());
	}
}
