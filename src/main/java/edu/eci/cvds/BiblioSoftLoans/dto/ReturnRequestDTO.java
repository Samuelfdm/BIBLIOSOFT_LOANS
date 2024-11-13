package edu.eci.cvds.BiblioSoftLoans.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnRequestDTO {
    private Long studentId; // ID del usuario que devuelve el libro
    private String copyId;  // ID de la copia o ejemplar que se presto
}