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
    private String copyId;
    private LocalDate loanDate;
    private String loanState;


    public HistoryLoanStudient(String studentId,String copyId, LocalDate loanDate, String loanState) {
        this.studentId = studentId;
        this.loanDate = loanDate;
        this.loanState = loanState;
        this.copyId = copyId;
    }
}
