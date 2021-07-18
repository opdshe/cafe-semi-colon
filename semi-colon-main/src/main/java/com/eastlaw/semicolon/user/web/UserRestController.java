package com.eastlaw.semicolon.user.web;

import com.eastlaw.semicolon.user.service.UserService;
import com.eastlaw.semicolon.user.web.dto.UserRequestDto;
import com.eastlaw.semicolon.user.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("api/user")
@RestController
public class UserRestController {
	private final UserService userService;

	@PostMapping("/")
	public UserResponseDto sinUp(@Valid @RequestBody UserRequestDto requestDto) {
		return userService.signUp(requestDto);
	}

	@GetMapping("/{id}")
	public UserResponseDto findById(@PathVariable Long id) {
		return userService.findById(id);
	}

	@DeleteMapping("/{id}")
	public UserResponseDto delete(@PathVariable Long id) {
		return userService.delete(id);
	}

	@PutMapping("/{id}")
	public UserResponseDto update(@PathVariable Long id, @RequestBody UserRequestDto requestDto){
		return userService.update(id, requestDto);
	}
}
