package com.pr.dto;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobCreateRequestDto {

    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title must be at most 150 characters")
    private String title;

    @NotBlank(message = "Company is required")
    @Size(max = 150, message = "Company must be at most 150 characters")
    private String company;

    @NotBlank(message = "Location is required")
    @Size(max = 150, message = "Location must be at most 150 characters")
    private String location;
    
    @NotBlank(message = "Salary is required")
    @Size(max = 150, message = "Salary must be at most 150 characters")
    private String salary;

    @NotNull(message = "postedBy is required")
    private Long postedBy;
}

