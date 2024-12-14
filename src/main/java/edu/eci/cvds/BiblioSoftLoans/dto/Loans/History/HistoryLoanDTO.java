package edu.eci.cvds.BiblioSoftLoans.dto.Loans.History;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
public class HistoryLoanDTO {
    private String nameBook;
    private String studentName;
    private LocalDate loanDate;
    private String loanState;

    public HistoryLoanDTO(String nameBook, String copyId, LocalDate loanDate, String loanState) {
        this.nameBook = nameBook;
        this.studentName = copyId;
        this.loanDate = loanDate;
        this.loanState = loanState;
    }
}