package edu.eci.cvds.BiblioSoftLoans.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLoanRequestDTO {
    private Long studentId; // ID del estudiante que toma el préstamo
    private String copyId;  // ID de la copia o ejemplar que se presta
    private LocalDate loanDate;  // Fecha en la que se realiza el préstamo
    private LocalDate returnDate; // Fecha esperada de devolución
}