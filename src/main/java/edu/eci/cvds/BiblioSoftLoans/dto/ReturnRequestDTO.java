package edu.eci.cvds.BiblioSoftLoans.dto;

import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReturnRequestDTO {
    //Información para buscar el prestamo a devolver
    private String studentId; // ID del usuario que devuelve el libro
    private String copyId;  // ID de la copia o ejemplar que se presto
    private CopyState finalCopyState; // Estado final de la copia al ser devuelta

    public ReturnRequestDTO(String studentId, String copyId, CopyState finalCopyState) {
        this.studentId = studentId;
        this.copyId = copyId;
        this.finalCopyState = finalCopyState;
    }
}