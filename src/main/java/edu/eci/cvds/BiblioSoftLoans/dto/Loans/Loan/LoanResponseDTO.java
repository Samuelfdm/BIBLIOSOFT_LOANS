package edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan;

import java.time.LocalDate;
import java.util.List;
import edu.eci.cvds.BiblioSoftLoans.model.LoanHistory;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanResponseDTO {
    private Long loanId;
    private String copyId;
    private String bookId;
    private String studentId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LoanState loanState;
    private List<LoanHistory> loanHistory;
}