package com.pr.controller;

import com.pr.dto.JobCreateRequestDto;
import com.pr.dto.JobResponseDto;
import com.pr.dto.PagedResponseDto;
import com.pr.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor

public class JobController {

    private final JobService jobService;

    @PostMapping("/createJob")
    public ResponseEntity<JobResponseDto> createJob(@Valid @RequestBody JobCreateRequestDto requestDto) {
        return ResponseEntity.ok(jobService.createJob(requestDto));
    }

    @GetMapping
    public ResponseEntity<PagedResponseDto<JobResponseDto>> listJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(jobService.listJobs(search, page, size));
    }
}

