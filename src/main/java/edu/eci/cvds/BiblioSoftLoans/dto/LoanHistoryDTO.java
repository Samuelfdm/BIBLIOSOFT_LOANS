package edu.eci.cvds.BiblioSoftLoans.dto;

import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanHistoryDTO {
    private Long id; // ID del evento en el historial
    private LocalDate date; // Fecha del evento en el historial
    private CopyState copyState; // Estado de la copia en ese momento
}