package com.pr.repository;

import com.pr.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(String title, String company, Pageable pageable);
}

