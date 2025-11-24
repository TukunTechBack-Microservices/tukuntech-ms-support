package com.upc.tukuntechmssupport.support.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateResponseRequest(
        @NotBlank @Size(min = 5, max = 1000)
        String message
) {}
