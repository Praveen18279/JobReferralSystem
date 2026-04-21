package com.pr.controller;

import com.pr.dto.AuthResponseDto;
import com.pr.dto.LoginRequestDto;
import com.pr.dto.RegisterRequestDto;
import com.pr.entity.Role;
import com.pr.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerEmployee(@RequestBody @Valid RegisterRequestDto dto) {
        return ResponseEntity.ok(authService.register(dto, Role.REQUESTER));
    }

    
    @PostMapping("/register/referrer")
    public ResponseEntity<AuthResponseDto> registerReferrer(@RequestBody @Valid RegisterRequestDto dto) {
        return ResponseEntity.ok(authService.register(dto, Role.REFERRER));
    }
    

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}

