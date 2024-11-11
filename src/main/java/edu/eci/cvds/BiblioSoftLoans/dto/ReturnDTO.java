package edu.eci.cvds.BiblioSoftLoans.dto;

import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import java.time.LocalDate;

public class ReturnDTO {
    private Long loanId; // ID del préstamo que se devuelve
    private LocalDate returnDate; // Fecha en la que se está realizando la devolución
    private CopyState finalCopyState; // Estado final de la copia al ser devuelta

    // Getters y Setters
}

