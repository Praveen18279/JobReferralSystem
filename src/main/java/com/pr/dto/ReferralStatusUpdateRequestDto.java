package com.pr.dto;

import com.pr.entity.ReferralStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferralStatusUpdateRequestDto {

    @NotNull(message = "status is required")
    private ReferralStatus status;
}

