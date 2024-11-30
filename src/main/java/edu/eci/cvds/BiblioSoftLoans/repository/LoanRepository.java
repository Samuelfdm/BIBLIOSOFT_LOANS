package edu.eci.cvds.BiblioSoftLoans.repository;

import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStudentId(Long studentId);
    List<Loan> findByStudentIdAndLoanState(Long studentId, LoanState loanState);
    List<Loan> findByBookIdAndStudentId(String bookId, Long studentId);
    //List<Loan> findByBookIdAndStudentIdAndLoanState(String bookId, Long studentId, LoanState loanState);
    List<Loan> findByLoanState(LoanState loanState);
    Boolean findByBookIdAndStudentIdAndLoanState(String bookId, Long studentId, LoanState loanState);
    @Query("SELECT l.id FROM Loan l WHERE l.copyId = :copyId AND l.studentId = :studentId AND l.loanState = :loanState")
    Loan findByCopyIdAndStudentIdAndLoanState(@Param("copyId") String copyId, @Param("studentId") Long studentId, @Param("loanState") LoanState loanState);
}