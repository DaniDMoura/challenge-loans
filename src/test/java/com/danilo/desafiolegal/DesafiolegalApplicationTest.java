package com.danilo.desafiolegal;

import com.danilo.desafiolegal.dto.LoanDto;
import com.danilo.desafiolegal.dto.LoanRequestDto;
import com.danilo.desafiolegal.dto.LoanResponseDto;
import com.danilo.desafiolegal.dto.LoanType;
import com.danilo.desafiolegal.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DesafiolegalApplicationTest {

    @Autowired
    LoanService loanService;

    @Test
    void shouldGrantPersonalAndGuaranteedLoanWhenIncomeIsLessOrEqualThan3000() {
        LoanRequestDto request = new LoanRequestDto(
                32,
                "275.484.389-23",
                "Vuxaywua Zukiagou",
                BigDecimal.valueOf(2000),
                "RJ"
        );

        LoanResponseDto response = loanService.getCustomerLoans(request);

        assertEquals("Vuxaywua Zukiagou", response.customer());
        assertEquals(2, response.loans().size());
        assertTrue(response.loans().contains(new LoanDto(LoanType.PERSONAL, LoanType.PERSONAL.getInterestRate())));
        assertTrue(response.loans().contains(new LoanDto(LoanType.GUARANTEED, LoanType.GUARANTEED.getInterestRate())));
    }

    @Test
    void shouldGrantPersonalAndGuaranteedLoanWhenIncomeIsBetween3000And5000IsFromSpAndLessThan30YearsOld() {
        LoanRequestDto request = new LoanRequestDto(
                29,
                "275.484.389-23",
                "Vuxaywua Zukiagou",
                BigDecimal.valueOf(4000),
                "SP"
        );

        LoanResponseDto response = loanService.getCustomerLoans(request);

        assertEquals("Vuxaywua Zukiagou", response.customer());
        assertEquals(2, response.loans().size());
        assertTrue(response.loans().contains(new LoanDto(LoanType.PERSONAL, LoanType.PERSONAL.getInterestRate())));
        assertTrue(response.loans().contains(new LoanDto(LoanType.GUARANTEED, LoanType.GUARANTEED.getInterestRate())));
    }

    @Test
    void shouldGrantConsignedLoanWhenIncomeIsGreaterThan5000() {
        LoanRequestDto request = new LoanRequestDto(
                31,
                "275.484.389-23",
                "Vuxaywua Zukiagou",
                BigDecimal.valueOf(8000),
                "RJ"
        );

        LoanResponseDto response = loanService.getCustomerLoans(request);

        assertEquals("Vuxaywua Zukiagou", response.customer());
        assertEquals(1, response.loans().size());
        assertTrue(response.loans().contains(new LoanDto(LoanType.CONSIGNMENT, LoanType.CONSIGNMENT.getInterestRate())));
    }
}
