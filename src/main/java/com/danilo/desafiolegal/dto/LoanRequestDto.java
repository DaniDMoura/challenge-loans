package com.danilo.desafiolegal.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record LoanRequestDto(
        @Min(value = 0) @NotNull int age,
        @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}") @NotBlank String cpf,
        @NotBlank String name,
        @DecimalMin(value = "0.01") @NotNull BigDecimal income,
        @NotBlank String location
) {
}
