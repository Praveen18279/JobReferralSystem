package com.pr.service;

import com.pr.dto.JobCreateRequestDto;
import com.pr.dto.JobResponseDto;
import com.pr.dto.PagedResponseDto;

public interface JobService {
    JobResponseDto createJob(JobCreateRequestDto requestDto);
    PagedResponseDto<JobResponseDto> listJobs(String search, int page, int size);
}

