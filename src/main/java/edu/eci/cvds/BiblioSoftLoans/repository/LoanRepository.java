package edu.eci.cvds.BiblioSoftLoans.repository;

import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStudentId(String studentId);
    List<Loan> findByStudentIdAndLoanState(String studentId, LoanState loanState);
    List<Loan> findByLoanState(LoanState loanState);
    List<Loan> findByBookIdAndStudentIdAndLoanState(String bookId, String studentId, LoanState loanState);
    Loan findByCopyIdAndStudentIdAndLoanState(String copyId, String studentId, LoanState loanState);
}