package com.pr.service;

import com.pr.dto.ReferralCreateRequestDto;
import com.pr.dto.ReferralResponseDto;
import com.pr.dto.ReferralStatusUpdateRequestDto;
import com.pr.dto.PagedResponseDto;

public interface ReferralService {
    ReferralResponseDto createReferralRequest(ReferralCreateRequestDto requestDto);
    ReferralResponseDto updateReferralStatus(Long referralId, ReferralStatusUpdateRequestDto requestDto);
    PagedResponseDto<ReferralResponseDto> listReferrals(int page, int size);
}

