package edu.eci.cvds.BiblioSoftLoans.dto;

import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanDTO {
    private Long id; // ID del préstamo
    private Long studentId;
    private String copyId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LoanState loanState; // Estado actual del préstamo
    private List<LoanHistoryDTO> loanHistory; // Historial del préstamo (opcional)
}