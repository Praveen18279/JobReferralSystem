package com.pr.service.impl;

import com.pr.dto.JobCreateRequestDto;
import com.pr.dto.JobResponseDto;
import com.pr.dto.PagedResponseDto;
import com.pr.entity.Job;
import com.pr.entity.User;
import com.pr.exception.ResourceNotFoundException;
import com.pr.repository.JobRepository;
import com.pr.repository.UserRepository;
import com.pr.service.JobService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override 
    public JobResponseDto createJob(JobCreateRequestDto requestDto) {
        User postedBy = userRepository.findById(requestDto.getPostedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + requestDto.getPostedBy()));

        Job job = Job.builder()
                .title(requestDto.getTitle())
                .company(requestDto.getCompany())
                .location(requestDto.getLocation())
                .salary(requestDto.getSalary())
                .postedBy(postedBy)
                .build();

        return mapToResponse(jobRepository.save(job));
    }

    @Override
    public PagedResponseDto<JobResponseDto> listJobs(String search, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Job> jobPage;
        if (search == null || search.trim().isEmpty()) {
            jobPage = jobRepository.findAll(pageRequest);
        } else {
            jobPage = jobRepository.findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(search, search, pageRequest);
        }

        List<JobResponseDto> content = jobPage.getContent().stream().map(this::mapToResponse).toList();
        return PagedResponseDto.<JobResponseDto>builder()
                .content(content)
                .page(jobPage.getNumber())
                .size(jobPage.getSize())
                .totalElements(jobPage.getTotalElements())
                .totalPages(jobPage.getTotalPages())
                .last(jobPage.isLast())
                .build();
    }

    private JobResponseDto mapToResponse(Job job) {
        return JobResponseDto.builder()
                .id(job.getId())
                .title(job.getTitle())
                .company(job.getCompany())
                .location(job.getLocation())
                .salary(job.getSalary())
                .postedBy(job.getPostedBy().getId())
                .postedByName(job.getPostedBy().getName())
                .build();
    }
}

