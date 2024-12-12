package edu.eci.cvds.BiblioSoftLoans.dto.Loans;

import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
public class HistoryLoanBookDTO {
    private String copyId;
    private LocalDate loanDate;
    private CopyState loanState;

    public HistoryLoanBookDTO(String copyId, LocalDate loanDate, CopyState loanState) {
        this.copyId = copyId;
        this.loanDate = loanDate;
        this.loanState = loanState;
    }
}
