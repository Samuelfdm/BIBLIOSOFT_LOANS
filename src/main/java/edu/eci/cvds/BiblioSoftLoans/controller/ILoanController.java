package edu.eci.cvds.BiblioSoftLoans.controller;

import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanRequestDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.ReturnRequestDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.ReturnResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ILoanController {

    ResponseEntity<LoanResponseDTO> requestLoan(LoanRequestDTO loanRequest);

    ResponseEntity<ReturnResponseDTO> returnLoan(ReturnRequestDTO returnRequest);

    List<Loan> getLoans();

    List<Loan> getLoansStudent(String studentId);

    List<Loan> getLoansStudent(String studentId, String state);
}