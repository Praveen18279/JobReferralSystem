package com.pr.controller;

import com.pr.dto.PagedResponseDto;
import com.pr.dto.ReferralCreateRequestDto;
import com.pr.dto.ReferralResponseDto;
import com.pr.dto.ReferralStatusUpdateRequestDto;
import com.pr.service.ReferralService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/referrals")
@RequiredArgsConstructor
public class ReferralController {

	private final ReferralService referralService;

	@PostMapping()
	public ResponseEntity<ReferralResponseDto> createRequest(@Valid @RequestBody ReferralCreateRequestDto requestDto) {
		return ResponseEntity.ok(referralService.createReferralRequest(requestDto));
	}

	@PatchMapping("/{referralId}/status")
	public ResponseEntity<ReferralResponseDto> updateStatus(@PathVariable Long referralId,
			@Valid @RequestBody ReferralStatusUpdateRequestDto requestDto) {
		return ResponseEntity.ok(referralService.updateReferralStatus(referralId, requestDto));
	}

	@GetMapping
	public ResponseEntity<PagedResponseDto<ReferralResponseDto>> listReferrals(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(referralService.listReferrals(page, size));
	}
}
