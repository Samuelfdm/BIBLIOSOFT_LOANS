package edu.eci.cvds.BiblioSoftLoans.dto;

import java.time.LocalDate;

public class CreateLoanRequestDTO {
    private Long studentId; // ID del estudiante que toma el préstamo
    private String copyId;  // ID de la copia o ejemplar que se presta
    private LocalDate loanDate;  // Fecha en la que se realiza el préstamo
    private LocalDate returnDate; // Fecha esperada de devolución

    // Getters y Setters
}

