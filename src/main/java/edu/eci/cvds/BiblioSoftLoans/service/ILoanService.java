package edu.eci.cvds.BiblioSoftLoans.service;

import edu.eci.cvds.BiblioSoftLoans.dto.*;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;

import java.util.List;

public interface ILoanService {

    LoanResponseDTO requestLoan(LoanRequestDTO loanRequest);

    ReturnResponseDTO returnBook(ReturnRequestDTO returnRequest);

    List<Loan> getLoans();

    List<Loan> getLoans(String state);

    List<Loan> getLoansStudent(Long studentId);


    List<Loan> getLoansStudent(String studentId);

    List<Loan> getLoansStudent(String studentId, String state);
}