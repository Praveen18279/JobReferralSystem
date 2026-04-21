package com.pr.service.impl;

import com.pr.dto.PagedResponseDto;
import com.pr.dto.ReferralCreateRequestDto;
import com.pr.dto.ReferralResponseDto;
import com.pr.dto.ReferralStatusUpdateRequestDto;
import com.pr.entity.Job;
import com.pr.entity.ReferralRequest;
import com.pr.entity.ReferralStatus;
import com.pr.entity.User;
import com.pr.exception.BadRequestException;
import com.pr.exception.ResourceNotFoundException;
import com.pr.repository.JobRepository;
import com.pr.repository.ReferralRequestRepository;
import com.pr.repository.UserRepository;
import com.pr.service.ReferralService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReferralServiceImpl implements ReferralService {

    private final ReferralRequestRepository referralRequestRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    @Override
    public ReferralResponseDto createReferralRequest(ReferralCreateRequestDto requestDto) {
        if (requestDto.getRequesterId().equals(requestDto.getReferrerId())) {
            throw new BadRequestException("Requester and referrer cannot be same");
        }

        User requester = userRepository.findById(requestDto.getRequesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Requester not found with id: " + requestDto.getRequesterId()));
        User referrer = userRepository.findById(requestDto.getReferrerId())
                .orElseThrow(() -> new ResourceNotFoundException("Referrer not found with id: " + requestDto.getReferrerId()));
        Job job = jobRepository.findById(requestDto.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + requestDto.getJobId()));

        ReferralRequest referralRequest = ReferralRequest.builder()
                .requester(requester)
                .referrer(referrer)
                .job(job)
                .status(ReferralStatus.PENDING)
                .build();

        return mapToResponse(referralRequestRepository.save(referralRequest));
    }

    @Override
    public ReferralResponseDto updateReferralStatus(Long referralId, ReferralStatusUpdateRequestDto requestDto) {
        ReferralRequest referralRequest = referralRequestRepository.findById(referralId)
                .orElseThrow(() -> new ResourceNotFoundException("Referral request not found with id: " + referralId));

        referralRequest.setStatus(requestDto.getStatus());
        return mapToResponse(referralRequestRepository.save(referralRequest));
    }

    @Override
    public PagedResponseDto<ReferralResponseDto> listReferrals(int page, int size) {
        Page<ReferralRequest> referralPage = referralRequestRepository.findAll(PageRequest.of(page, size));
        List<ReferralResponseDto> content = referralPage.getContent().stream().map(this::mapToResponse).toList();
        return PagedResponseDto.<ReferralResponseDto>builder()
                .content(content)
                .page(referralPage.getNumber())
                .size(referralPage.getSize())
                .totalElements(referralPage.getTotalElements())
                .totalPages(referralPage.getTotalPages())
                .last(referralPage.isLast())
                .build();
    }

    private ReferralResponseDto mapToResponse(ReferralRequest referralRequest) {
        return ReferralResponseDto.builder()
                .id(referralRequest.getId())
                .requesterId(referralRequest.getRequester().getId())
                .requesterName(referralRequest.getRequester().getName())
                .referrerId(referralRequest.getReferrer().getId())
                .referrerName(referralRequest.getReferrer().getName())
                .jobId(referralRequest.getJob().getId())
                .jobTitle(referralRequest.getJob().getTitle())
                .status(referralRequest.getStatus())
                .build();
    }
}

