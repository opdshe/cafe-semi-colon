package com.eastlaw.semicolon.global.security.config;

import com.eastlaw.semicolon.global.security.UserDetailServiceCustom;
import com.eastlaw.semicolon.global.security.handler.LogOutSuccessHandler;
import com.eastlaw.semicolon.global.security.handler.LoginFailureHandler;
import com.eastlaw.semicolon.global.security.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailServiceCustom userDetailsService;
	private final LoginSuccessHandler loginSuccessHandler;
	private final LoginFailureHandler loginFailureHandler;
	private final LogOutSuccessHandler logOutSuccessHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/**").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.successHandler(loginSuccessHandler)
				.failureHandler(loginFailureHandler)
				.usernameParameter("email")
				.passwordParameter("password")
				.and()
				.logout()
				.logoutSuccessHandler(logOutSuccessHandler);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/static/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
