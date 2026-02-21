package com.danilo.desafiolegal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class LoanException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Loans Application Internal Server Error");
        problemDetail.setDetail("The server encountered an unexpected condition that prevented it from fulfilling the request.");
        return problemDetail;
    }
}
