package edu.eci.cvds.BiblioSoftLoans.service;

import edu.eci.cvds.BiblioSoftLoans.dto.*;

import java.util.List;

public interface ILoanService {

    LoanResponseDTO requestLoan(LoanRequestDTO loanRequest);

    ReturnResponseDTO returnBook(ReturnRequestDTO returnRequest);

    CopyDTO getCopy(String copyId);

    List<LoanResponseDTO> getActiveLoansByStudent(Long studentId);
}