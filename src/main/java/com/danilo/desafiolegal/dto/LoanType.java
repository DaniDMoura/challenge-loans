package com.danilo.desafiolegal.dto;

public enum LoanType {
    PERSONAL(4), GUARANTEED(3), CONSIGNMENT(2);

    private final int interestRate;

    LoanType(int interestRate) {
        this.interestRate = interestRate;
    }

    public int getInterestRate() {
        return interestRate;
    }
}
