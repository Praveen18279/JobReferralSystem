package com.pr.dto;

import com.pr.entity.ReferralStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReferralResponseDto {
    private Long id;
    private Long requesterId;
    private String requesterName;
    private Long referrerId;
    private String referrerName;
    private Long jobId;
    private String jobTitle;
    private ReferralStatus status;
}

