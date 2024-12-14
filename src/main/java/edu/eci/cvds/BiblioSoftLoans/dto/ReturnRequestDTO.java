package edu.eci.cvds.BiblioSoftLoans.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class ReturnRequestDTO {
    //Información para buscar el prestamo a devolver
    private String studentId; // ID del usuario que devuelve el libro
    private String copyId;  // ID de la copia o ejemplar que se presto
    private String finalCopyState; // Estado final de la copia al ser devuelta

    public ReturnRequestDTO(String studentId, String copyId, String finalCopyState) {
        this.studentId = studentId;
        this.copyId = copyId;
        this.finalCopyState = finalCopyState;
    }
}