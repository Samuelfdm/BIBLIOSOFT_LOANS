package edu.eci.cvds.BiblioSoftLoans.dto;

import java.time.LocalDate;
import java.util.List;
import edu.eci.cvds.BiblioSoftLoans.model.LoanHistory;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanResponseDTO {
    private String loanId;
    private String copyId;
    private Long studentId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LoanState loanState;
    private List<LoanHistory> loanHistory;
}