package edu.eci.cvds.BiblioSoftLoans.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class ReturnResponseDTO {
    private Long loanId; // ID del préstamo que se devuelve
    private LocalDate returnDate; // Fecha en la que se está realizando la devolución
    private String copyId;
    private String finalCopyState; // Estado final de la copia al ser devuelta

    public ReturnResponseDTO(Long loanId, LocalDate returnDate, String copyId,String finalCopyState) {
        this.loanId = loanId;
        this.returnDate = returnDate;
        this.copyId = copyId;
        this.finalCopyState = finalCopyState;
    }
}