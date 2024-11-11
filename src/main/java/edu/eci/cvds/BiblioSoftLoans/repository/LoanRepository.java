package edu.eci.cvds.BiblioSoftLoans.repository;

import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import java.util.List;

public interface LoanRepository extends IRepository<Loan, Long> {
    List<Loan> findByStudentIdAndLoanState(Long studentId, LoanState loanState);
    List<Loan> findByCopyIdAndLoanState(String copyId, LoanState loanState);
}