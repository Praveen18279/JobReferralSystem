package com.pr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferralCreateRequestDto {

    @NotNull(message = "requesterId is required")
    private Long requesterId;

    @NotNull(message = "referrerId is required")
    private Long referrerId;

    @NotNull(message = "jobId is required")
    private Long jobId;
}

