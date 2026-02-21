package com.danilo.desafiolegal.service;

import com.danilo.desafiolegal.dto.LoanDto;
import com.danilo.desafiolegal.dto.LoanRequestDto;
import com.danilo.desafiolegal.dto.LoanResponseDto;
import com.danilo.desafiolegal.dto.LoanType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    private static final BigDecimal INCOME_3000 = BigDecimal.valueOf(3000);
    private static final BigDecimal INCOME_5000 = BigDecimal.valueOf(5000);
    private static final String LOCATION_SP = "SP";
    private static final int AGE_LIMIT = 30;

    public LoanResponseDto getCustomerLoans(LoanRequestDto loanRequestDto) {
        List<LoanDto> availableLoans = checkAvailableLoans(loanRequestDto);

        return new LoanResponseDto(
                loanRequestDto.name(),
                availableLoans
        );
    }

    private List<LoanDto> checkAvailableLoans(LoanRequestDto loanRequestDto) {
        List<LoanDto> availableLoans = new ArrayList<>();

        BigDecimal income = loanRequestDto.income();
        int age = loanRequestDto.age();
        String location = loanRequestDto.location();

        boolean isIncomeUpTo3000 = income.compareTo(INCOME_3000) <= 0;
        boolean isIncomeBetween3000And5000 = income.compareTo(INCOME_3000) >= 0 && income.compareTo(INCOME_5000) <= 0;
        boolean isIncomeAbove5000 = income.compareTo(INCOME_5000) >= 0;
        boolean isFromSP = location.equals("SP");
        boolean isAgeUpTo30 = age < 30;

        if (isIncomeUpTo3000 || isIncomeBetween3000And5000 && isFromSP && isAgeUpTo30) {
            addLoan(availableLoans, LoanType.PERSONAL);
            addLoan(availableLoans, LoanType.GUARANTEED);
        }

        if (isIncomeAbove5000) {
            addLoan(availableLoans, LoanType.CONSIGNMENT);
        }

        return availableLoans;
    }

    private void addLoan(List<LoanDto> availableLoans, LoanType type) {
        availableLoans.add(new LoanDto(type, type.getInterestRate()));
    }
}
