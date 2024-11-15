package edu.eci.cvds.BiblioSoftLoans.dto;

import java.time.LocalDate;
import java.util.List;

import edu.eci.cvds.BiblioSoftLoans.model.LoanHistory;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class LoanResponseDTO {
    private Long loanId;
    private String copyId;
    private Long studentId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LoanState loanState;
    private List<LoanHistory> loanHistory;
}