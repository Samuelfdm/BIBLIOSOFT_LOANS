package edu.eci.cvds.BiblioSoftLoans.repository;

import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStudentId(Long studentId);
    List<Loan> findByStudentIdAndLoanState(Long studentId, LoanState loanState);
    List<Loan> findByLoanState(LoanState loanState);
    Boolean findByBookIdAndStudentIdAndLoanState(String bookId, Long studentId, LoanState loanState);
    Loan findByCopyIdAndStudentIdAndLoanState(String copyId, Long studentId, LoanState loanState);
}