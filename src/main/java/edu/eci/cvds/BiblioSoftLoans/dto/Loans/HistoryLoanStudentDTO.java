package edu.eci.cvds.BiblioSoftLoans.dto.Loans;

import lombok.*;
import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
public class HistoryLoanStudentDTO {
    private String nameBook;
    private String studentName;
    private String copyId;
    private LocalDate loanDate;
    private String loanState;

    public HistoryLoanStudentDTO(String nameBook, String studentName, String copyId, LocalDate loanDate, String loanState) {
        this.nameBook = nameBook;
        this.studentName = studentName;
        this.copyId = copyId;
        this.loanDate = loanDate;
        this.loanState = loanState;
    }
}
