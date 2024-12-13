package edu.eci.cvds.BiblioSoftLoans.dto.Loans;

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
    private String nameBook;
    private String copyId;
    private LocalDate loanDate;
    private String loanState;

    public HistoryLoanBookDTO(String nameBook, String copyId, LocalDate loanDate, String loanState) {
        this.nameBook = nameBook;
        this.copyId = copyId;
        this.loanDate = loanDate;
        this.loanState = loanState;
    }
}
