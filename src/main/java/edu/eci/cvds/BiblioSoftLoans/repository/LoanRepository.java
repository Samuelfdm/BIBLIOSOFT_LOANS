package edu.eci.cvds.BiblioSoftLoans.repository;

import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStudentIdAndLoanState(Long studentId, LoanState loanState);
    List<Loan> findByCopyIdAndStudentId(String copyId, Long studentId);
    List<Loan> findByCopyIdAndStudentIdAndLoanState(String copyId, Long studentId, LoanState loanState);
    @Query("SELECT l.id FROM Loan l WHERE l.copyId = :copyId AND l.studentId = :studentId AND l.loanState = :loanState")
    Long findIdByCopyIdAndStudentIdAndLoanState(@Param("copyId") String copyId, @Param("studentId") Long studentId, @Param("loanState") LoanState loanState);
}