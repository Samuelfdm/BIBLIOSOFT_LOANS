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
    private String studentName;
    private String copyId;
    private LocalDate loanDate;
    private String loanState;


    public HistoryLoanStudient(String studentName,String copyId, LocalDate loanDate, String loanState) {
        this.studentName = studentName;
        this.loanDate = loanDate;
        this.loanState = loanState;
        this.copyId = copyId;
    }
}
