package com.pr.service;

import com.pr.dto.AuthResponseDto;
import com.pr.dto.LoginRequestDto;
import com.pr.dto.RegisterRequestDto;
import com.pr.entity.Role;

public interface AuthService {
    AuthResponseDto register(RegisterRequestDto requestDto,Role role);
    AuthResponseDto login(LoginRequestDto requestDto);
}

