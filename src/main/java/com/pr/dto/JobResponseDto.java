package com.pr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JobResponseDto {
    private Long id;
    private String title;
    private String company;
    private String location;
    private String salary;
    private long postedBy;
    private String postedByName;
}

