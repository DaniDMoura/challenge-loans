package com.danilo.desafiolegal.controller;

import com.danilo.desafiolegal.dto.LoanRequestDto;
import com.danilo.desafiolegal.dto.LoanResponseDto;
import com.danilo.desafiolegal.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/customer-loans")
    public ResponseEntity<LoanResponseDto> getCustomerLoans(@RequestBody @Valid LoanRequestDto loanRequestDto) {
        return ResponseEntity.ok(
                loanService.getCustomerLoans(loanRequestDto)
        );
    }
}
