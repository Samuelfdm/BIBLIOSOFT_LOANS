package edu.eci.cvds.BiblioSoftLoans.repository;

import edu.eci.cvds.BiblioSoftLoans.dto.Loans.HistoryLoanBookDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.History.HistoryLoanDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.HistoryLoanStudentDTO;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStudentId(String studentId);
    List<Loan> findByStudentIdAndLoanState(String studentId, LoanState loanState);
    List<Loan> findByLoanState(LoanState loanState);
    List<Loan> findByBookIdAndStudentIdAndLoanState(String bookId, String studentId, LoanState loanState);
    Loan findByCopyIdAndStudentIdAndLoanState(String copyId, String studentId, LoanState loanState);

    @Query("SELECT new edu.eci.cvds.BiblioSoftLoans.dto.Loans.HistoryLoanStudentDTO(l.nameBook,l.studentName, l.copyId, l.loanDate, lh.copyState) " +
            "FROM Loan l JOIN l.loanHistory lh WHERE l.studentId = :studentId")
    List<HistoryLoanStudentDTO> findLoanHistoryByStudentId(String studentId);

    @Query("SELECT new edu.eci.cvds.BiblioSoftLoans.dto.Loans.HistoryLoanBookDTO(l.nameBook,l.copyId, l.loanDate, lh.copyState) " +
            "FROM Loan l JOIN l.loanHistory lh WHERE l.copyId = :copyId")
    List<HistoryLoanBookDTO> findLoanHistoryByCopyId(String copyId);

    @Query("SELECT new edu.eci.cvds.BiblioSoftLoans.dto.Loans.History.HistoryLoanDTO(l.nameBook, l.studentName, l.loanDate, lh.copyState) " +
            "FROM Loan l JOIN l.loanHistory lh")
    List<HistoryLoanDTO> findAllHistory();
}