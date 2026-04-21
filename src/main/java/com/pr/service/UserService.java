package com.pr.service;

import com.pr.dto.UserResponseDto;

public interface UserService {
    UserResponseDto getUserProfile(Long userId);
}

