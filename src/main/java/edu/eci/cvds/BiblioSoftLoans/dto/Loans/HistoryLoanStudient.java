package edu.eci.cvds.BiblioSoftLoans.dto.Loans;

import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
public class HistoryLoanStudient {
    private String studentId;
    private LocalDate loanDate;
    private CopyState loanState;

    public HistoryLoanStudient(String studentId, LocalDate loanDate, CopyState loanState) {
        this.studentId = studentId;
        this.loanDate = loanDate;
        this.loanState = loanState;
    }
}
