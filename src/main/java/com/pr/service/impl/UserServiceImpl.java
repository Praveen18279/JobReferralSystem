package com.pr.service.impl;

import com.pr.dto.UserResponseDto;
import com.pr.entity.User;
import com.pr.exception.ResourceNotFoundException;
import com.pr.repository.UserRepository;
import com.pr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .resumeUrl(user.getResumeUrl())
                .build();
    }
}

