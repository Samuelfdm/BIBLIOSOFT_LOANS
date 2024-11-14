package edu.eci.cvds.BiblioSoftLoans.service;

import edu.eci.cvds.BiblioSoftLoans.dto.*;

public interface ILoanService {

    LoanResponseDTO requestLoan(LoanRequestDTO loanRequest);

    ReturnResponseDTO returnBook(ReturnRequestDTO returnRequest);
}