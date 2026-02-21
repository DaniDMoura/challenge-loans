package com.danilo.desafiolegal.dto;

import java.util.List;

public record LoanResponseDto(
        String customer,
        List<LoanDto> loans
) {
}
