package edu.eci.cvds.BiblioSoftLoans.dto;

import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReturnResponseDTO {
    private Long loanId; // ID del préstamo que se devuelve
    private LocalDate returnDate; // Fecha en la que se está realizando la devolución
    private CopyState finalCopyState; // Estado final de la copia al ser devuelta

    public ReturnResponseDTO(Long loanId, LocalDate returnDate, CopyState finalCopyState) {
        this.loanId = loanId;
        this.returnDate = returnDate;
        this.finalCopyState = finalCopyState;
    }
}