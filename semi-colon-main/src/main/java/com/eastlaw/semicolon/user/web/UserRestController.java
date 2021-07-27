package com.eastlaw.semicolon.user.web;

import com.eastlaw.semicolon.api.ApiResult;
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
	public ApiResult<UserResponseDto> findById(@PathVariable Long id) {
		return ApiResult.success(userService.findById(id));
	}

	@DeleteMapping("/{id}")
	public ApiResult<UserResponseDto> delete(@PathVariable Long id) {
		return ApiResult.success(userService.delete(id));
	}

	@PutMapping("/{id}")
	public ApiResult<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto requestDto) {
		return ApiResult.success(userService.update(id, requestDto));
	}
}
