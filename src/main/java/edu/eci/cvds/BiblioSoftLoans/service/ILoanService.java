package edu.eci.cvds.BiblioSoftLoans.service;

import edu.eci.cvds.BiblioSoftLoans.dto.*;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;

import java.util.List;

public interface ILoanService {

    LoanResponseDTO requestLoan(LoanRequestDTO loanRequest);

    ReturnResponseDTO returnBook(ReturnRequestDTO returnRequest);

    List<Loan> loansActive();

    List<Loan> loansActiveStudent(Long studentId);

    List<Loan> loansAllStudent(Long studentId);
}