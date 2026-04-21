package com.pr.service.impl;

import com.pr.dto.AuthResponseDto;
import com.pr.dto.LoginRequestDto;
import com.pr.dto.RegisterRequestDto;
import com.pr.dto.UserResponseDto;
import com.pr.entity.Role;
import com.pr.entity.User;
import com.pr.exception.BadRequestException;
import com.pr.exception.UnauthorizedException;
import com.pr.repository.UserRepository;
import com.pr.security.JwtService;
import com.pr.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@Override
	public AuthResponseDto register(RegisterRequestDto requestDto,Role role) {
		if (userRepository.existsByEmail(requestDto.getEmail())) {
			throw new BadRequestException("Email already exists");
		}

		User user = User.builder()
				.name(requestDto.getName())
				.email(requestDto.getEmail())
				.role(role)
				.password(passwordEncoder.encode(requestDto.getPassword()))

				.resumeUrl(requestDto.getResumeUrl()).build();

		User savedUser = userRepository.save(user);
		String token = jwtService.generateToken(savedUser.getEmail(), savedUser.getRole().name());
		return AuthResponseDto.builder().token(token).user(mapToUserResponse(savedUser)).build();
	}

	@Override
	public AuthResponseDto login(LoginRequestDto requestDto) {
		User user = userRepository.findByEmail(requestDto.getEmail())
				.orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

		if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
			throw new UnauthorizedException("Invalid credentials");
		}

		String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
		return AuthResponseDto.builder().token(token).user(mapToUserResponse(user)).build();
	}

	private UserResponseDto mapToUserResponse(User user) {
		return UserResponseDto.builder().id(user.getId()).name(user.getName()).email(user.getEmail())
				.role(user.getRole()).resumeUrl(user.getResumeUrl()).build();
	}
}
