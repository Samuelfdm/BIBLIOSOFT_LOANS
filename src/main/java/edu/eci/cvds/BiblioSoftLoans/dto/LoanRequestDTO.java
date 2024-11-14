package edu.eci.cvds.BiblioSoftLoans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class LoanRequestDTO {
    private Long studentId; // ID del estudiante que toma el préstamo
    private String copyId;  // ID de la copia o ejemplar que se presta
}