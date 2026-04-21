package com.pr.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "referral_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferralRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "requester_id", nullable = false, foreignKey = @ForeignKey(name = "fk_referral_requester"))
	private User requester;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "referrer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_referral_referrer"))
	private User referrer;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "job_id", nullable = false, foreignKey = @ForeignKey(name = "fk_referral_job"))
	private Job job;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ReferralStatus status;
}
