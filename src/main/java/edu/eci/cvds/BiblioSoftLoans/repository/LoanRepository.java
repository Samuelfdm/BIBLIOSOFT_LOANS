package edu.eci.cvds.BiblioSoftLoans.repository;

import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends IRepository<Loan, Long> {
    List<Loan> findByStudentIdAndLoanState(Long studentId, LoanState loanState);
    List<Loan> findByCopyIdAndStudentId(String copyId, LoanState loanState);
    @Query("SELECT l.id FROM Loan l WHERE l.copyId = :copyId AND l.studentId = :studentId AND l.loanState = :loanState")
    Long findIdByCopyIdAndStudentIdAndLoanState(@Param("copyId") String copyId, @Param("studentId") Long studentId, @Param("loanState") LoanState loanState);
}